package by.epam.traning.tarasiuk.hotel.dao.factory.impl;

import by.epam.traning.tarasiuk.hotel.dao.DAOAdmin;
import by.epam.traning.tarasiuk.hotel.dao.DAOClient;
import by.epam.traning.tarasiuk.hotel.dao.DAOHotel;
import by.epam.traning.tarasiuk.hotel.dao.DAOOrder;
import by.epam.traning.tarasiuk.hotel.dao.factory.DAOFactory;
import by.epam.traning.tarasiuk.hotel.dao.impl.DAOAdminImpl;
import by.epam.traning.tarasiuk.hotel.dao.impl.DAOClientImpl;
import by.epam.traning.tarasiuk.hotel.dao.impl.DAOHotelImpl;
import by.epam.traning.tarasiuk.hotel.dao.impl.DAOOrderImpl;

public class DAOFactoryImpl implements DAOFactory {
    private static final DAOFactoryImpl INSTANCE = new DAOFactoryImpl();

    private DAOFactoryImpl() {

    }

    public static DAOFactoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public DAOAdmin getDAOAdmin() {
        return DAOAdminImpl.getInstance();
    }

    @Override
    public DAOClient getDAOClient() {
        return DAOClientImpl.getInstance();
    }

    @Override
    public DAOOrder getDAOOrder() {
        return DAOOrderImpl.getInstance();
    }

    @Override
    public DAOHotel getDAOHotel() {
        return DAOHotelImpl.getInstance();
    }
}
