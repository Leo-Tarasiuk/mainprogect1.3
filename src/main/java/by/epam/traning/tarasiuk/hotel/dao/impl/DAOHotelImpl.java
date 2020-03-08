package by.epam.traning.tarasiuk.hotel.dao.impl;

import by.epam.traning.tarasiuk.hotel.dao.DAOHotel;
import by.epam.traning.tarasiuk.hotel.dao.connectionPool.ConnectionPool;
import by.epam.traning.tarasiuk.hotel.dao.connectionPool.exception.PoolException;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOHotelImpl implements DAOHotel {
    private static final String ALL_ROOMS = "SELECT DISTINCT number_id, placement, comfort.comfort, number_persons, price, description, img " +
            "FROM room  INNER JOIN comfort ON room.comfort_id = comfort.comfort_id";
    //    private static final String COMFORT = "SELECT comfort_id FROM room WHERE number_id = ?";
    private static final String CONVENIENCE = "SELECT convenience FROM convenience";
    private static final DAOHotelImpl INSTANCE = new DAOHotelImpl();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final Logger logger = LogManager.getLogger("DaoLayer");

    private DAOHotelImpl() {

    }

    public static DAOHotelImpl getInstance() {
        return INSTANCE;
    }

    /**
     * @return List with hotel rooms
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public List<Room> getRooms() throws ConnectionException {
        List<Room> rooms = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(ALL_ROOMS)) {
            while (resultSet.next()) {
                Room room = new Room();
                room.setId(resultSet.getInt(1));
                room.setPlacement(resultSet.getString(2));
                room.setComfort(resultSet.getString(3));
                room.setPerson(resultSet.getInt(4));
                room.setPrice(resultSet.getDouble(5));
                room.setDescription(resultSet.getString(6));
                room.setPath(resultSet.getString(7));
                rooms.add(room);
            }
            return rooms;
        } catch (SQLException | PoolException e) {
            logger.error("exception: ", e);
            throw new ConnectionException(e);
        }
    }

    /**
     * @return List with conveniences
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public List<String> getConvenience() throws ConnectionException {
        List<String> convenience = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(CONVENIENCE)) {
            while (resultSet.next()) {
                String str = resultSet.getString(1);
                convenience.add(str);
            }
        } catch (SQLException | PoolException e) {
            logger.error("exception: ", e);
            throw new ConnectionException(e);
        }
        return convenience;
    }
}
