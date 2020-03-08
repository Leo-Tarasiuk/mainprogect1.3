package by.epam.traning.tarasiuk.hotel.command.factory.impl;

import by.epam.traning.tarasiuk.hotel.command.Command;
import by.epam.traning.tarasiuk.hotel.command.exception.FactoryException;
import by.epam.traning.tarasiuk.hotel.command.factory.CommandFactory;
import by.epam.traning.tarasiuk.hotel.command.impl.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommandFactoryImpl implements CommandFactory {
    private static final String BACK = "back";
    private static final String DATE = "date";
    private static final String REGISTER = "register";
    private static final String SIGN_IN = "sign_in";
    private static final String CHANGE_PASS = "change_pass";
    private static final String CHANGE_MAIL = "change_mail";
    private static final String ROOMS = "rooms";
    private static final String ORDER = "order";
    private static final String MY_PAGE = "my_page";
    private static final String PASSPORT = "pasport";
    private static final String BLACK_LIST = "black_list";
    private static final String LIST_ORDER = "list_order";
    private static final String LIST_CLIENT = "list_client";
    private static final String NEW_BAN = "new_ban";
    private static final String CONVENIENCE = "convenience";
    private static final String CANCEL = "cancel";
    private static final String LOCALE = "locale_form";
    private static final String PHOTO = "photo";
    private static final String ADMIN_PASSPORT = "list_passport";

    private static final CommandFactoryImpl INSTANCE = new CommandFactoryImpl();

    private CommandFactoryImpl() {

    }

    public static CommandFactoryImpl getInstance() {
        return INSTANCE;
    }

    /**
     * @param command  - parameter that determines which class is called
     * @param request  - Object request form user
     * @param response - Object response for user
     * @return the class the user needs
     * @throws FactoryException - class definition exception
     */
    @Override
    public Command getCommand(String command, HttpServletRequest request, HttpServletResponse response) throws FactoryException {
        Command commandfactory;
        switch (command) {
            case PHOTO:
                commandfactory = new PhotoCommand(request, response);
                break;
            case BACK:
                commandfactory = new LogOutCommand(request, response);
                break;
            case DATE:
                commandfactory = new DateChooseCommand(request, response);
                break;
            case REGISTER:
                commandfactory = new RegisterCommand(request, response);
                break;
            case SIGN_IN:
                commandfactory = new LogInCommand(request, response);
                break;
            case CHANGE_PASS:
                commandfactory = new ChangePasswordCommand(request, response);
                break;
            case CHANGE_MAIL:
                commandfactory = new ChangeMailCommand(request, response);
                break;
            case ROOMS:
                commandfactory = new HotelRoomCommand(request, response);
                break;
            case ORDER:
                commandfactory = new CreateOrderCommand(request, response);
                break;
            case LOCALE:
                commandfactory = new LocaleCommand(request, response);
                break;
            case CANCEL:
                commandfactory = new CancelOrderCommand(request, response);
                break;
            case MY_PAGE:
                commandfactory = new GetClientInfCommand(request, response);
                break;
            case PASSPORT:
                commandfactory = new RegisterPassportCommand(request, response);
                break;
            case ADMIN_PASSPORT:
                commandfactory = new GetClientPassportCommand(request, response);
                break;
            case BLACK_LIST:
                commandfactory = new GetBlackListCommand(request, response);
                break;
            case LIST_ORDER:
                commandfactory = new GetAllOrdersCommand(request, response);
                break;
            case LIST_CLIENT:
                commandfactory = new GetAllClientsCommand(request, response);
                break;
            case NEW_BAN:
                commandfactory = new AddNewUserToBlackListCommand(request, response);
                break;
            case CONVENIENCE:
                commandfactory = new GetConvenienceCommand(request, response);
                break;
            default:
                throw new FactoryException("unrecognized request!");
        }

        return commandfactory;
    }
}