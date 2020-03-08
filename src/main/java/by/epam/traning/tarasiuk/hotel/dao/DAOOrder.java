package by.epam.traning.tarasiuk.hotel.dao;


import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.entity.Order;

import java.util.List;
import java.sql.Date;

public interface DAOOrder {
    boolean makeAnOrder(Order order) throws ConnectionException;

    boolean cancelOrder(Client client, Date first_day, Date last_day) throws ConnectionException;

    List<Order> getOrderByDate(Date first_day, Date last_day) throws ConnectionException;

    List<Order> getOrderForClient(Client client) throws ConnectionException;
}
