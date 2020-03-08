package by.epam.traning.tarasiuk.hotel.service;

import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.service.exception.LogInException;
import by.epam.traning.tarasiuk.hotel.service.exception.RegisterException;

public interface GuestService {
    void signIn(String email, String password) throws LogInException, ConnectionException;

    void signUp(String name, String email, String password) throws RegisterException, ConnectionException;
}
