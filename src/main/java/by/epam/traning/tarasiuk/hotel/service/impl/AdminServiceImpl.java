package by.epam.traning.tarasiuk.hotel.service.impl;

import by.epam.traning.tarasiuk.hotel.dao.DAOAdmin;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.dao.factory.DAOFactory;
import by.epam.traning.tarasiuk.hotel.dao.factory.impl.DAOFactoryImpl;
import by.epam.traning.tarasiuk.hotel.entity.BlackList;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.entity.Order;
import by.epam.traning.tarasiuk.hotel.service.AdminService;
import by.epam.traning.tarasiuk.hotel.service.exception.BanedException;
import by.epam.traning.tarasiuk.hotel.service.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AdminServiceImpl implements AdminService {
    private final Logger logger = LogManager.getLogger("ServiceLayer");

    private static final AdminServiceImpl INSTANCE = new AdminServiceImpl();

    private DAOFactory factory = DAOFactoryImpl.getInstance();
    private DAOAdmin daoAdmin = factory.getDAOAdmin();

    private AdminServiceImpl() {

    }

    public static AdminServiceImpl getInstance() {
        return INSTANCE;
    }


    /**
     * @param id     - client identifier for adding to black list
     * @param reason - a description of why the client is banned
     * @throws BanedException      - exception with adding client in black list
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public void addBlackList(int id, String reason) throws BanedException, ConnectionException {
        if (!Validator.isValidId(id)) {
            logger.debug("Incorrect id");
            throw new BanedException("Incorrect id");
        }

        if (!daoAdmin.addBlackList(id, reason)) {
            logger.debug("Invalid id");
            throw new BanedException("Invalid id");
        }
    }

    /**
     * @return List with banned clients
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public List<BlackList> getBlackList() throws ConnectionException {
        return daoAdmin.getBlackList();
    }

    /**
     * @return List with orders
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public List<Order> getAllOrders() throws ConnectionException {
        return daoAdmin.getAllOrders();
    }

    /**
     * @return List with clients
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public List<Client> getAllClient() throws ConnectionException {
        return daoAdmin.getAllClients();
    }

    /**
     *
     * @return List with client's passports
     * @throws ConnectionException - exception with database connection
     */
    @Override
    public List<Client.Passport> getAllPassports() throws ConnectionException {
        return daoAdmin.getAllPassports();
    }
}
