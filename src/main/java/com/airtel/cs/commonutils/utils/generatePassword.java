package com.airtel.cs.commonutils.utils;

import java.util.Base64;

public class generatePassword {
    public static void main(String[] args) {
        String encodePass = encodePassword("Rao@2103");
        //String decodePassword = decodePassword("UmFvQDIxMDM");
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

