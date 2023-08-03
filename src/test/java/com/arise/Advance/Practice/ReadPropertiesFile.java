package com.arise.Advance.Practice;

import java.io.*;
import java.util.*;

/**
 * @author Nandkumar Babar
 */
public class ReadPropertiesFile {

    public static void main(String[] args) throws IOException {

        FileReader fr =new FileReader("src/main/java/com/arise/Utils/global.properties");

        Properties prop =new Properties();
        prop.load(fr);

      String URL = prop.getProperty("URL");
        System.out.println(URL);

        System.out.println(prop.getProperty("Username"));

    }

}
