package Tests;

import Utils.ExcelUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Map;

public class Quotes extends BaseTest{
    @Test(enabled = true)
    public void getQuoteOfTheDay() {

        RestAssured.baseURI = baseUri;
        String filePath = new File(headersFilePath).getAbsolutePath();
        Map<String, String> headersMap = ExcelUtils.getExcelData(filePath, "headers");

        Response response = RestAssured
                .given()
                .headers(headersMap)
                .log().all()
                .when()
                .get("/qotd")
                .then()
                .extract()
                .response();

        System.out.println(response.prettyPrint());
        System.out.println(response.getStatusCode());
        System.out.println(response.body());
        response.then().statusCode(200);
        quoteId = response.path("quote.id");
    }

    @Test(enabled = true)
    public void listQuotes() {

        RestAssured.baseURI = baseUri;
        String filePath = new File(headersFilePath).getAbsolutePath();
        Map<String, String> headersMap = ExcelUtils.getExcelData(filePath, "headers");

        Response response = RestAssured
                .given()
                .headers(headersMap)
                .header("Authorization",authorization)
                .log().all()
                .when()
                .get("/quotes")
                .then()
                .extract()
                .response();

        System.out.println(response.prettyPrint());
        System.out.println(response.getStatusCode());
        System.out.println(response.body());
        response.then().statusCode(200);
        System.out.println("Total quotes displayed: "+response.path("quotes.size()"));
    }

    @Test(dependsOnMethods = "Tests.Quotes.getQuoteOfTheDay")
    public void getQuote(){
        RestAssured.baseURI = baseUri;
        String filePath = new File(headersFilePath).getAbsolutePath();
        Map<String, String> headersMap = ExcelUtils.getExcelData(filePath, "headers");

        Response response = RestAssured
                .given()
                .headers(headersMap)
                .header("Authorization", authorization)
                .pathParam("quoteId",quoteId)
                .log().all()
                .when()
                .get("/quotes/{quoteId}")
                .then()
                .extract()
                .response();

        System.out.println(response.prettyPrint());
        System.out.println(response.getStatusCode());
        System.out.println(response.body());
        response.then().statusCode(200);
    }

    @Test(dependsOnMethods = "Tests.Session.createSession")
    public void markQuoteFavourite(){
        RestAssured.baseURI = baseUri;
        String filePath = new File(headersFilePath).getAbsolutePath();
        Map<String, String> headersMap = ExcelUtils.getExcelData(filePath, "headers");

        Response response = RestAssured
                .given()
                .headers(headersMap)
                .header("Authorization", authorization)
                .header("User-Token",userToken)
                .pathParam("quoteId",quoteId)
                .log().all()
                .when()
                .body("")
                .put("/quotes/{quoteId}/fav")
                .then()
                .extract()
                .response();

        System.out.println(response.prettyPrint());
        System.out.println(response.getStatusCode());
        System.out.println(response.body());
        response.then().statusCode(200);
    }

    @Test(dependsOnMethods = {"Tests.Session.createSession","Tests.Quotes.markQuoteFavourite"})
    public void unmarkQuoteFavourite(){
        RestAssured.baseURI = baseUri;
        String filePath = new File(headersFilePath).getAbsolutePath();
        Map<String, String> headersMap = ExcelUtils.getExcelData(filePath, "headers");

        Response response = RestAssured
                .given()
                .headers(headersMap)
                .header("Authorization", authorization)
                .header("User-Token",userToken)
                .pathParam("quoteId",quoteId)
                .log().all()
                .when()
                .body("")
                .put("/quotes/{quoteId}/unfav")
                .then()
                .extract()
                .response();

        System.out.println(response.prettyPrint());
        System.out.println(response.getStatusCode());
        System.out.println(response.body());
        response.then().statusCode(200);
    }

    @Test(dependsOnMethods = "Tests.Session.createSession")
    public void hideQuote(){
        RestAssured.baseURI = baseUri;
        String filePath = new File(headersFilePath).getAbsolutePath();
        Map<String, String> headersMap = ExcelUtils.getExcelData(filePath, "headers");

        Response response = RestAssured
                .given()
                .headers(headersMap)
                .header("Authorization", authorization)
                .header("User-Token",userToken)
                .pathParam("quoteId",quoteId)
                .log().all()
                .when()
                .body("")
                .put("/quotes/{quoteId}/hide")
                .then()
                .extract()
                .response();

        System.out.println(response.prettyPrint());
        System.out.println(response.getStatusCode());
        System.out.println(response.body());
        response.then().statusCode(200);
    }

    @Test(dependsOnMethods = {"Tests.Session.createSession","Tests.Quotes.hideQuote"})
    public void unhideQuote(){
        RestAssured.baseURI = baseUri;
        String filePath = new File(headersFilePath).getAbsolutePath();
        Map<String, String> headersMap = ExcelUtils.getExcelData(filePath, "headers");

        Response response = RestAssured
                .given()
                .headers(headersMap)
                .header("Authorization", authorization)
                .header("User-Token",userToken)
                .pathParam("quoteId",quoteId)
                .log().all()
                .when()
                .body("")
                .put("/quotes/{quoteId}/unhide")
                .then()
                .extract()
                .response();

        System.out.println(response.prettyPrint());
        System.out.println(response.getStatusCode());
        System.out.println(response.body());
        response.then().statusCode(200);
    }

}
