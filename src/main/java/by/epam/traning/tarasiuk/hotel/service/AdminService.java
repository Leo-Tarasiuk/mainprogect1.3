package by.epam.traning.tarasiuk.hotel.service;

import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.BlackList;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.entity.Order;
import by.epam.traning.tarasiuk.hotel.service.exception.BanedException;

import java.util.List;

public interface AdminService {
    void addBlackList(int id, String reason) throws BanedException, ConnectionException;

    List<BlackList> getBlackList() throws ConnectionException;

    List<Order> getAllOrders() throws ConnectionException;

    List<Client> getAllClient() throws ConnectionException;

    List<Client.Passport> getAllPassports() throws ConnectionException;
}
