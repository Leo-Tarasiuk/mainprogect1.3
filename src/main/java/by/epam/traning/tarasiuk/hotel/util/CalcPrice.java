package by.epam.traning.tarasiuk.hotel.util;

import by.epam.traning.tarasiuk.hotel.util.exception.CalcPriceException;

import java.sql.Date;

public class CalcPrice {
    /**
     * @param first - first booking dat
     * @param last  - last booking day
     * @param price - price for the one day residence in hotel
     * @return coast for the all time residence
     */
    public double getPrice(Date first, Date last, String price) throws CalcPriceException {
        if (first == null || last == null
                || price == null || price.length() == 0) {
            throw new CalcPriceException("Calculate price exception. Check the input variables");
        }

        long days = (int) ((last.getTime() - first.getTime()) / 8.64e+7);

        double price_res = Double.parseDouble(price);

        return price_res * days;
    }
}
