package by.epam.traning.tarasiuk.hotel.service;

import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.service.exception.PassportException;
import by.epam.traning.tarasiuk.hotel.service.exception.UpdateException;

import java.sql.Date;

public interface UserService {
    void changeMail(Client client, String email) throws UpdateException, ConnectionException;

    void changePassword(Client client, String password) throws UpdateException, ConnectionException;

    void inputPassport(Client client, String lastName, String name, String patronymic,
                       String country, Date dateBirth, String sex,
                       String identificationNo, String pasportNo) throws ConnectionException, PassportException;

    boolean getPassport(int client) throws ConnectionException;
}
