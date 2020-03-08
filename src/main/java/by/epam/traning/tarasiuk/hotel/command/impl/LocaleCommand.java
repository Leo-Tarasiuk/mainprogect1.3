package by.epam.traning.tarasiuk.hotel.command.impl;

import by.epam.traning.tarasiuk.hotel.command.Command;
import by.epam.traning.tarasiuk.hotel.command.exception.CommandException;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.entity.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LocaleCommand implements Command {
    private static final String ADMIN = "admin";
    private static final String CLIENT = "client";
    private static final String USER_PAGE = "userPage.jsp";
    private static final String CLIENT_PAGE = "clientPage.jsp";
    private static final String ADMIN_PAGE = "WEB-INF/admin/adminOrder.jsp";
    private static final String LOCALE = "locale";
    private static final String LOCALE_RU = "ru";
    private static final String LOCALE_EN = "en";
    private final Logger logger = LogManager.getLogger("Command");
    private HttpServletRequest request;
    private HttpServletResponse response;

    public LocaleCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * @throws CommandException final wrapper for throws exception
     */
    @Override
    public void execute() throws CommandException {
        try {
            String ruLanguage = request.getParameter("ruLanguage");
            String enLanguage = request.getParameter("enLanguage");
            HttpSession session = request.getSession();
            if (ruLanguage != null) {
                session.setAttribute(LOCALE, LOCALE_RU);
            }
            if (enLanguage != null) {
                session.setAttribute(LOCALE, LOCALE_EN);
            }

            Client client = (Client) session.getAttribute(CLIENT);
            Client admin = (Client) session.getAttribute(ADMIN);

            if (admin != null && admin.getRole().equals(UserRole.ADMIN)) {
                request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);
            } else if (client != null) {
                request.getRequestDispatcher(CLIENT_PAGE).forward(request, response);
            } else {
                request.getRequestDispatcher(USER_PAGE).forward(request, response);
            }
            logger.info("Change locale done");
        } catch (ServletException | IOException e) {
            logger.info("change locale failed: ", e);
            throw new CommandException("change locale failed", e);
        }
    }
}