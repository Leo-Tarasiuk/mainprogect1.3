package by.epam.traning.tarasiuk.hotel.util;

import by.epam.traning.tarasiuk.hotel.util.exception.CalcPriceException;
import org.testng.annotations.Test;

import java.sql.Date;

import static org.testng.Assert.*;

public class CalcPriceTest {

    @Test
    public void testGetPrice() {
        Date first = new Date(2020, 3, 6);
        Date last = new Date(2020, 3, 13);

        String price = "160";

        CalcPrice calcPrice = new CalcPrice();
        double result = 0;

        try {
            result = calcPrice.getPrice(first, last, price);
        } catch (CalcPriceException e) {
            e.printStackTrace();
        }

        double expected = 7 * Double.parseDouble(price);

        assertEquals(result, expected);
    }
}