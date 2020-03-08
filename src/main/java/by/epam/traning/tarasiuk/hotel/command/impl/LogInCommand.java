package by.epam.traning.tarasiuk.hotel.command.impl;

import by.epam.traning.tarasiuk.hotel.command.Command;
import by.epam.traning.tarasiuk.hotel.command.exception.CommandException;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.service.GuestService;
import by.epam.traning.tarasiuk.hotel.service.exception.LogInException;
import by.epam.traning.tarasiuk.hotel.service.factory.impl.ServiceFactoryImpl;
import by.epam.traning.tarasiuk.hotel.util.LogInSessionInitializer;
import by.epam.traning.tarasiuk.hotel.util.exception.SessionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInCommand implements Command {
    private final Logger logger = LogManager.getLogger("Command");

    private static final String EMAIL = "email";
    private static final String PASSWORD = "pass";

    private HttpServletRequest request;
    private HttpServletResponse response;

    public LogInCommand(HttpServletRequest request, HttpServletResponse response) {
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
            String login = request.getParameter(EMAIL);
            String password = request.getParameter(PASSWORD);

            GuestService guestService = ServiceFactoryImpl.getInstance().getGuestService();
            guestService.signIn(login, password);

            LogInSessionInitializer sessionInitializer = new LogInSessionInitializer();
            sessionInitializer.initialize(request, response);
            logger.info("Log in successful");
        } catch (LogInException | SessionException e) {
            logger.info("Log in failed: ", e);
            throw new CommandException("Log in failed: " + e.getMessage());
        } catch (ConnectionException e) {
            logger.error("Connect to database failed, error", e);
        }
    }
}
