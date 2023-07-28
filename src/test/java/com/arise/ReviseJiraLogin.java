package com.arise;

import io.restassured.*;
import io.restassured.http.*;
import io.restassured.response.*;
import org.json.*;
import org.testng.annotations.*;

/**
 * @author Nandkumar Babar
 */
public class ReviseJiraLogin {
  public static String cookieValue = "";

    @Test(priority = 1)
    public void loginToJira(){
        //Given (Pre-Req)
        Response response = RestAssured.given().baseUri("http://localhost:9009").body("{\n" +
                        "     \"username\": \"nandkumar\",\n" +
                        "      \"password\": \"nandkumar\" \n" +
                        " }").contentType(ContentType.JSON).when().post("/rest/auth/1/session").then()
                .log().all().extract().response();

        System.out.println(response.asString());
        System.out.println(response.getStatusCode());

        JSONObject js = new JSONObject(response.asString());
         cookieValue = "JSESSIONID="+js.getJSONObject("session").get("value").toString();
        System.out.println(cookieValue);
    }

    @Test(priority = 2)
    public void cretaeUserStory(){
        System.out.println(cookieValue);

        Response response = RestAssured.given().baseUri("http://localhost:9009").body("  {\n" +
                        "   \"fields\": {\n" +
                        "       \"project\": {\n" +
                        "           \"key\": \"AM\"\n" +
                        "       },\n" +
                        "       \"summary\": \"Adding a user story 28th Jul\",\n" +
                        "       \"issuetype\": {\n" +
                        "           \"name\": \"Story\"\n" +
                        "       }\n" +
                        "}\n" +
                        "}").contentType(ContentType.JSON).header("Cookie", cookieValue)
                .when().post("/rest/api/2/issue").then().log().all().extract().response();

        System.out.println(response.getStatusCode());

    }

}
