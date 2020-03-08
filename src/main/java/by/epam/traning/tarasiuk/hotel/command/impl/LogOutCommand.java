package by.epam.traning.tarasiuk.hotel.command.impl;

import by.epam.traning.tarasiuk.hotel.command.Command;
import by.epam.traning.tarasiuk.hotel.command.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogOutCommand implements Command {
    private final Logger logger = LogManager.getLogger("Command");

    private static final String USER_PAGE = "userPage.jsp";

    private HttpServletRequest request;
    private HttpServletResponse response;

    public LogOutCommand(HttpServletRequest request, HttpServletResponse response) {
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
            request.getSession().invalidate();
            request.getRequestDispatcher(USER_PAGE).forward(request, response);
            logger.info("Log out done");
        } catch (IOException | ServletException e) {
            logger.info("Log out failed: ", e);
            throw new CommandException("Log out failed", e);
        }
    }
}
