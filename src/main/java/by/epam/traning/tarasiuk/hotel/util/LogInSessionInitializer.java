package by.epam.traning.tarasiuk.hotel.util;

import by.epam.traning.tarasiuk.hotel.dao.DAOAdmin;
import by.epam.traning.tarasiuk.hotel.dao.DAOClient;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.dao.factory.DAOFactory;
import by.epam.traning.tarasiuk.hotel.dao.factory.impl.DAOFactoryImpl;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.entity.Order;
import by.epam.traning.tarasiuk.hotel.entity.UserRole;
import by.epam.traning.tarasiuk.hotel.service.AdminService;
import by.epam.traning.tarasiuk.hotel.service.factory.ServiceFactory;
import by.epam.traning.tarasiuk.hotel.service.factory.impl.ServiceFactoryImpl;
import by.epam.traning.tarasiuk.hotel.util.exception.ClientParserException;
import by.epam.traning.tarasiuk.hotel.util.exception.SessionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class LogInSessionInitializer {
    private final Logger logger = LogManager.getLogger("Command");

    private static final String EMAIL = "email";
    private static final String LOGIN = "login";
    private static final String ADMIN = "admin";
    private static final String CLIENT = "client";
    private static final String ORDER = "orders";
    private static final String CLIENT_PAGE = "clientPage.jsp";
    private static final String ADMIN_PAGE = "WEB-INF/admin/adminOrder.jsp";

    /**
     * @param request - client's request
     * @param response - response for client
     */
    public void initialize(HttpServletRequest request, HttpServletResponse response) throws SessionException {
        String name = request.getParameter(EMAIL);
        try {
            HttpSession session = request.getSession();

            DAOFactory factory = DAOFactoryImpl.getInstance();
            DAOClient daoClient = factory.getDAOClient();
            DAOAdmin daoAdmin = factory.getDAOAdmin();

            ClientParser clientParser = new ClientParser();
            Client client = clientParser.parse(daoClient.getUserInformation(name));

            if (client.getRole().equals(UserRole.ADMIN)) {
                session.setAttribute(LOGIN, true);
                session.setAttribute(ADMIN, client);
                List<Order> orders = daoAdmin.getAllOrders();
                request.setAttribute(ORDER, orders);
                request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);
            } else {
                session.setAttribute(LOGIN, true);
                session.setAttribute(CLIENT, client);
                request.getRequestDispatcher(CLIENT_PAGE).forward(request, response);
            }
            logger.info("session init");
        } catch (IOException | ServletException | ClientParserException e) {
            logger.info("session init failed");
            throw new SessionException("session init failed", e);
        } catch (ConnectionException e) {
            logger.error("Connect to database failed, error: ", e);
        }
    }
}
