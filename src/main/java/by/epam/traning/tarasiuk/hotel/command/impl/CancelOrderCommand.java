package by.epam.traning.tarasiuk.hotel.command.impl;

import by.epam.traning.tarasiuk.hotel.command.Command;
import by.epam.traning.tarasiuk.hotel.command.exception.CommandException;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.service.OrderService;
import by.epam.traning.tarasiuk.hotel.service.exception.OrderException;
import by.epam.traning.tarasiuk.hotel.service.factory.ServiceFactory;
import by.epam.traning.tarasiuk.hotel.service.factory.impl.ServiceFactoryImpl;
import by.epam.traning.tarasiuk.hotel.util.DateParser;
import by.epam.traning.tarasiuk.hotel.util.exception.DateParserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.text.ParseException;

public class CancelOrderCommand implements Command {
    private final Logger logger = LogManager.getLogger("Command");

    private static final String CLIENT = "client";
    private static final String FIRST_DAY = "first_day";
    private static final String LAST_DAY = "last_day";

    private HttpServletRequest request;
    private HttpServletResponse response;

    public CancelOrderCommand(HttpServletRequest request, HttpServletResponse response) {
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
            ServiceFactory factory = ServiceFactoryImpl.getInstance();
            OrderService service = factory.getOrderService();
            HttpSession session = request.getSession();

            Client client = (Client) session.getAttribute(CLIENT);
            String first_day = request.getParameter(FIRST_DAY);
            String last_day = request.getParameter(LAST_DAY);

            DateParser dateParser = new DateParser();
            Date first = dateParser.parse(first_day);
            Date last = dateParser.parse(last_day);

            service.cancelOrder(client, first, last);
            logger.info("Order is cancel");
        } catch (ParseException | OrderException | DateParserException e) {
            logger.info("Cancel order failed: ", e);
            throw new CommandException("Cancel order failed", e);
        } catch (ConnectionException e) {
            logger.error("Connect to database failed, error: ", e);
        }
    }
}
