package by.epam.traning.tarasiuk.hotel.command.impl;

import by.epam.traning.tarasiuk.hotel.command.Command;
import by.epam.traning.tarasiuk.hotel.command.exception.CommandException;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.Order;
import by.epam.traning.tarasiuk.hotel.service.AdminService;
import by.epam.traning.tarasiuk.hotel.service.factory.ServiceFactory;
import by.epam.traning.tarasiuk.hotel.service.factory.impl.ServiceFactoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetAllOrdersCommand implements Command {
    private final Logger logger = LogManager.getLogger("Command");

    private static final String ORDER = "orders";
    private static final String ORDER_PAGE = "WEB-INF/admin/adminOrder.jsp";

    private HttpServletRequest request;
    private HttpServletResponse response;

    public GetAllOrdersCommand(HttpServletRequest request, HttpServletResponse response) {
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
            AdminService orderService = factory.getAdminService();

            List<Order> orders = orderService.getAllOrders();

            request.setAttribute(ORDER, orders);

            request.getRequestDispatcher(ORDER_PAGE).forward(request, response);
            logger.info("All orders return");
        } catch (ServletException | IOException e) {
            logger.info("Get all orders failed: ", e);
            throw new CommandException("Get all orders failed", e);
        } catch (ConnectionException e) {
            logger.error("Connect to database failed, error: ", e);
        }
    }
}
