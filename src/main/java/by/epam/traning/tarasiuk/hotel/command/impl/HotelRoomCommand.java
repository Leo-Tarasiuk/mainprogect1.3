package by.epam.traning.tarasiuk.hotel.command.impl;

import by.epam.traning.tarasiuk.hotel.command.Command;
import by.epam.traning.tarasiuk.hotel.command.exception.CommandException;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.entity.Room;
import by.epam.traning.tarasiuk.hotel.service.HotelService;
import by.epam.traning.tarasiuk.hotel.service.UserService;
import by.epam.traning.tarasiuk.hotel.service.factory.ServiceFactory;
import by.epam.traning.tarasiuk.hotel.service.factory.impl.ServiceFactoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HotelRoomCommand implements Command {
    private final Logger logger = LogManager.getLogger("Command");

    private static final String ROOM = "rooms";
    private static final String CLIENT = "client";
    private static final String ROOM_PAGE = "roomPage.jsp";
    private static final String ROOM_EXPERT_PAGE = "roomPageExpert.jsp";
    private static final String USER_PAGE = "userRoomPage.jsp";

    private HttpServletRequest request;
    private HttpServletResponse response;

    public HotelRoomCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     *
     * @throws CommandException final wrapper for throws exception
     */
    @Override
    public void execute() throws CommandException {
        try {
            ServiceFactory factory = ServiceFactoryImpl.getInstance();
            HotelService hotelService = factory.getHotelService();
            UserService userService = factory.getUserService();

            List<Room> list = hotelService.getRooms();
            Set<Room> rooms = new HashSet<>(list);
            request.setAttribute(ROOM, rooms);
            HttpSession session = request.getSession();
            Client client = (Client) session.getAttribute(CLIENT);
            if (client != null) {
                if (userService.getPassport(client.getId())) {
                    request.getRequestDispatcher(ROOM_EXPERT_PAGE).forward(request, response);
                } else {
                    request.getRequestDispatcher(ROOM_PAGE).forward(request, response);
                }
            } else {
                request.getRequestDispatcher(USER_PAGE).forward(request, response);
            }
            logger.info("Hotel rooms return");
        } catch (ServletException | IOException e) {
            logger.info("get hotel rooms failed: ", e);
            throw new CommandException("get hotel rooms failed", e);
        } catch (ConnectionException e) {
            logger.error("Connect to database failed, error: ", e);
        }
    }
}
