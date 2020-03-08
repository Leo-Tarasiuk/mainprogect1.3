package by.epam.traning.tarasiuk.hotel.command.impl;

import by.epam.traning.tarasiuk.hotel.command.Command;
import by.epam.traning.tarasiuk.hotel.command.exception.CommandException;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.service.GuestService;
import by.epam.traning.tarasiuk.hotel.service.exception.LogInException;
import by.epam.traning.tarasiuk.hotel.service.exception.RegisterException;
import by.epam.traning.tarasiuk.hotel.service.factory.ServiceFactory;
import by.epam.traning.tarasiuk.hotel.service.factory.impl.ServiceFactoryImpl;
import by.epam.traning.tarasiuk.hotel.util.LogInSessionInitializer;
import by.epam.traning.tarasiuk.hotel.util.exception.SessionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterCommand implements Command {
    private final Logger logger = LogManager.getLogger("Command");

    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "pass";

    private HttpServletRequest request;
    private HttpServletResponse response;

    public RegisterCommand(HttpServletRequest request, HttpServletResponse response) {
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
            String name = request.getParameter(NAME);
            String email = request.getParameter(EMAIL);
            String password = request.getParameter(PASSWORD);

            ServiceFactory factory = ServiceFactoryImpl.getInstance();
            GuestService service = factory.getGuestService();

            service.signUp(name, email, password);
            service.signIn(email, password);

            LogInSessionInitializer sessionInitializer = new LogInSessionInitializer();
            sessionInitializer.initialize(request, response);
            logger.info("Registration successful");
        } catch (RegisterException | LogInException | SessionException e) {
            logger.info("Registration failed: ", e);
            throw new CommandException("Registration failed", e);
        } catch (ConnectionException e) {
            logger.error("Connect to database failed, error: ", e);
        }

    }
}
