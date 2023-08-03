package com.arise.Utils;

import java.io.*;
import java.util.*;

/**
 * @author Nandkumar Babar
 */
public class ReadPropertiesFile {

    public static String readProperties (String property) throws IOException {
        FileReader fr =new FileReader("src/main/java/com/arise/Utils/global.properties");
        Properties prop =new Properties();
        prop.load(fr);
        String value = prop.getProperty(property);
        return value;

    }
}
