package by.epam.traning.tarasiuk.hotel.util;

import by.epam.traning.tarasiuk.hotel.entity.Client;
import by.epam.traning.tarasiuk.hotel.entity.UserRole;
import by.epam.traning.tarasiuk.hotel.util.exception.ClientParserException;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ClientParserTest {

    @Test
    public void testParse() {
        String userInf = "3 Leontiy lieo.tarasiuk@mail.ru user";
        ClientParser parser = new ClientParser();

        Client expect = new Client(3, "Leontiy", "lieo.tarasiuk@mail.ru", null, UserRole.USER);

        Client result = null;
        try {
            result = parser.parse(userInf);
        } catch (ClientParserException e) {
            e.printStackTrace();
        }

        assertEquals(result, expect);
    }
}