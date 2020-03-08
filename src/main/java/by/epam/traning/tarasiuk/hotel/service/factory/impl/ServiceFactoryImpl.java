package by.epam.traning.tarasiuk.hotel.service.factory.impl;

import by.epam.traning.tarasiuk.hotel.service.AdminService;
import by.epam.traning.tarasiuk.hotel.service.GuestService;
import by.epam.traning.tarasiuk.hotel.service.HotelService;
import by.epam.traning.tarasiuk.hotel.service.OrderService;
import by.epam.traning.tarasiuk.hotel.service.UserService;
import by.epam.traning.tarasiuk.hotel.service.factory.ServiceFactory;
import by.epam.traning.tarasiuk.hotel.service.impl.AdminServiceImpl;
import by.epam.traning.tarasiuk.hotel.service.impl.GuestServiceImpl;
import by.epam.traning.tarasiuk.hotel.service.impl.HotelServiceImpl;
import by.epam.traning.tarasiuk.hotel.service.impl.OrderServiceImpl;
import by.epam.traning.tarasiuk.hotel.service.impl.UserServiceImpl;

public class ServiceFactoryImpl implements ServiceFactory {
    private static final ServiceFactoryImpl INSTANCE = new ServiceFactoryImpl();

    private ServiceFactoryImpl() {

    }

    public static ServiceFactoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public AdminService getAdminService() {
        return AdminServiceImpl.getInstance();
    }

    @Override
    public UserService getUserService() {
        return UserServiceImpl.getInstance();
    }

    @Override
    public GuestService getGuestService() {
        return GuestServiceImpl.getInstance();
    }

    @Override
    public OrderService getOrderService() {
        return OrderServiceImpl.getInstance();
    }

    @Override
    public HotelService getHotelService() {
        return HotelServiceImpl.getInstance();
    }

}
