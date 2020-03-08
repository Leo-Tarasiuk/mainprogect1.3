package by.epam.traning.tarasiuk.hotel.util;

import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.entity.UserRole;
import by.epam.traning.tarasiuk.hotel.util.exception.ClientParserException;

public class ClientParser {
    /**
     * @param allUserInformation - string with all user information but without password
     * @return Object type client
     */
    Client parse(String allUserInformation) throws ClientParserException {
        if (allUserInformation == null || allUserInformation.length() == 0) {
            throw new ClientParserException("Client parser exception. String is empty or null");
        }

        String[] userInfo = allUserInformation.split(" ");
        Client client = new Client();
        client.setId(Integer.parseInt(userInfo[0]));
        client.setLastName(userInfo[1]);
        client.setEmail(userInfo[2]);
        client.setRole(UserRole.valueOf(userInfo[3].toUpperCase()));

        return client;
    }
}
