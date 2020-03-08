package by.epam.traning.tarasiuk.hotel.service.impl;

import by.epam.traning.tarasiuk.hotel.dao.DAOClient;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.dao.factory.DAOFactory;
import by.epam.traning.tarasiuk.hotel.dao.factory.impl.DAOFactoryImpl;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.service.UserService;
import by.epam.traning.tarasiuk.hotel.service.exception.PassportException;
import by.epam.traning.tarasiuk.hotel.service.exception.UpdateException;
import by.epam.traning.tarasiuk.hotel.service.validation.Validator;
import by.epam.traning.tarasiuk.hotel.util.PasswordEncrypter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;

public class UserServiceImpl implements UserService {
    private static final UserServiceImpl INSTANCE = new UserServiceImpl();
    private static final PasswordEncrypter passwordEncrypter = new PasswordEncrypter();
    private static final String KEY = "hotel";
    private final Logger logger = LogManager.getLogger("ServiceLayer");
    private DAOFactory factory = DAOFactoryImpl.getInstance();
    private DAOClient daoClient = factory.getDAOClient();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return INSTANCE;
    }

    /**
     * @param client - Client who want to change his email
     * @param email  - new client's email
     * @throws UpdateException     - exception with change data
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public void changeMail(Client client, String email) throws UpdateException, ConnectionException {
        if (Validator.isValidEmail(email)) {
            logger.debug("Invalid new mail format \"" + email + "\"");
            throw new UpdateException("Invalid new mail format");
        }

        client.setEmail(email);

        if (!daoClient.changeData(client, "email")) {
            logger.debug("Email wasn't change");
            throw new UpdateException("Email wasn't change");
        }
    }

    /**
     * @param client   - Client who want to change his email
     * @param password - new client's password
     * @throws UpdateException     - exception with change data
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public void changePassword(Client client, String password) throws UpdateException, ConnectionException {
        if (Validator.isValidPassword(password)) {
            logger.debug("Invalid new password format \"" + password + "\"");
            throw new UpdateException("Invalid new password format");
        }
        try {
            client.setPassword(passwordEncrypter.encrypt(KEY, password));

            if (!daoClient.changeData(client, "password")) {
                logger.debug("Password wasn't change");
                throw new UpdateException("Password wasn't change");
            }
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | BadPaddingException
                | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException
                | IllegalBlockSizeException | UnsupportedEncodingException e) {
            logger.debug("Password wasn't change, exception: ", e);
            throw new UpdateException("Password wasn't change, exception: ", e);
        }
    }

    /**
     * @param client           - Client who input his passport data
     * @param lastName         - Last name in passport data
     * @param name             - Name in passport data
     * @param patronymic       - Patronymic in passport data
     * @param country          - Country in passport data
     * @param dateBirth        - Date of birth in passport data
     * @param sex              - Sex in passport data
     * @param identificationNo - Identification number in passport data
     * @param passportNo       - Passport number in passport data
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public void inputPassport(Client client, String lastName, String name, String patronymic,
                              String country, Date dateBirth, String sex,
                              String identificationNo, String passportNo) throws PassportException, ConnectionException {
        if (!Validator.isValidPassportInf(lastName, name, patronymic, country, dateBirth,
                sex, identificationNo, passportNo)) {
            logger.debug("Invalid passport format");
            throw new PassportException("Invalid passport format");
        }

        Client.Passport passport = client.new Passport(lastName, name, patronymic,
                country, dateBirth, sex, identificationNo, passportNo);

        daoClient.inputPassport(passport);
    }

    /**
     * @param client - Client who want to check his passport data
     * @return boolean value which show that client was input his passport data
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public boolean getPassport(int client) throws ConnectionException {
        return daoClient.getPassport(client);
    }
}
