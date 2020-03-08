package by.epam.traning.tarasiuk.hotel.util;

import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.Order;
import by.epam.traning.tarasiuk.hotel.entity.Room;
import by.epam.traning.tarasiuk.hotel.service.HotelService;
import by.epam.traning.tarasiuk.hotel.service.OrderService;
import by.epam.traning.tarasiuk.hotel.service.factory.ServiceFactory;
import by.epam.traning.tarasiuk.hotel.service.factory.impl.ServiceFactoryImpl;
import by.epam.traning.tarasiuk.hotel.util.exception.SortByDateException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SortByDate {
    /**
     * @param first_day - first booking day
     * @param last_day  - last booking day
     * @return Set with rooms
     */
    public Set<Room> getSortRoom(Date first_day, Date last_day) throws SortByDateException {
        if (first_day == null || last_day == null) {
            throw new SortByDateException("The date is incorrect");
        }
        List<Room> roomList = new ArrayList<>();

        try {
            ServiceFactory factory = ServiceFactoryImpl.getInstance();
            OrderService orderService = factory.getOrderService();
            HotelService hotelService = factory.getHotelService();

            List<Order> orders = orderService.getOrderByDate(first_day, last_day);

            roomList = hotelService.getRooms();

            for (Order order : orders) {
                for (int i = 0; i < roomList.size(); i++) {
                    if (roomList.get(i).getId() == order.getRoom()) {
                        roomList.remove(roomList.get(i));
                    }
                }
            }
        } catch (ConnectionException e) {
            e.printStackTrace();
        }

        return new HashSet<>(roomList);
    }

}
