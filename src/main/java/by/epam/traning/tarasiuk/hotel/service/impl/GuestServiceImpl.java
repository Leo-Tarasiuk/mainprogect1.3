package by.epam.traning.tarasiuk.hotel.service.impl;

import by.epam.traning.tarasiuk.hotel.dao.DAOClient;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.dao.factory.DAOFactory;
import by.epam.traning.tarasiuk.hotel.dao.factory.impl.DAOFactoryImpl;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.service.GuestService;
import by.epam.traning.tarasiuk.hotel.service.exception.LogInException;
import by.epam.traning.tarasiuk.hotel.service.exception.RegisterException;
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

public class GuestServiceImpl implements GuestService {
    private static final GuestServiceImpl INSTANCE = new GuestServiceImpl();
    private static final PasswordEncrypter passwordEncrypter = new PasswordEncrypter();
    private static final String KEY = "hotel";
    private final Logger logger = LogManager.getLogger("ServiceLayer");

    private DAOFactory factory = DAOFactoryImpl.getInstance();
    private DAOClient daoClient = factory.getDAOClient();

    private GuestServiceImpl() {

    }

    public static GuestServiceImpl getInstance() {
        return INSTANCE;
    }

    /**
     * @param email    - client's email for sign in
     * @param password - client's password for sign in
     * @throws LogInException      - exception with sign in
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public void signIn(String email, String password) throws LogInException, ConnectionException {
        try {
            Client client = new Client();
            client.setEmail(email);
            client.setPassword(passwordEncrypter.encrypt(KEY, password));

            if (!daoClient.signIn(client)) {
                logger.debug("Log in failed");
                throw new LogInException("You not registration or banned");
            }
        } catch (InvalidKeySpecException | NoSuchAlgorithmException
                | BadPaddingException | InvalidKeyException
                | InvalidAlgorithmParameterException | NoSuchPaddingException
                | IllegalBlockSizeException | UnsupportedEncodingException e) {
            logger.debug("Log in failed, exception :", e);
            throw new LogInException("log in failed, exception :", e);
        }
    }

    /**
     * @param name     - client's name for using website
     * @param email    - client's email for registration
     * @param password - client's password for registration
     * @throws RegisterException   - exception with registration
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public void signUp(String name, String email, String password) throws RegisterException, ConnectionException {
        if (Validator.isValidEmail(email)) {
            logger.debug("Invalid mail format \"" + email + "\"");
            throw new RegisterException("Invalid mail format");
        }
        if (!Validator.isValidUsername(name)) {
            logger.debug("Invalid name format \"" + name + "\"");
            throw new RegisterException("Invalid name format");
        }
        if (Validator.isValidPassword(password)) {
            logger.debug("Invalid password format \"" + password + "\"");
            throw new RegisterException("Invalid password format");
        }

        try {
            Client client = new Client();
            client.setLastName(name);
            client.setEmail(email);
            client.setPassword(passwordEncrypter.encrypt(KEY, password));

            if (!daoClient.signUp(client)) {
                logger.debug("Registration failed");
                throw new RegisterException("Registration failed");
            }
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | BadPaddingException |
                InvalidKeyException | InvalidAlgorithmParameterException |
                NoSuchPaddingException | IllegalBlockSizeException | UnsupportedEncodingException e) {
            logger.debug("Registration failed", e);
            throw new RegisterException("Registration failed", e);
        }
    }
}
