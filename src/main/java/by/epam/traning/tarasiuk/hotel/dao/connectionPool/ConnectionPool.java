package by.epam.traning.tarasiuk.hotel.dao.connectionPool;

import by.epam.traning.tarasiuk.hotel.dao.connectionPool.exception.PoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger("DAOLayer");
    private static ConnectionPool instance = null;
    private static ReentrantLock Lock = new ReentrantLock();
    private static AtomicBoolean create = new AtomicBoolean(false);
    private BlockingQueue<ProxyConnection> availableConnections;

    private ConnectionPool() {
        init();
    }

    public static ConnectionPool getInstance() {
        if (!create.get()) {
            try {
                Lock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    create.set(true);
                }
            } finally {
                Lock.unlock();
            }
        }
        return instance;
    }

    private void init() {
        availableConnections = new LinkedBlockingDeque<>();
        try {
            int poolSize = Integer.parseInt(DatabaseManager.getProperty(DatabaseManager.POOL_SIZE));
            String url = DatabaseManager.getProperty(DatabaseManager.URL);
            Properties properties = getDatabaseProperties();
            Class.forName(DatabaseManager.getProperty(DatabaseManager.DRIVER_NAME));
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, properties);
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                availableConnections.add(proxyConnection);
            }
        } catch (SQLException e) {
            logger.fatal("Cannot create connection pool: SQL exception\n", e);
        } catch (ClassNotFoundException e) {
            logger.fatal("Cannot create connection pool: failed loading driver\n", e);
        }
        logger.info("Connection pool created successfully");
    }

    public Connection getConnection() throws PoolException {
        ProxyConnection connection;
        try {
            connection = availableConnections.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new PoolException("Pool overflow");
        }

        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection) {
            ProxyConnection proxyConnection = (ProxyConnection) connection;
            availableConnections.offer(proxyConnection);
        }
    }

    public void closePool() {
        deregisterDriver();
        ProxyConnection connection;
        int poolSize = Integer.parseInt(DatabaseManager.getProperty(DatabaseManager.POOL_SIZE));
        for (int i = 0; i < poolSize; i++) {
            try {
                connection = availableConnections.take();
                connection.realClose();
            } catch (SQLException e) {
                logger.error("Cannot close connection.\n", e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        logger.info("Connection pool successfully closed");
    }

    private Properties getDatabaseProperties() {
        Properties properties = new Properties();
        properties.put(DatabaseManager.USER, DatabaseManager.getProperty(DatabaseManager.USER));
        properties.put(DatabaseManager.PASSWORD, DatabaseManager.getProperty(DatabaseManager.PASSWORD));
        properties.put(DatabaseManager.AUTORECONNECT, DatabaseManager.getProperty(DatabaseManager.AUTORECONNECT));
        properties.put(DatabaseManager.ENCODING, DatabaseManager.getProperty(DatabaseManager.ENCODING));
        properties.put(DatabaseManager.USE_UNICODE, DatabaseManager.getProperty(DatabaseManager.USE_UNICODE));
        return properties;
    }

    private void deregisterDriver() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                logger.info(String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                logger.info(String.format("Error deregistering driver %s", driver), e);
            }
        }
    }

    public void closeConnection(Connection currentConnection) {
        try {
            if (currentConnection != null) {
                currentConnection.close();
            }
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public void closePreparedStatement(PreparedStatement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public void closeAll(ResultSet resultSet, PreparedStatement statement, Connection connection) {
        closeResultSet(resultSet);
        closePreparedStatement(statement);
        closeConnection(connection);
    }

    public void closeAll(ResultSet resultSet, Statement statement, Connection connection) {
        closeResultSet(resultSet);
        closeStatement(statement);
        closeConnection(connection);
    }
}
