package by.epam.traning.tarasiuk.hotel.dao.impl;

import by.epam.traning.tarasiuk.hotel.dao.DAOClient;
import by.epam.traning.tarasiuk.hotel.dao.connectionPool.ConnectionPool;
import by.epam.traning.tarasiuk.hotel.dao.connectionPool.exception.PoolException;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for CRUD information about client in database
 */
public class DAOClientImpl implements DAOClient {
    private static final String CHANGE_EMAIL = "UPDATE user SET email = ? WHERE last_name = ?";
    private static final String CHANGE_PASSWORD = "UPDATE user SET password = ? WHERE last_name = ?";
    private static final String SIGN_IN = "SELECT user_id, last_name FROM user WHERE email = ? AND password = ?";
    private static final String SIGN_UP = "INSERT INTO user (last_name, email, password) VALUES (?, ?, ?)";
    private static final String INSERT_PASSPORT = "INSERT INTO user_pasport VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_PASSPORT = "SELECT * FROM user_pasport WHERE user_pasport_id = ?";
    private static final String ALL_INFO = "SELECT user_id, last_name, email, role FROM user WHERE email = ?";
    private static final String MAIL = "SELECT email FROM user WHERE email = ?";
    private static final String BLACK_LIST = "SELECT user_id FROM black_list WHERE user_id = ?";
    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";

    private static final DAOClientImpl INSTANCE = new DAOClientImpl();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final Logger logger = LogManager.getLogger("DaoLayer");

    private DAOClientImpl() {

    }

    public static DAOClientImpl getInstance() {
        return INSTANCE;
    }

    /**
     * @param client - Object client who want to sign in
     * @return boolean value, which check that client sign in or no
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public boolean signIn(Client client) throws ConnectionException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SIGN_IN)) {

            statement.setString(1, client.getEmail());
            statement.setString(2, client.getPassword());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    if (isNotBannedPerson(connection, resultSet.getInt(1))) {
                        logger.debug("The banned person");
                        return false;
                    }
                    return true;
                }
                return false;
            }
        } catch (SQLException | PoolException e) {
            logger.error("exception: ", e);
            throw new ConnectionException(e);
        }
    }

    /**
     * @param client - Object client who want to sign up
     * @return boolean value, which check that client sign up or no
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public boolean signUp(Client client) throws ConnectionException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SIGN_UP)) {
            if (isNotSameMail(connection, client.getEmail())) {
                logger.debug("The same mail");
                return false;
            }
            preparedStatement.setString(1, client.getLastName());
            preparedStatement.setString(2, client.getEmail());
            preparedStatement.setString(3, client.getPassword());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException | PoolException e) {
            logger.error("exception: ", e);
            throw new ConnectionException(e);
        }
    }

    /**
     * @param client    - Object client who want to change email or password
     * @param parameter - value which client want to change
     * @return boolean value, which check that client change his data or no
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public boolean changeData(Client client, String parameter) throws ConnectionException {
        String statement = "";
        String newValue = "";

        try (Connection connection = connectionPool.getConnection()) {
            switch (parameter) {
                case EMAIL_PARAM:
                    statement = CHANGE_EMAIL;
                    newValue = client.getEmail();
                    if (isNotSameMail(connection, client.getEmail())) {
                        return false;
                    }
                    break;
                case PASSWORD_PARAM:
                    statement = CHANGE_PASSWORD;
                    newValue = client.getPassword();
                    break;
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.setString(1, newValue);
                preparedStatement.setString(2, client.getLastName());
                return preparedStatement.execute();
            }
        } catch (SQLException | PoolException e) {
            logger.error("exception: ", e);
            throw new ConnectionException(e);
        }
    }

    /**
     * @param passport - Client's passport
     * @return boolean value, which check that client add his passport data or no
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public boolean inputPassport(Client.Passport passport) throws ConnectionException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PASSPORT)) {
            preparedStatement.setInt(1, passport.getPassportId());
            preparedStatement.setString(2, passport.getLastName());
            preparedStatement.setString(3, passport.getName());
            preparedStatement.setString(4, passport.getPatronymic());
            preparedStatement.setString(5, passport.getCountry());
            preparedStatement.setDate(6, passport.getDateOfBirth());
            preparedStatement.setString(7, passport.getSex());
            preparedStatement.setString(8, passport.getIdentificationNo());
            preparedStatement.setString(9, passport.getPassportNo());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException | PoolException e) {
            logger.error("exception: ", e);
            throw new ConnectionException(e);
        }
    }

    /**
     * @param client - Object client who want to get his passport data
     * @return boolean value, which check that client get his passport data or no
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public boolean getPassport(int client) throws ConnectionException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_PASSPORT)) {
            preparedStatement.setInt(1, client);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException | PoolException e) {
            logger.error("exception: ", e);
            throw new ConnectionException(e);
        }
    }

    /**
     * @param email - Client's email
     * @return string value with all information about client but without his password
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public String getUserInformation(String email) throws ConnectionException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ALL_INFO)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                String result = "";
                while (resultSet.next()) {
                    result = resultSet.getInt(1) + " " + resultSet.getString(2) +
                            " " + resultSet.getString(3) + " " + resultSet.getString(4);
                }
                return result;
            }
        } catch (SQLException | PoolException e) {
            logger.error("exception: ", e);
            throw new ConnectionException(e);
        }
    }

    /**
     * @param connection - connect to database
     * @param email      - Client's email
     * @return boolean value, which check that client add not same mail
     * @throws SQLException - exception with database connection
     */
    private boolean isNotSameMail(Connection connection, String email) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(MAIL)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    /**
     * @param connection - connect to database
     * @param id         - Client's id
     * @return boolean value, which check that client is not banned
     * @throws SQLException - exception with database connection
     */
    private boolean isNotBannedPerson(Connection connection, int id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(BLACK_LIST)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}
