package Utils;

import pages.BasePage;
import tests.BaseTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

public class PassUtils {
    public static String encodePassword(String originalInput) {
        return Base64.getEncoder().withoutPadding().encodeToString(originalInput.getBytes());
    }

    public static String decodePassword(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes);
    }

}
