package Tests;

import Utils.ExcelUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Map;

public class GetQuoteOfTheDay extends BaseTest{
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
        System.out.println("QuoteID: "+quoteId);
    }

}
