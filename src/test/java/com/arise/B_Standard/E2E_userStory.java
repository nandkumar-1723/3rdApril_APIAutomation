package com.arise.B_Standard;

import io.restassured.*;
import io.restassured.http.*;
import io.restassured.response.*;
import org.json.*;
import org.json.simple.parser.*;
import org.testng.annotations.*;

import java.io.*;

/**
 * @author Nandkumar Babar
 */
public class E2E_userStory {

    public static String cookieValue = "";
    public static String issueId = "";

    @Test(priority = 1)
    public void loginToJira() throws IOException, ParseException {

        FileReader fr = new FileReader("src/main/java/com/arise/InputJson/loginReqBody.json");
        JSONParser jp = new JSONParser();
        String reqBody = jp.parse(fr).toString();

        Response response = RestAssured.given().baseUri("http://localhost:9009").body(reqBody).contentType(ContentType.JSON)
                .when().post("/rest/auth/1/session")
                .then().log().body().extract().response();

        System.out.println(response.asString());
        System.out.println(response.getStatusCode());
        JSONObject js = new JSONObject(response.asString());
        cookieValue = "JSESSIONID=" + js.getJSONObject("session").get("value").toString();
        System.out.println(cookieValue);


    }
    @Test(priority = 2)
    public void cretaeUserStory() throws IOException, ParseException {
        FileReader fr = new FileReader("src/main/java/com/arise/InputJson/CreateUserStoryBody.json");
        JSONParser jp = new JSONParser();
        String requestBody = jp.parse(fr).toString();


        Response response = RestAssured.given().baseUri("http://localhost:9009").body(requestBody).contentType(ContentType.JSON).header("Cookie", cookieValue)
                .when().post("/rest/api/2/issue")
                .then().log().body().extract().response();

        JSONObject js = new JSONObject(response.asString());
        issueId = js.get("key").toString();
        System.out.println(response.getStatusCode());
        System.out.println(response.asString());

    }
    @Test(priority = 3)
    public void getCreatedUserStory(){
        Response response = RestAssured.given().baseUri("http://localhost:9009").contentType(ContentType.JSON).header("Cookie", cookieValue)
                .when().get("/rest/api/2/issue/"+issueId+"")
                .then().log().body().extract().response();
        System.out.println(response.getStatusCode());
        System.out.println(response.asString());


    }

    @Test(priority = 4)
    public void updateUserStory() throws IOException, ParseException {
        FileReader fr =new FileReader("src/main/java/com/arise/InputJson/UpdateUserStory.json");
        JSONParser jp =new JSONParser();
        String requestBody = jp.parse(fr).toString();

        Response response = RestAssured.given().baseUri("http://localhost:9009").body(requestBody).contentType(ContentType.JSON).header("Cookie", cookieValue)
                .when().put("/rest/api/2/issue/" + issueId + "")
                .then().log().body().extract().response();

        System.out.println(response.getStatusCode());
        System.out.println(response.asString());

    }
    @Test(priority = 5)
    public void getUpdatedUserStory(){
        Response response = RestAssured.given().baseUri("http://localhost:9009").contentType(ContentType.JSON).header("Cookie", cookieValue)
                .when().get("/rest/api/2/issue/"+issueId+"")
                .then().log().body().extract().response();
        System.out.println(response.getStatusCode());

    }
    @Test(priority = 6)
    public void deleteUserStory(){
        Response response = RestAssured.given().baseUri("http://localhost:9009").contentType(ContentType.JSON).header("Cookie", cookieValue)
                .when().delete("/rest/api/2/issue/" + issueId + "")
                .then().log().all().extract().response();
        System.out.println(response.getStatusCode());
        System.out.println(response.asString());
    }
}
