package by.epam.traning.tarasiuk.hotel.service.impl;

import by.epam.traning.tarasiuk.hotel.dao.DAOHotel;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.dao.factory.DAOFactory;
import by.epam.traning.tarasiuk.hotel.dao.factory.impl.DAOFactoryImpl;
import by.epam.traning.tarasiuk.hotel.entity.Room;
import by.epam.traning.tarasiuk.hotel.service.HotelService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class HotelServiceImpl implements HotelService {
    private final Logger logger = LogManager.getLogger("ServiceLayer");
    private static final HotelServiceImpl INSTANCE = new HotelServiceImpl();

    private DAOFactory factory = DAOFactoryImpl.getInstance();
    private DAOHotel daoHotel = factory.getDAOHotel();

    private HotelServiceImpl() {
    }

    public static HotelServiceImpl getInstance() {
        return INSTANCE;
    }

    /**
     * @return List with hotel rooms
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public List<Room> getRooms() throws ConnectionException {
        return daoHotel.getRooms();
    }

    /**
     * @return List with conveniences in hotel
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public List<String> getConvenience() throws ConnectionException {
        return daoHotel.getConvenience();
    }
}
