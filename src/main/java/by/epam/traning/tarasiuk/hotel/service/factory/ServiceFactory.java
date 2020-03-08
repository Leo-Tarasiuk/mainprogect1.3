package by.epam.traning.tarasiuk.hotel.service.factory;


import by.epam.traning.tarasiuk.hotel.service.AdminService;
import by.epam.traning.tarasiuk.hotel.service.GuestService;
import by.epam.traning.tarasiuk.hotel.service.HotelService;
import by.epam.traning.tarasiuk.hotel.service.OrderService;
import by.epam.traning.tarasiuk.hotel.service.UserService;

public interface ServiceFactory {
    AdminService getAdminService();

    UserService getUserService();

    GuestService getGuestService();

    OrderService getOrderService();

    HotelService getHotelService();
}
