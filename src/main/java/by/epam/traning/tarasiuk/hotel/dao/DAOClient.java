package by.epam.traning.tarasiuk.hotel.dao;

import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.Client;

public interface DAOClient {
    boolean signIn(Client client) throws ConnectionException;

    boolean signUp(Client client) throws ConnectionException;

    boolean changeData(Client client, String parameter) throws ConnectionException;

    boolean inputPassport(Client.Passport passport) throws ConnectionException;

    boolean getPassport(int client) throws ConnectionException;

    String getUserInformation(String name) throws ConnectionException;
}
