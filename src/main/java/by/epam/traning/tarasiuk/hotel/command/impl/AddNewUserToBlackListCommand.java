package by.epam.traning.tarasiuk.hotel.command.impl;

import by.epam.traning.tarasiuk.hotel.command.Command;
import by.epam.traning.tarasiuk.hotel.command.exception.CommandException;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.service.AdminService;
import by.epam.traning.tarasiuk.hotel.service.exception.BanedException;
import by.epam.traning.tarasiuk.hotel.service.factory.ServiceFactory;
import by.epam.traning.tarasiuk.hotel.service.factory.impl.ServiceFactoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddNewUserToBlackListCommand implements Command {
    private final Logger logger = LogManager.getLogger("Command");

    private static final String BAN_ID = "ban_id";
    private static final String BAN_REASON = "ban_reason";
    private static final String ADMIN_PAGE = "WEB-INF/admin/adminBan.jsp";

    private HttpServletRequest request;
    private HttpServletResponse response;

    public AddNewUserToBlackListCommand(HttpServletRequest request, HttpServletResponse response) {
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
            String clientId = request.getParameter(BAN_ID);
            String reason = request.getParameter(BAN_REASON);

            ServiceFactory factory = ServiceFactoryImpl.getInstance();
            AdminService adminService = factory.getAdminService();

            int client_id = Integer.parseInt(clientId);

            adminService.addBlackList(client_id, reason);
            request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);
            logger.info("Add new user to black list done");
        } catch (BanedException | ServletException | IOException e) {
            logger.info("Add new user to black list failed: ", e);
            throw new CommandException("Add new user to black list failed", e);
        } catch (ConnectionException e) {
            logger.error("Connect to database failed, error: ", e);
        }

    }
}
