package com.airtel.cs.commonutils.applicationutils.constants;


import com.airtel.cs.driver.Driver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class CommonConstantsUtils extends Driver implements Cloneable {
    private static CommonConstantsUtils commonConstants;
    private Properties prop;
    private static final String USER_DIR = "user.dir";
    private static final String FILE_NAME = "/resources/properties/commonconfig.properties";

    private static final String FILE_PATH = System.getProperty(USER_DIR) + FILE_NAME;

    private CommonConstantsUtils() {
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

    public static CommonConstantsUtils getInstance() {
        if (commonConstants == null) {
            commonConstants = new CommonConstantsUtils();
        }
        return commonConstants;
    }

    public String getValue(String key) {
        return prop.getProperty(key);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}