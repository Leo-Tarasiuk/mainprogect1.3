package by.epam.traning.tarasiuk.hotel.service;

import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.entity.Order;
import by.epam.traning.tarasiuk.hotel.service.exception.OrderException;

import java.sql.Date;
import java.util.List;

public interface OrderService {
    void makeAnOrder(int client_id, int number_id, Date first, Date last) throws OrderException, ConnectionException;
    void cancelOrder(Client client, Date first_day, Date last_day) throws ConnectionException, OrderException;

    List<Order> getOrderByDate(Date first_day, Date last_day) throws ConnectionException;

    List<Order> getOrderForClient(Client client) throws ConnectionException;
}
