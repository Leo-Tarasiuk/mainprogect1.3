package by.epam.traning.tarasiuk.hotel.command.impl;

import by.epam.traning.tarasiuk.hotel.command.Command;
import by.epam.traning.tarasiuk.hotel.command.exception.CommandException;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.Client;
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

public class GetAllClientsCommand implements Command {
    private final Logger logger = LogManager.getLogger("Command");

    private static final String ORDER = "orders";
    private static final String CLIENT = "clients";
    private static final String CLIENT_PAGE = "WEB-INF/admin/adminClient.jsp";

    private HttpServletRequest request;
    private HttpServletResponse response;

    public GetAllClientsCommand(HttpServletRequest request, HttpServletResponse response) {
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
            AdminService adminService = factory.getAdminService();
            AdminService orderService = factory.getAdminService();

            List<Order> orders = orderService.getAllOrders();
            List<Client> clients = adminService.getAllClient();

            request.setAttribute(ORDER, orders);
            request.setAttribute(CLIENT, clients);

            request.getRequestDispatcher(CLIENT_PAGE).forward(request, response);
            logger.info("All clients return");
        } catch (ServletException | IOException e) {
            logger.info("Get all clients failed: ", e);
            throw new CommandException("Get all clients failed", e);
        } catch (ConnectionException e) {
            logger.error("Connect to database failed, error: ", e);
        }
    }
}
