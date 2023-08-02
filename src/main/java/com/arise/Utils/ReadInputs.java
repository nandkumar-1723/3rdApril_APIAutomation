package com.arise.Utils;

import org.json.simple.parser.*;

import java.io.*;

/**
 * @author Nandkumar Babar
 */
public class ReadInputs {

    public static String readJsonInputs(String filePath) throws IOException, ParseException {
        FileReader fr = new FileReader(filePath);
        JSONParser jp = new JSONParser();
        String requestBody = jp.parse(fr).toString();
        return requestBody;
    }

}