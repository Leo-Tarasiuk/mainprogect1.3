package by.epam.traning.tarasiuk.hotel.dao.impl;

import by.epam.traning.tarasiuk.hotel.dao.DAOAdmin;
import by.epam.traning.tarasiuk.hotel.dao.connectionPool.ConnectionPool;
import by.epam.traning.tarasiuk.hotel.dao.connectionPool.exception.PoolException;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.BlackList;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.entity.Order;
import by.epam.traning.tarasiuk.hotel.entity.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOAdminImpl implements DAOAdmin {
    private static final String BLACKLIST = "SELECT * FROM black_list";
    private static final String SET_BLACKLIST = "INSERT INTO black_list (user_id, reason) VALUES (?, ?)";
    private static final String USER_ID = "SELECT user_id FROM user WHERE user_id = ?";
    private static final String ALL_ORDERS = "SELECT * FROM booking";
    private static final String ALL_USERS = "SELECT user_id, last_name, email, role FROM user";
    private static final String ALL_PASSPORTS = "SELECT * FROM user_pasport";

    private static final DAOAdminImpl INSTANCE = new DAOAdminImpl();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final Logger logger = LogManager.getLogger("DaoLayer");

    private DAOAdminImpl() {

    }

    public static DAOAdminImpl getInstance() {
        return INSTANCE;
    }

    /**
     * @param id     - client identifier for adding to black list
     * @param reason - a description of why the client is banned
     * @return boolean value, which check that client banned
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public boolean addBlackList(int id, String reason) throws ConnectionException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_BLACKLIST)) {
            if (checkId(connection, id)) {
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, reason);
                preparedStatement.executeUpdate();
                return true;
            }
            return false;
        } catch (SQLException | PoolException e) {
            logger.error("Add in the black list exception: ", e);
            throw new ConnectionException(e);
        }
    }

    /**
     * @param connection - connect to database
     * @param id         - client identifier for adding to black list
     * @return boolean value, which check that client is alive
     * @throws ConnectionException - exception with database connection
     */
    private boolean checkId(Connection connection, int id) throws ConnectionException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(USER_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            logger.error("Check id exception: ", e);
            throw new ConnectionException(e);
        }
    }

    /**
     * @return List with banned clients
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public List<BlackList> getBlackList() throws ConnectionException {
        List<BlackList> banlist = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(BLACKLIST)) {
            while (resultSet.next()) {
                BlackList blackList = new BlackList();
                blackList.setId(resultSet.getInt(1));
                blackList.setClient(resultSet.getInt(2));
                blackList.setReason(resultSet.getString(3));
                banlist.add(blackList);
            }
            return banlist;
        } catch (SQLException | PoolException e) {
            logger.error("Get black list exception: ", e);
            throw new ConnectionException(e);
        }
    }

    /**
     * @return List with orders
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public List<Order> getAllOrders() throws ConnectionException {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(ALL_ORDERS)) {
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt(1));
                order.setClient(resultSet.getInt(2));
                order.setRoom(resultSet.getInt(3));
                order.setFirstday(resultSet.getDate(4));
                order.setLastday(resultSet.getDate(5));
                orders.add(order);
            }
            return orders;
        } catch (SQLException | PoolException e) {
            logger.error("Get orders exception: ", e);
            throw new ConnectionException(e);
        }
    }

    /**
     * @return List with clients
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public List<Client> getAllClients() throws ConnectionException {
        List<Client> clientList = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(ALL_USERS)) {
            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt(1));
                client.setLastName(resultSet.getString(2));
                client.setEmail(resultSet.getString(3));
                client.setRole(UserRole.getUserRole(resultSet.getString(4)));
                clientList.add(client);
            }
            return clientList;
        } catch (SQLException | PoolException e) {
            logger.error("Get client exception: ", e);
            throw new ConnectionException(e);
        }
    }

    /**
     * @return List with clients passports
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public List<Client.Passport> getAllPassports() throws ConnectionException {
        List<Client.Passport> passports = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(ALL_PASSPORTS)) {
            while (resultSet.next()) {
                Client.Passport passport = new Client().new Passport();

                passport.setPassportId(resultSet.getInt(1));
                passport.setLastName(resultSet.getString(2));
                passport.setName(resultSet.getString(3));
                passport.setPatronymic(resultSet.getString(4));
                passport.setCountry(resultSet.getString(5));
                passport.setDateOfBirth(resultSet.getDate(6));
                passport.setSex(resultSet.getString(7));
                passport.setIdentificationNo(resultSet.getString(8));
                passport.setPassportNo(resultSet.getString(9));

                passports.add(passport);
            }
            return passports;
        } catch (SQLException | PoolException e) {
            logger.error("Sql error: " + e.getMessage());
            throw new ConnectionException("Sql error: " + e.getMessage());
        }
    }
}
