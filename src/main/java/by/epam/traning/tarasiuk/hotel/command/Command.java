package by.epam.traning.tarasiuk.hotel.command;

import by.epam.traning.tarasiuk.hotel.command.exception.CommandException;

public interface Command {
    void execute() throws CommandException;
}
