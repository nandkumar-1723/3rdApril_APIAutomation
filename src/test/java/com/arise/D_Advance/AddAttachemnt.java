package com.arise.D_Advance;

import com.arise.Utils.*;
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
public class AddAttachemnt {

    public static String cookieValue = "";
    public static String issueId = "";

    @Test(priority = 1)
    public void loginToJira() throws IOException, ParseException {
        String reqBody = ReadInputs.readJsonInputs("src/main/java/com/arise/InputJson/loginReqBody.json");
        Response response = RestAssured.given().baseUri(ReadPropertiesFile.readProperties("URL")).body(reqBody).contentType(ContentType.JSON)
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

        String requestBody = ReadInputs.readJsonInputs("src/main/java/com/arise/InputJson/CreateUserStoryBody.json");
        JSONObject updatedRB = new JSONObject(requestBody);
        updatedRB.getJSONObject("fields").getJSONObject("issuetype").put("name","Story");

        Response response = RestAssured.given().baseUri(ReadPropertiesFile.readProperties("URL")).body(updatedRB.toString()).contentType(ContentType.JSON).header("Cookie", cookieValue)
                .when().post("/rest/api/2/issue")
                .then().log().body().extract().response();

        JSONObject js = new JSONObject(response.asString());
        issueId = js.get("key").toString();
        System.out.println(response.getStatusCode());
        System.out.println(response.asString());

    }

    @Test(priority = 3)
    public void addAttachemnt() throws IOException {
        File f = new File("src/main/java/com/arise/Document/ariseLOGO.png");
        Response response = RestAssured.given().baseUri(ReadPropertiesFile.readProperties("URL")).contentType(ContentType.MULTIPART)
                .multiPart("file",f)
                .header("Cookie", cookieValue).header("X-Atlassian-Token", "no-check")
                .when().post("/rest/api/2/issue/" + issueId + "/attachments").then().log().body().extract().response();

        System.out.println(response.getStatusCode());

    }
}
