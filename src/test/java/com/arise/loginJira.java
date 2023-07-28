package com.arise;
import io.restassured.*;
import io.restassured.http.*;
import io.restassured.response.*;
import org.json.*;
import org.testng.annotations.*;

/**
 * @author Nandkumar Babar
 */
public class loginJira {
    public static String value = "";  //Declared string globaly.
    public static String issueId ="";

    @Test(priority = 1)
    public void loginJira() throws Exception {
        Response response = RestAssured.given().baseUri("http://localhost:9009").body(" {\n" +
                        "     \"username\": \"nandkumar\",\n" +
                        "      \"password\": \"nandkumar\" \n" +
                        " }").contentType(ContentType.JSON)
                .when().post("/rest/auth/1/session")
                .then().log().body().extract().response(); //Used log method to print the log in console

        System.out.println(response.getStatusCode()); //Print the status code
        JSONObject js = new JSONObject(response.asString()); //Read the json
        value = "JSESSIONID="+js.getJSONObject("session").get("value").toString(); // get the value from Json
        System.out.println("Cookie generated successfully.........................!!");

    }

    @Test(priority = 2)
    public void createUserStory(){
        Response response = RestAssured.given().baseUri("http://localhost:9009").body("{\n" +
                        "   \"fields\": {\n" +
                        "       \"project\": {\n" +
                        "           \"key\": \"AM\"\n" +
                        "       },\n" +
                        "       \"summary\": \"Adding a user story by automation\",\n" +
                        "       \"issuetype\": {\n" +
                        "           \"name\": \"Story\"\n" +
                        "       }\n" +
                        "}\n" +
                        "}").contentType(ContentType.JSON).header("Cookie", value).
                when().post("/rest/api/2/issue").then().log().body().extract().response();

        JSONObject js = new JSONObject(response.asString());
         issueId = js.get("key").toString();
        System.out.println(response.getStatusCode());

    }
    @Test(priority = 3)
    public void getUserStory(){
        Response response = RestAssured.given().baseUri("http://localhost:9009").header("Cookie", value).contentType(ContentType.JSON)
                .when().get("/rest/api/2/issue/"+issueId+"").then().log().all().extract().response();

        System.out.println(response.getStatusCode());

    }
    @Test(priority = 4)
    public void updateUseStory(){
        Response response = RestAssured.given().baseUri("http://localhost:9009").body("   {\n" +
                        "   \"fields\": {\n" +
                        "       \"project\": {\n" +
                        "           \"key\": \"AM\"\n" +
                        "       },\n" +
                        "       \"summary\": \"Adding a user story by automation for PUT call\",\n" +
                        "       \"issuetype\": {\n" +
                        "           \"name\": \"Story\"\n" +
                        "       }\n" +
                        "}\n" +
                        "}     ").header("Cookie", value).contentType(ContentType.JSON)
                .when().put("/rest/api/2/issue/"+issueId+"").then().log().body().extract().response();
        System.out.println(response.getStatusCode());
    }
}
