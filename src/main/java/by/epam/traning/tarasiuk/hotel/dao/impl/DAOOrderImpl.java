package by.epam.traning.tarasiuk.hotel.dao.impl;

import by.epam.traning.tarasiuk.hotel.dao.DAOOrder;
import by.epam.traning.tarasiuk.hotel.dao.connectionPool.ConnectionPool;
import by.epam.traning.tarasiuk.hotel.dao.connectionPool.exception.PoolException;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.entity.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOOrderImpl implements DAOOrder {
    private final Logger logger = LogManager.getLogger("DaoLayer");

    private static final String ORDER = "INSERT INTO booking(user_id, number_id, first_day, last_day) " +
            "VALUES (?, ?, ?, ?)";
    private static final String CANCEL = "DELETE FROM booking WHERE user_id = ? AND first_day = ? AND last_day = ?";
    private static final String DATE = "SELECT number_id, first_day, last_day FROM booking WHERE first_day >= ? AND last_day <= ? and number_id = ?";
    private static final String ORDER_DATE = "SELECT number_id FROM booking WHERE first_day = ? AND last_day = ?";
    private static final String CLIENTS_ORDER = "SELECT first_day, last_day FROM booking WHERE user_id = ?";

    private static final DAOOrderImpl INSTANCE = new DAOOrderImpl();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private DAOOrderImpl() {

    }

    public static DAOOrderImpl getInstance() {
        return INSTANCE;
    }

    /**
     * @param order - Object order for booking
     * @return boolean value for check that booking successful or no
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public boolean makeAnOrder(Order order) throws ConnectionException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ORDER)) {

            if (!isCorrectOrder(connection, order.getRoom(), order.getFirstday(), order.getLastday())) {
                preparedStatement.setInt(1, order.getClient());
                preparedStatement.setInt(2, order.getRoom());
                preparedStatement.setDate(3, order.getFirstday());
                preparedStatement.setDate(4, order.getLastday());
                preparedStatement.executeUpdate();
                return true;
            }
            logger.debug("There is incorrect order");
            return false;
        } catch (SQLException | PoolException e) {
            logger.error("exception: ", e);
            throw new ConnectionException(e);
        }
    }

    /**
     * @param client    - Object client who want to cancel booking
     * @param first_day - the first day to booking
     * @param last_day  - the last day to booking
     * @return boolean value for check that booking cancel successful or no
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public boolean cancelOrder(Client client, Date first_day, Date last_day) throws ConnectionException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CANCEL)) {
            preparedStatement.setInt(1, client.getId());
            preparedStatement.setDate(2, first_day);
            preparedStatement.setDate(3, last_day);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException | PoolException e) {
            logger.error("exception: ", e);
            throw new ConnectionException(e);
        }
    }

    /**
     * @param first_day - the first day to booking
     * @param last_day  - the last day to booking
     * @return List with orders for check empty rooms
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public List<Order> getOrderByDate(Date first_day, Date last_day) throws ConnectionException {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ORDER_DATE)) {
            preparedStatement.setDate(1, first_day);
            preparedStatement.setDate(2, last_day);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setRoom(resultSet.getInt(1));
                    orders.add(order);
                }
                return orders;
            }
        } catch (SQLException | PoolException e) {
            logger.error("exception: ", e);
            throw new ConnectionException(e);
        }
    }

    /**
     * @param client - Object client who wants to see his orders
     * @return List with client's orders
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public List<Order> getOrderForClient(Client client) throws ConnectionException {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CLIENTS_ORDER)) {
            preparedStatement.setInt(1, client.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setFirstday(resultSet.getDate(1));
                    order.setLastday(resultSet.getDate(2));
                    orders.add(order);
                }
                return orders;
            }
        } catch (SQLException | PoolException e) {
            logger.error("exception: ", e);
            throw new ConnectionException(e);
        }
    }

    /**
     * @param connection - connect to database
     * @param first      - the first day to booking
     * @param last       - the last day to booking
     * @return boolean value for check that client done correct order
     * @throws SQLException - exception with database connection
     */
    private boolean isCorrectOrder(Connection connection, int number_id, Date first, Date last) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DATE)) {
            preparedStatement.setDate(1, first);
            preparedStatement.setDate(2, last);
            preparedStatement.setInt(3, number_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}
