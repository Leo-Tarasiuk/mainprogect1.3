package by.epam.traning.tarasiuk.hotel.command.impl;

import by.epam.traning.tarasiuk.hotel.command.Command;
import by.epam.traning.tarasiuk.hotel.command.exception.CommandException;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.BlackList;
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

public class GetBlackListCommand implements Command {
    private final Logger logger = LogManager.getLogger("Command");

    private static final String BLACK_LIST = "blackList";
    private static final String BAN_PAGE = "WEB-INF/admin/adminBan.jsp";

    private HttpServletRequest request;
    private HttpServletResponse response;

    public GetBlackListCommand(HttpServletRequest request, HttpServletResponse response) {
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

            List<BlackList> blackList = orderService.getBlackList();

            request.setAttribute(BLACK_LIST, blackList);

            request.getRequestDispatcher(BAN_PAGE).forward(request, response);
            logger.info("Black list return");
        } catch (ServletException | IOException e) {
            logger.info("Get black list failed: ", e);
            throw new CommandException("Get black list failed", e);
        } catch (ConnectionException e) {
            logger.error("Connect to database failed, error: ", e);
        }
    }
}