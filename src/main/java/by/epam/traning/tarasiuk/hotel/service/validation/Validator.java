package by.epam.traning.tarasiuk.hotel.service.validation;

import java.sql.Date;

public class Validator {
    private static final String USERNAME_REGEX = "^[A-Za-z]*";
    private static final String PASSWORD_REGEX = "(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])[A-Za-z0-9]{6,}";
    private static final String EMAIL_REGEX = "^.+@.+\\..+$";
    private static final String COUNTRY = "^([A-Za-z]+)(([-]|[ ])*[A-Za-z]+)?";
    private static final String IDENTIFICATION = "^[0-9]{7}[A-Z][0-9]{3}[A-Z]{2}[0-9]";
    private static final String PASSPORT_NO = "^[A-Z]{2}[0-9]{7}";
    private static final int MIN_ID_VALUE = 1;
    private static final String MALE = "male";
    private static final String FEMALE = "female";

    public static boolean isValidUsername(String username) {
        return isNullOrEmpty(username) && username.matches(USERNAME_REGEX);
    }

    public static boolean isValidPassword(String password) {
        return !isNullOrEmpty(password) || !password.matches(PASSWORD_REGEX);
    }

    public static boolean isValidEmail(String email) {
        return !isNullOrEmpty(email) || !email.matches(EMAIL_REGEX);
    }

    private static boolean isNullOrEmpty(String string) {
        return (!(string == null)) && (!string.trim().isEmpty());
    }

    public static boolean isValidPassportInf(String lastName, String name, String patronymic,
                                             String country, Date dateBirth, String sex,
                                             String identificationNo, String passportNo) {

        return isValidUsername(lastName) && isValidUsername(name) && patronymic.matches(USERNAME_REGEX)
                && isValidCountry(country) && isValidSex(sex) && isValidIdentification(identificationNo)
                && isValidPassportNo(passportNo);
    }

    private static boolean isValidCountry(String country) {
        return isNullOrEmpty(country) && country.matches(COUNTRY);
    }

    private static boolean isValidSex(String sex) {
        switch (sex) {
            case MALE:
            case FEMALE:
                return true;
        }
        return false;
    }

    private static boolean isValidIdentification(String identificationNo) {
        return isNullOrEmpty(identificationNo) && identificationNo.matches(IDENTIFICATION);
    }

    private static boolean isValidPassportNo(String passportNo) {
        return isNullOrEmpty(passportNo) && passportNo.matches(PASSPORT_NO);
    }

    public static boolean isValidId(int id) {
        return id >= MIN_ID_VALUE;
    }
}
