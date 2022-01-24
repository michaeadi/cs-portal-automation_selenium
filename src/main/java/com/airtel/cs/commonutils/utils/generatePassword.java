package com.airtel.cs.commonutils.utils;

import java.util.Base64;

public class generatePassword {
    public static void main(String[] args) {
        String encodePass = encodePassword("Abhi.ansh@01");
        //String decodePassword = decodePassword("UkAwQDA4OTQ");
        System.out.println(encodePass);
    }

    public static String encodePassword(String originalInput) {
        return Base64.getEncoder().withoutPadding().encodeToString(originalInput.getBytes());
    }

    public static String decodePassword(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes);
    }
}

