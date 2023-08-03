package com.arise.C_Practice;

import org.json.simple.parser.*;

import java.io.*;

/**
 * @author Nandkumar Babar
 */
public class ReadJsonFile {

    public static void main(String[] args) throws IOException, ParseException {

        FileReader fr = new FileReader("src/main/java/com/arise/InputJson/CreateUserStoryBody.json");
        JSONParser jp = new JSONParser();
        String requestBody = jp.parse(fr).toString();

        System.out.println(requestBody);

//        Hint --> please use JSONObject class

        //Assignment for 3rd april
//           1. Find the summary
//           2. Fine the Name (e.g.Story)
//           3. Fine the key (E.g. AM)

    }
}
