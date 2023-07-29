package com.arise.Basic;

import io.restassured.*;
import io.restassured.http.*;
import io.restassured.response.*;
import org.json.*;
import org.testng.annotations.*;

/**
 * @author Nandkumar Babar
 */
public class E2E_userStory {
  public static String cookieValue = "";
  public static String issueId = "";

    @Test(priority = 1)
    public void loginToJira(){
        //Given (Pre-Req)
        Response response = RestAssured.given().baseUri("http://localhost:9009").body("{\n" +
                        "     \"username\": \"nandkumar\",\n" +
                        "      \"password\": \"nandkumar\" \n" +
                        " }").contentType(ContentType.JSON)
                 .when().post("/rest/auth/1/session")
                 .then().log().body().extract().response();

         System.out.println(response.asString());
         System.out.println(response.getStatusCode());
        JSONObject js = new JSONObject(response.asString());
         cookieValue = "JSESSIONID="+js.getJSONObject("session").get("value").toString();
        System.out.println(cookieValue);
    }

    @Test(priority = 2)
    public void cretaeUserStory(){
        Response response = RestAssured.given().baseUri("http://localhost:9009").body("  {\n" +
                        "   \"fields\": {\n" +
                        "       \"project\": {\n" +
                        "           \"key\": \"AM\"\n" +
                        "       },\n" +
                        "       \"summary\": \"Adding a user for 3rd april students\",\n" +
                        "       \"issuetype\": {\n" +
                        "           \"name\": \"Story\"\n" +
                        "       }\n" +
                        "}\n" +
                        "}").contentType(ContentType.JSON).header("Cookie", cookieValue)
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
    public void updateUserStory(){
        Response response = RestAssured.given().baseUri("http://localhost:9009").body("            {\n" +
                        "   \"fields\": {\n" +
                        "       \"project\": {\n" +
                        "           \"key\": \"AM\"\n" +
                        "       },\n" +
                        "       \"summary\": \"Updating for 3rd april students\",\n" +
                        "       \"issuetype\": {\n" +
                        "           \"name\": \"Story\"\n" +
                        "       }\n" +
                        "}\n" +
                        "}").contentType(ContentType.JSON).header("Cookie", cookieValue)
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
