package by.epam.traning.tarasiuk.hotel.controller;

import by.epam.traning.tarasiuk.hotel.command.Command;
import by.epam.traning.tarasiuk.hotel.command.exception.CommandException;
import by.epam.traning.tarasiuk.hotel.command.exception.FactoryException;
import by.epam.traning.tarasiuk.hotel.command.factory.CommandFactory;
import by.epam.traning.tarasiuk.hotel.command.factory.impl.CommandFactoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/hotel", name = "controller")
public class Controller extends HttpServlet {
    private static final String FORM = "form";
    private static final String EXCEPTION = "exception";
    private static final String ERROR_PAGE = "error.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String jspCommand = request.getParameter(FORM);
        CommandFactory factory = CommandFactoryImpl.getInstance();
        executeCommand(factory, jspCommand, request, response);
    }

    private void executeCommand(CommandFactory factory, String commandName, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            if (commandName != null) {
                Command command = factory.getCommand(commandName, request, response);
                command.execute();
            }
        } catch (CommandException | FactoryException e) {
            request.setAttribute(EXCEPTION, e.getMessage());
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
