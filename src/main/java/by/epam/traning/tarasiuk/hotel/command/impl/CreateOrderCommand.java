package by.epam.traning.tarasiuk.hotel.command.impl;

import by.epam.traning.tarasiuk.hotel.command.Command;
import by.epam.traning.tarasiuk.hotel.command.exception.CommandException;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.service.OrderService;
import by.epam.traning.tarasiuk.hotel.service.UserService;
import by.epam.traning.tarasiuk.hotel.service.exception.OrderException;
import by.epam.traning.tarasiuk.hotel.service.exception.PassportException;
import by.epam.traning.tarasiuk.hotel.service.factory.ServiceFactory;
import by.epam.traning.tarasiuk.hotel.service.factory.impl.ServiceFactoryImpl;
import by.epam.traning.tarasiuk.hotel.util.CalcPrice;
import by.epam.traning.tarasiuk.hotel.util.DateParser;
import by.epam.traning.tarasiuk.hotel.util.exception.CalcPriceException;
import by.epam.traning.tarasiuk.hotel.util.exception.DateParserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;

public class CreateOrderCommand implements Command {
    private final Logger logger = LogManager.getLogger("Command");

    private static final String NUMBER = "number";
    private static final String PRICE = "price";
    private static final String FIRST_DAY = "first_day";
    private static final String LAST_DAY = "last_day";
    private static final String CLIENT = "client";
    private static final String LAST_NAME = "last_name";
    private static final String NAME = "name";
    private static final String PATRONYMIC = "patronymic";
    private static final String COUNTRY = "country";
    private static final String IDENTIFICATION = "ident";
    private static final String PASSPORT = "pasport";
    private static final String BIRTHDAY = "birthday";
    private static final String SEX = "sex";
    private static final String ORDER_PAGE = "Order.jsp";

    private HttpServletRequest request;
    private HttpServletResponse response;

    public CreateOrderCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     *
     * @throws CommandException final wrapper for throws exception
     */
    @Override
    public void execute() throws CommandException {
        try {
            String number_id = request.getParameter(NUMBER);
            String price = request.getParameter(PRICE);
            String first_day = request.getParameter(FIRST_DAY);
            String last_day = request.getParameter(LAST_DAY);

            DateParser dateParser = new DateParser();
            Date first = dateParser.parse(first_day);
            Date last = dateParser.parse(last_day);

            CalcPrice calcPrice = new CalcPrice();
            double price_res = calcPrice.getPrice(first, last, price);

            ServiceFactory factory = ServiceFactoryImpl.getInstance();
            OrderService orderService = factory.getOrderService();
            UserService userService = factory.getUserService();

            HttpSession session = request.getSession();
            Client client = (Client) session.getAttribute(CLIENT);
            if (!userService.getPassport(client.getId())) {
                String last_name = request.getParameter(LAST_NAME);
                String name = request.getParameter(NAME);
                String patronymic = request.getParameter(PATRONYMIC);
                String country = request.getParameter(COUNTRY);
                String ident = request.getParameter(IDENTIFICATION);
                String passport = request.getParameter(PASSPORT);
                String birthday = request.getParameter(BIRTHDAY);
                String sex = request.getParameter(SEX);

                Date birth = dateParser.parse(birthday);
                userService.inputPassport(client, last_name, name, patronymic, country, birth, sex, ident, passport);
            }
            orderService.makeAnOrder(client.getId(), Integer.parseInt(number_id), first, last);
            request.setAttribute(PRICE, price_res);
            request.getRequestDispatcher(ORDER_PAGE).forward(request, response);
            logger.info("Create order done");
        } catch (ServletException | IOException | ParseException | OrderException | PassportException
                 | CalcPriceException | DateParserException e) {
            logger.info("Create order failed: ", e);
            throw new CommandException("Create order failed", e);
        } catch (ConnectionException e) {
            logger.error("Connect to database failed, error: ", e);
        }
    }
}
