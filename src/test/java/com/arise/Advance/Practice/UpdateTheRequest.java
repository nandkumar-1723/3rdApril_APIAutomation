package com.arise.Advance.Practice;


import org.json.*;
import org.json.simple.parser.*;

import java.io.*;

/**
 * @author Nandkumar Babar
 */
public class UpdateTheRequest {
    public static void main(String[] args) throws IOException, ParseException {

        FileReader fr = new FileReader("src/main/java/com/arise/InputJson/practice.json");
        JSONParser jp = new JSONParser();
        String requestBody = jp.parse(fr).toString();

        JSONObject js = new JSONObject(requestBody);

        //Update the 1st student name
        js.getJSONArray("students").getJSONObject(0).put("name","Virat").toString();

        //Update the 1st student third mock marks
        js.getJSONArray("students").getJSONObject(0).getJSONArray("marks").getJSONObject(2).put("thirdMock","10").toString();

        //Printing the updated result
        System.out.println(js);

    }
}
