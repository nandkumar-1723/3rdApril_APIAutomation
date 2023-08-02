package com.arise.Advance.Practice;

import org.json.*;
import org.json.simple.parser.*;

import java.io.*;

/**
 * @author Nandkumar Babar
 */
public class practice {
    public static void main(String[] args) throws IOException, ParseException {

        FileReader fr = new FileReader("src/main/java/com/arise/InputJson/practice.json");
        JSONParser jp =new JSONParser();
        String requestBody = jp.parse(fr).toString();

        System.out.println(requestBody);

        JSONObject js = new JSONObject(requestBody);
        // print the 1st student id.
        String id = js.getJSONArray("students").getJSONObject(0).get("id").toString();
        System.out.println(id);

        // print the 1st student previous city
        String previousCity = js.getJSONArray("students").getJSONObject(0).getJSONObject("city").get("previous").toString();
        System.out.println(previousCity);

        //print the 3rd students second mock marks
        String marks = js.getJSONArray("students").getJSONObject(2).getJSONArray("marks").getJSONObject(1).get("secondMock").toString();
        System.out.println(marks);

        //print no of jsonObject in jsonArray
        int lenthOfObject = js.getJSONArray("students").length();
        System.out.println(lenthOfObject);


        //Assignment
//        1. Print second student 3rd mock marks
//        2. Print no of jsonObject in second student mock
//        3. print third student current city
//        4. Print first student 3rd mock status


    }
}
