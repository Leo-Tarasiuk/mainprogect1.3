package by.epam.traning.tarasiuk.hotel.service;

import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.Room;

import java.util.List;

public interface HotelService {
    List<Room> getRooms() throws ConnectionException;

    List<String> getConvenience() throws ConnectionException;
}
