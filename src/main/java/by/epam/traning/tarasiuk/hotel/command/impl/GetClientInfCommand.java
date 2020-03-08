package by.epam.traning.tarasiuk.hotel.command.impl;

import by.epam.traning.tarasiuk.hotel.command.Command;
import by.epam.traning.tarasiuk.hotel.command.exception.CommandException;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.entity.Order;
import by.epam.traning.tarasiuk.hotel.service.OrderService;
import by.epam.traning.tarasiuk.hotel.service.UserService;
import by.epam.traning.tarasiuk.hotel.service.factory.ServiceFactory;
import by.epam.traning.tarasiuk.hotel.service.factory.impl.ServiceFactoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class GetClientInfCommand implements Command {
    private final Logger logger = LogManager.getLogger("Command");

    private static final String CLIENT = "client";
    private static final String ORDER = "orders";
    private static final String MY_PAGE = "myPage.jsp";
    private static final String MY_PAGE_EXPERT = "myPageExpert.jsp";

    private HttpServletRequest request;
    private HttpServletResponse response;

    public GetClientInfCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * @throws CommandException final wrapper for throws exception
     */
    @Override
    public void execute() throws CommandException {
        try {
            HttpSession session = request.getSession();
            Client client = (Client) session.getAttribute(CLIENT);

            ServiceFactory factory = ServiceFactoryImpl.getInstance();
            UserService service = factory.getUserService();
            OrderService orderService = factory.getOrderService();

            if (client != null) {
                List<Order> orders = orderService.getOrderForClient(client);
                request.setAttribute(ORDER, orders);
                if (service.getPassport(client.getId())) {
                    request.getRequestDispatcher(MY_PAGE_EXPERT).forward(request, response);
                } else {
                    request.getRequestDispatcher(MY_PAGE).forward(request, response);
                }
            }
            logger.info("Client information return");
        } catch (ServletException | IOException e) {
            logger.info("Get client information failed: ", e);
            throw new CommandException("Get client information failed", e);
        } catch (ConnectionException e) {
            logger.error("Connect to database failed, error: ", e);
        }
    }
}