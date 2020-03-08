package by.epam.traning.tarasiuk.hotel.dao.factory;

import by.epam.traning.tarasiuk.hotel.dao.DAOAdmin;
import by.epam.traning.tarasiuk.hotel.dao.DAOClient;
import by.epam.traning.tarasiuk.hotel.dao.DAOHotel;
import by.epam.traning.tarasiuk.hotel.dao.DAOOrder;

public interface DAOFactory {
    DAOAdmin getDAOAdmin();

    DAOClient getDAOClient();

    DAOOrder getDAOOrder();

    DAOHotel getDAOHotel();
}
