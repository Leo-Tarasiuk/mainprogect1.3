package by.epam.traning.tarasiuk.hotel.command.impl;

import by.epam.traning.tarasiuk.hotel.command.Command;
import by.epam.traning.tarasiuk.hotel.command.exception.CommandException;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.service.UserService;
import by.epam.traning.tarasiuk.hotel.service.exception.PassportException;
import by.epam.traning.tarasiuk.hotel.service.factory.ServiceFactory;
import by.epam.traning.tarasiuk.hotel.service.factory.impl.ServiceFactoryImpl;
import by.epam.traning.tarasiuk.hotel.util.DateParser;
import by.epam.traning.tarasiuk.hotel.util.exception.DateParserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.text.ParseException;

public class RegisterPassportCommand implements Command {
    private final Logger logger = LogManager.getLogger("Command");

    private static final String CLIENT = "client";
    private static final String LAST_NAME = "last_name";
    private static final String NAME = "name";
    private static final String PATRONYMIC = "patronymic";
    private static final String COUNTRY = "country";
    private static final String IDENTIFICATION = "ident";
    private static final String PASSPORT = "pasport";
    private static final String BIRTHDAY = "birthday";
    private static final String SEX = "sex";

    private HttpServletRequest request;
    private HttpServletResponse response;

    public RegisterPassportCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * @throws CommandException final wrapper for throws exception
     */
    @Override
    public void execute() throws CommandException {
        try {
            String lastName = request.getParameter(LAST_NAME);
            String name = request.getParameter(NAME);
            String patronymic = request.getParameter(PATRONYMIC);
            String country = request.getParameter(COUNTRY);
            String birthdayStr = request.getParameter(BIRTHDAY);
            String sex = request.getParameter(SEX);
            String identificationNo = request.getParameter(IDENTIFICATION);
            String passportNo = request.getParameter(PASSPORT);

            DateParser dateParser = new DateParser();
            Date birthday = dateParser.parse(birthdayStr);

            ServiceFactory factory = ServiceFactoryImpl.getInstance();
            UserService userService = factory.getUserService();

            HttpSession session = request.getSession();
            Client client = (Client) session.getAttribute(CLIENT);

            userService.inputPassport(client, lastName, name, patronymic, country,
                    birthday, sex, identificationNo, passportNo);
            logger.info("Registration passport successful");
        } catch (ParseException | PassportException | DateParserException e) {
            logger.info("Registration passport failed: ", e);
            throw new CommandException("Registration passport failed", e);
        } catch (ConnectionException e) {
            logger.error("Connect to database failed, error: ", e);
        }
    }
}
