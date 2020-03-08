package by.epam.traning.tarasiuk.hotel.service.impl;

import by.epam.traning.tarasiuk.hotel.dao.DAOClient;
import by.epam.traning.tarasiuk.hotel.dao.DAOOrder;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.dao.factory.DAOFactory;
import by.epam.traning.tarasiuk.hotel.dao.factory.impl.DAOFactoryImpl;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.entity.Order;
import by.epam.traning.tarasiuk.hotel.service.OrderService;
import by.epam.traning.tarasiuk.hotel.service.exception.OrderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static final OrderServiceImpl INSTANCE = new OrderServiceImpl();
    private final Logger logger = LogManager.getLogger("ServiceLayer");
    private DAOFactory factory = DAOFactoryImpl.getInstance();
    private DAOOrder daoOrder = factory.getDAOOrder();

    private OrderServiceImpl() {
    }

    public static OrderServiceImpl getInstance() {
        return INSTANCE;
    }

    /**
     * @param client - Object client who want to booking room
     * @param first  - the first day to booking
     * @param last   - the last day to booking
     * @throws OrderException      - exception with booking
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public void makeAnOrder(int client, int number_id, Date first, Date last) throws OrderException, ConnectionException {
        DAOFactory factory = DAOFactoryImpl.getInstance();
        DAOClient daoClient = factory.getDAOClient();

        if (!daoClient.getPassport(client)) {
            logger.debug("passport is not registration");
            throw new OrderException("You need to registration passport");
        }

        Order order = new Order();
        order.setClient(client);
        order.setRoom(number_id);
        order.setFirstday(first);
        order.setLastday(last);

        if (!daoOrder.makeAnOrder(order)) {
            logger.debug("Order failed");
            throw new OrderException("Order failed");
        }
    }

    /**
     * @param client    - Object client who want to cancel booking
     * @param first_day - the first day to booking
     * @param last_day  - the last day to booking
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public void cancelOrder(Client client, Date first_day, Date last_day) throws ConnectionException, OrderException {
        if (!daoOrder.cancelOrder(client, first_day, last_day)) {
            logger.debug("Cancel order failed");
            throw new OrderException("Cancel order failed");
        }
    }

    /**
     * @param first_day - the first day to booking
     * @param last_day  - the last day to booking
     * @return List with orders which include first_day or last_day
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public List<Order> getOrderByDate(Date first_day, Date last_day) throws ConnectionException {
        return daoOrder.getOrderByDate(first_day, last_day);
    }

    /**
     * @param client - Client who want to get his orders
     * @return List with client's orders
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public List<Order> getOrderForClient(Client client) throws ConnectionException {
        return daoOrder.getOrderForClient(client);
    }

}
