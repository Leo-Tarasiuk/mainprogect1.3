package by.epam.traning.tarasiuk.hotel.util;

import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.Order;
import by.epam.traning.tarasiuk.hotel.entity.Room;
import by.epam.traning.tarasiuk.hotel.service.HotelService;
import by.epam.traning.tarasiuk.hotel.service.OrderService;
import by.epam.traning.tarasiuk.hotel.service.factory.ServiceFactory;
import by.epam.traning.tarasiuk.hotel.service.factory.impl.ServiceFactoryImpl;
import by.epam.traning.tarasiuk.hotel.util.exception.SortByDateException;
import org.testng.annotations.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.*;

public class SortByDateTest {

    @Test
    public void testGetSortRoom() {
        ServiceFactory factory = ServiceFactoryImpl.getInstance();
        HotelService hotelService = factory.getHotelService();

        SortByDate sortByDate = new SortByDate();

        Date first = new Date(2020, 3, 6);
        Date last = new Date(2020, 3, 13);

        try {
            List<Room> rooms = hotelService.getRooms();
            Set<Room> expected = new HashSet<>(rooms);
            Set<Room> result = sortByDate.getSortRoom(first, last);
            assertEquals(result, expected);
        } catch (ConnectionException | SortByDateException e) {
            e.printStackTrace();
        }

    }
}