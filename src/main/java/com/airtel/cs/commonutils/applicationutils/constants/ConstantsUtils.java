package com.airtel.cs.commonutils.applicationutils.constants;


import com.airtel.cs.driver.Driver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class ConstantsUtils extends Driver implements Cloneable {
    private static ConstantsUtils constants;
    private final Properties prop;
    private static final String USER_DIR = "user.dir";
    private static final String FILE_NAME = "/resources/properties/commonconfig.properties";

    private static final String OPCO_FILE_PATH = System.getProperty(USER_DIR) + "/resources/properties/" + System.getProperty("Opco") + "-config.properties";
    private static final String PERMISSION_FILE_PATH = System.getProperty(USER_DIR) + FILE_NAME;

    List<String> fileList = Arrays.asList(OPCO_FILE_PATH, PERMISSION_FILE_PATH);


    private ConstantsUtils() {
        prop = new Properties();
        fileList.forEach(file -> {
            Scanner inFile;
            try {
                inFile = new Scanner(new FileReader(OPCO_FILE_PATH));
                inFile.next();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

            try (InputStream input = new FileInputStream(OPCO_FILE_PATH)) {
                /*load a properties file*/
                prop.load(input);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
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