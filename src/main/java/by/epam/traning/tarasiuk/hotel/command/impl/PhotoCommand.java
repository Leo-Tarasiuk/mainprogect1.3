package by.epam.traning.tarasiuk.hotel.command.impl;

import by.epam.traning.tarasiuk.hotel.command.Command;
import by.epam.traning.tarasiuk.hotel.command.exception.CommandException;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PhotoCommand implements Command {
    private final Logger logger = LogManager.getLogger("Command");

    private static final String CLIENT = "client";
    private static final String USER_PAGE = "userPage.jsp";
    private static final String CLIENT_PAGE = "clientPage.jsp";

    private HttpServletRequest request;
    private HttpServletResponse response;

    public PhotoCommand(HttpServletRequest request, HttpServletResponse response) {
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
            HttpSession session = request.getSession();

            Client client = (Client) session.getAttribute(CLIENT);

            if (client == null) {
                request.getRequestDispatcher(USER_PAGE).forward(request, response);
            } else {
                request.getRequestDispatcher(CLIENT_PAGE).forward(request, response);
            }
            logger.info("photo return");
        } catch (ServletException | IOException e) {
            logger.info("Get photo failed: ", e);
            throw new CommandException("Get photo failed", e);
        }
    }
}