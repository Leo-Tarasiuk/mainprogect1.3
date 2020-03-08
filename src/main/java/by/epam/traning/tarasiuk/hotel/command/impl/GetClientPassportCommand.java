package by.epam.traning.tarasiuk.hotel.command.impl;

import by.epam.traning.tarasiuk.hotel.command.Command;
import by.epam.traning.tarasiuk.hotel.command.exception.CommandException;
import by.epam.traning.tarasiuk.hotel.dao.exception.ConnectionException;
import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.service.AdminService;
import by.epam.traning.tarasiuk.hotel.service.factory.ServiceFactory;
import by.epam.traning.tarasiuk.hotel.service.factory.impl.ServiceFactoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetClientPassportCommand implements Command {
    private static final String PASSPORT = "passports";
    private static final String ADMIN_PASSPORT = "WEB-INF/admin/adminPassport.jsp";
    private final Logger logger = LogManager.getLogger("Command");
    private HttpServletRequest request;
    private HttpServletResponse response;

    public GetClientPassportCommand(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * @throws CommandException final wrapper for throws exception
     */
    @Override
    public void execute() throws CommandException {
        try {
            ServiceFactory factory = ServiceFactoryImpl.getInstance();
            AdminService adminService = factory.getAdminService();

            List<Client.Passport> passports = adminService.getAllPassports();

            request.setAttribute(PASSPORT, passports);
            request.getRequestDispatcher(ADMIN_PASSPORT).forward(request, response);
            logger.info("Get client's passport information success");
        } catch (ConnectionException e) {
            logger.error("Connect to database failed, error: " + e.getMessage());
        } catch (ServletException | IOException e) {
            logger.info("Get client's passport information failed: " + e.getMessage());
            throw new CommandException("Get client's passport information failed: " + e.getMessage());
        }
    }
}