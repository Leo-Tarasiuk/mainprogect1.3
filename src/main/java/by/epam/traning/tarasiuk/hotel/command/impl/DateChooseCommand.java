package by.epam.traning.tarasiuk.hotel.command.impl;

import by.epam.traning.tarasiuk.hotel.command.Command;
import by.epam.traning.tarasiuk.hotel.command.exception.CommandException;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.entity.Room;
import by.epam.traning.tarasiuk.hotel.util.DateParser;
import by.epam.traning.tarasiuk.hotel.util.SortByDate;
import by.epam.traning.tarasiuk.hotel.util.exception.DateParserException;
import by.epam.traning.tarasiuk.hotel.util.exception.SortByDateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.Set;

public class DateChooseCommand implements Command {
    private final Logger logger = LogManager.getLogger("Command");

    private static final String CLIENT = "client";
    private static final String FIRST_DAY = "first_day";
    private static final String LAST_DAY = "last_day";
    private static final String ROOMS = "rooms";
    private static final String ROOM_PAGE = "roomPage.jsp";
    private static final String USER_ROOM_PAGE = "userRoomPage.jsp";

    private HttpServletRequest request;
    private HttpServletResponse response;

    public DateChooseCommand(HttpServletRequest request, HttpServletResponse response) {
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
            HttpSession session = request.getSession();
            Client client = (Client) session.getAttribute(CLIENT);
            String first_day = request.getParameter(FIRST_DAY);
            String last_day = request.getParameter(LAST_DAY);

            DateParser dateParser = new DateParser();
            Date first = dateParser.parse(first_day);
            Date last = dateParser.parse(last_day);
            SortByDate sortByDate = new SortByDate();
            Set<Room> rooms = sortByDate.getSortRoom(first, last);
            request.setAttribute(ROOMS, rooms);
            if (client != null) {
                request.getRequestDispatcher(ROOM_PAGE).forward(request, response);
            } else {
                request.getRequestDispatcher(USER_ROOM_PAGE).forward(request, response);
            }
            logger.info("Rooms was sorted");
        } catch (ServletException | IOException | ParseException | DateParserException | SortByDateException e) {
            logger.info("Sorting failed: ", e);
            throw new CommandException("Sorting failed", e);
        }
    }
}
