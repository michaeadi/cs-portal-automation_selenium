package com.airtel.cs.commonutils.applicationutils.constants;


import tests.frontendagent.BaseTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class ConstantsUtils extends BaseTest implements Cloneable {
    private static ConstantsUtils constants;
    private Properties prop;
    private static final String EVN_NAME = System.getProperty("Env").toUpperCase();

    private static final String FILE_PATH = System.getProperty("user.dir") + "/resources/properties/" + System.getProperty("Opco") + EVN_NAME + "-config.properties";

    private ConstantsUtils() {
        Scanner inFile;
        try {
            inFile = new Scanner(new FileReader(FILE_PATH));
            String name = inFile.next();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        try (InputStream input = new FileInputStream(FILE_PATH)) {
            prop = new Properties();
            /*load a properties file*/
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static ConstantsUtils getInstance() {
        if (constants == null) {
            constants = new ConstantsUtils();
        }
        return constants;
    }

    public String getValue(String key) {
        return prop.getProperty(key);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}