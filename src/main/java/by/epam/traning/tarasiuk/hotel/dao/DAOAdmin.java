package by.epam.traning.tarasiuk.hotel.dao;

import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.BlackList;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.entity.Order;
import java.sql.Connection;

import java.util.List;

public interface DAOAdmin {
    boolean addBlackList(int id, String reason) throws ConnectionException;

    List<BlackList> getBlackList() throws ConnectionException;

    List<Order> getAllOrders() throws ConnectionException;

    List<Client> getAllClients() throws ConnectionException;

    List<Client.Passport> getAllPassports() throws ConnectionException;
}
