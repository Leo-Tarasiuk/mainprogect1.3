package by.epam.traning.tarasiuk.hotel.dao.connectionPool;

import java.util.ResourceBundle;

public class DatabaseManager {
    /**
     * Represents name of JDBC Driver of database used
     */
    public static final String DRIVER_NAME = "driver";

    /**
     * Represents type of DAO
     */
    public static final String DATABASE_TYPE = "databaseType";

    /**
     * Represents username of user to access to database
     */
    public static final String USER = "user";

    /**
     * Represents password of user to access to database
     */
    public static final String PASSWORD = "password";

    /**
     * Represents maximum amount of connection which are created and used in web app
     */
    public static final String POOL_SIZE = "poolSize";

    /**
     * Represents connection url to database
     */
    public static final String URL = "url";

    /**
     * Represents database encoding
     */
    public static final String ENCODING = "characterEncoding";

    /**
     * Represents if JDBC need to automatically reconnect to database
     */
    public static final String AUTORECONNECT = "autoReconnect";

    /**
     * Represents if database use unicode (see {@link #ENCODING})
     */
    public static final String USE_UNICODE = "useUnicode";

    private static final String RESOURCE_NAME = "database";

    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME);

    private DatabaseManager() {
    }

    /**
     * Returns database property value with specified key
     *
     * @param key key of the property, recommended to use constants.
     * @return value of property with the specified key
     */
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
