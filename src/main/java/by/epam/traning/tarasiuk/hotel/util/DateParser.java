package by.epam.traning.tarasiuk.hotel.util;

import by.epam.traning.tarasiuk.hotel.util.exception.DateParserException;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateParser {
    /**
     * @param date - date in string which need to pars
     * @return Object type date
     * @throws ParseException - exception with parsing
     */
    public Date parse(String date) throws ParseException, DateParserException {
        if (date == null || date.length() == 0) {
            throw new DateParserException("Date parser exception. String with date is empty or null");
        }
        java.util.Date newdate = new SimpleDateFormat("yyyy-MM-dd").parse(date);

        return new Date(newdate.getTime());
    }
}
