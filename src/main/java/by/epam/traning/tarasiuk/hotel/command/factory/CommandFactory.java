package by.epam.traning.tarasiuk.hotel.command.factory;

import by.epam.traning.tarasiuk.hotel.command.Command;
import by.epam.traning.tarasiuk.hotel.command.exception.FactoryException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandFactory {
    Command getCommand(String command, HttpServletRequest request, HttpServletResponse response) throws FactoryException;
}
