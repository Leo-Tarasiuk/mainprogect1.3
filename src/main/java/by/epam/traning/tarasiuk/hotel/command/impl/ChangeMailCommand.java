package by.epam.traning.tarasiuk.hotel.command.impl;

import by.epam.traning.tarasiuk.hotel.command.Command;
import by.epam.traning.tarasiuk.hotel.command.exception.CommandException;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.service.UserService;
import by.epam.traning.tarasiuk.hotel.service.exception.UpdateException;
import by.epam.traning.tarasiuk.hotel.service.factory.ServiceFactory;
import by.epam.traning.tarasiuk.hotel.service.factory.impl.ServiceFactoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangeMailCommand implements Command {
    private final Logger logger = LogManager.getLogger("Command");

    private static final String EMAIL = "email";
    private static final String CLIENT = "client";

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ChangeMailCommand(HttpServletRequest request, HttpServletResponse response) {
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
            String mail = request.getParameter(EMAIL);

            HttpSession session = request.getSession();
            Client client = (Client) session.getAttribute(CLIENT);

            ServiceFactory factory = ServiceFactoryImpl.getInstance();
            UserService userService = factory.getUserService();

            userService.changeMail(client, mail);
            logger.info("change mail done");
        } catch (UpdateException e) {
            logger.info("Change mail failed: ", e);
            throw new CommandException("Change mail failed", e);
        } catch (ConnectionException e) {
            logger.error("Connect to database failed, error: ", e);
        }
    }
}
