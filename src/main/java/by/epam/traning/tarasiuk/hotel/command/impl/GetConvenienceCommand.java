package by.epam.traning.tarasiuk.hotel.command.impl;

import by.epam.traning.tarasiuk.hotel.command.Command;
import by.epam.traning.tarasiuk.hotel.command.exception.CommandException;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.service.HotelService;
import by.epam.traning.tarasiuk.hotel.service.factory.ServiceFactory;
import by.epam.traning.tarasiuk.hotel.service.factory.impl.ServiceFactoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class GetConvenienceCommand implements Command {
    private final Logger logger = LogManager.getLogger("Command");

    private static final String CLIENT = "client";
    private static final String CONVENIENCE = "convenience";
    private static final String CONVENIENCE_PAGE = "convenience.jsp";
    private static final String CONVENIENCE_CLIENT_PAGE = "convenienceClient.jsp";

    private HttpServletRequest request;
    private HttpServletResponse response;

    public GetConvenienceCommand(HttpServletRequest request, HttpServletResponse response) {
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
            ServiceFactory factory = ServiceFactoryImpl.getInstance();
            HotelService hotelService = factory.getHotelService();

            List<String> convenience = hotelService.getConvenience();

            request.setAttribute(CONVENIENCE, convenience);
            if (client == null) {
                request.getRequestDispatcher(CONVENIENCE_PAGE).forward(request, response);
            } else {
                request.getRequestDispatcher(CONVENIENCE_CLIENT_PAGE).forward(request, response);
            }
            logger.info("Convenience return");
        } catch (ServletException | IOException e) {
            logger.info("Get convenience failed: ", e);
            throw new CommandException("Get convenience failed", e);
        } catch (ConnectionException e) {
            logger.error("Connect to database failed, error: ", e);
        }
    }
}
