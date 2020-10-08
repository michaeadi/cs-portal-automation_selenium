package Utils;

import Utils.ExcelUtils.writeToExcel;
import tests.BaseTest;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class PassUtils {
    public static String encodePassword(String originalInput) {
        return Base64.getEncoder().withoutPadding().encodeToString(originalInput.getBytes());
    }

    public static String decodePassword(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes);
    }

}
