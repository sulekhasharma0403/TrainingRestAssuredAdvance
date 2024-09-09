package Tests;

import POJO.Login;
import Utils.ExcelUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Collections;
import java.util.Map;

public class Session extends BaseTest{
    @Test(enabled = true)
    public void createSession() {

        RestAssured.baseURI = baseUri;
        String filePath = new File(headersFilePath).getAbsolutePath();
        Map<String, String> headersMap = ExcelUtils.getExcelData(filePath, "headers");

        Login requestBody = new Login(email,password);

        Response response = RestAssured
                .given()
                .headers(headersMap)
                .header("Authorization",authorization)
                .body(Collections.singletonMap("user",requestBody))
                .log().all()
                .when()
                .post("/session")
                .then()
                .extract()
                .response();

        System.out.println(response.prettyPrint());
        System.out.println(response.getStatusCode());
        System.out.println(response.body());
        response.then().statusCode(200);
        userToken = response.path("User-Token");
        login = response.path("login");
    }

    @Test(dependsOnMethods = {"Tests.Session.createSession","Tests.Users.getUser","Tests.Quotes.markQuoteFavourite","Tests.Quotes.unmarkQuoteFavourite","Tests.Quotes.hideQuote","Tests.Quotes.unhideQuote","Tests.Activity.getActivityUserToken","Tests.Activity.getActivityQueryParam"})
    public void deleteSession() {
        RestAssured.baseURI = baseUri;
        String filePath = new File(headersFilePath).getAbsolutePath();
        Map<String, String> headersMap = ExcelUtils.getExcelData(filePath, "headers");

        Response response = RestAssured
                .given()
                .headers(headersMap)
                .header("Authorization", authorization)
                .header("User-Token",userToken)
                .when()
                .delete("/session")
                .then()
                .extract()
                .response();
        System.out.println(response.prettyPrint());
        System.out.println(response.statusCode());
        response.then().statusCode(200);
    }
}
