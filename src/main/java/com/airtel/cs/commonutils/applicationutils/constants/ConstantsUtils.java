package com.airtel.cs.commonutils.applicationutils.constants;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class ConstantsUtils implements Cloneable {
    private static ConstantsUtils constants;
    private final Properties prop;
    private static final String USER_DIR = "user.dir";
    private static final String COMMON_CONFIG_FILE_NAME = "commonconfig.properties";
    private static final String PERMISSION_CONFIG_FILE_NAME = "permission.properties";
    private static final String MESSAGE_CONFIG_FILE_NAME = "messages.properties";
    private static final String ASSERT_MESSAGE_CONFIG_FILE_NAME = "assertion_message.properties";
    private static final String RESOURCES_PROPERTIES = "/resources/properties/";
    private static final String OPCO_CONFIG_FILE_NAME = System.getProperty("Opco").toLowerCase() + "-" + System.getProperty("Env") + ".properties";
    private static final String OPCO_FILE_PATH = System.getProperty(USER_DIR) + RESOURCES_PROPERTIES + OPCO_CONFIG_FILE_NAME;
    private static final String COMMON_FILE_PATH = System.getProperty(USER_DIR) + RESOURCES_PROPERTIES + COMMON_CONFIG_FILE_NAME;
    private static final String PERMISSION_FILE_PATH = System.getProperty(USER_DIR) + RESOURCES_PROPERTIES + PERMISSION_CONFIG_FILE_NAME;
    private static final String MESSAGE_FILE_PATH = System.getProperty(USER_DIR) + RESOURCES_PROPERTIES + MESSAGE_CONFIG_FILE_NAME;
    private static final String ASSERT_MESSAGE_FILE_PATH = System.getProperty(USER_DIR) + RESOURCES_PROPERTIES + ASSERT_MESSAGE_CONFIG_FILE_NAME;
    private static final List<String> fileList = Arrays
        .asList(COMMON_FILE_PATH, PERMISSION_FILE_PATH, MESSAGE_FILE_PATH, ASSERT_MESSAGE_FILE_PATH, OPCO_FILE_PATH);


    private ConstantsUtils() {
        prop = new Properties();
        fileList.forEach(file -> {
            Scanner inFile;
            try {
                inFile = new Scanner(new FileReader(file));
                inFile.next();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            try (InputStream input = new FileInputStream(file)) {
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

    /**
     * This method is use to set value in properties file based on key
     * @param key The key
     * @param value The value
     */
    public void setValue(String key,String value) {
        prop.setProperty(key,value);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}