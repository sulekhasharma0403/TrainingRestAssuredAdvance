package Tests;

import Utils.ExcelUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Map;

public class Activity extends BaseTest {
    @Test(dependsOnMethods = {"Tests.Session.createSession", "Tests.Quotes.unmarkQuoteFavourite"})
    public void getActivityUserToken() {
        RestAssured.baseURI = baseUri;
        String filePath = new File(headersFilePath).getAbsolutePath();
        Map<String, String> headersMap = ExcelUtils.getExcelData(filePath, "headers");

        Response response = RestAssured
                .given()
                .headers(headersMap)
                .header("Authorization", authorization)
                .header("User-Token", userToken)
                .when()
                .get("/activities")
                .then()
                .extract()
                .response();
        System.out.println(response.prettyPrint());
        System.out.println(response.statusCode());
        response.then().statusCode(200);
        activityId = response.path("activities[0].activity_id");
    }

    @Test(dependsOnMethods = {"Tests.Session.createSession", "Tests.Quotes.unmarkQuoteFavourite"})
    public void getActivityQueryParam() {
        RestAssured.baseURI = baseUri;
        String filePath = new File(headersFilePath).getAbsolutePath();
        Map<String, String> headersMap = ExcelUtils.getExcelData(filePath, "headers");

        Response response = RestAssured
                .given()
                .headers(headersMap)
                .header("Authorization", authorization)
                .queryParam("type", "user")// Possible values "user","author" and "tag"
                .queryParam("filter", login) //If type=user , then filter value should be that user's loginId, if type=author, then filter should be that author's name, if type=tag, then filter should be that tag value (g. funny, life, happy etc.)
                .when()
                .get("/activities")
                .then()
                .extract()
                .response();
        System.out.println(response.prettyPrint());
        System.out.println(response.statusCode());
        response.then().statusCode(200);
    }
}
