package by.epam.traning.tarasiuk.hotel.util;

import org.testng.annotations.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.testng.Assert.*;

public class PasswordEncrypterTest {

    private PasswordEncrypter passwordEncrypter = new PasswordEncrypter();
    private static final String KEY = "hotel";

    @Test
    public void testEncrypt() {
        String password = "Alex2000";
        String expected = "AI4HfGwSTXTFz1DjGjf+sw==";

        try {
            String result = passwordEncrypter.encrypt(KEY, password);
            assertEquals(result, expected);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | BadPaddingException | UnsupportedEncodingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDecrypt() {
        String password = "AI4HfGwSTXTFz1DjGjf+sw==";
        String expected = "Alex2000";

        try {
            String result = passwordEncrypter.decrypt(KEY, password);
            assertEquals(result, expected);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | IOException e) {
            e.printStackTrace();
        }
    }
}