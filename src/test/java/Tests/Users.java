package Tests;

import POJO.NewUserDetails;
import Utils.ExcelUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Collections;
import java.util.Map;

public class Users extends BaseTest {
    @Test(enabled = true)
    public void createUser() {

        RestAssured.baseURI = baseUri;
        String filePath = new File(headersFilePath).getAbsolutePath();
        Map<String, String> headersMap = ExcelUtils.getExcelData(filePath, "headers");

        NewUserDetails requestBody = new NewUserDetails(newUserLogin, newUserEmail, newUserPassword);

        Response response = RestAssured
                .given()
                .headers(headersMap)
                .header("Authorization", authorization)
                .body(Collections.singletonMap("user", requestBody))
                .log().all()
                .when()
                .post("/users")
                .then()
                .extract()
                .response();

        System.out.println(response.prettyPrint());
        System.out.println(response.getStatusCode());
        System.out.println(response.body());
        response.then().statusCode(200);
    }

    @Test(dependsOnMethods = "Tests.Session.createSession")
    public void getUser() {
        RestAssured.baseURI = baseUri;
        String filePath = new File(headersFilePath).getAbsolutePath();
        Map<String, String> headersMap = ExcelUtils.getExcelData(filePath, "headers");

        Response response = RestAssured
                .given()
                .headers(headersMap)
                .header("Authorization", authorization)
                .header("User-Token",userToken)
                .pathParam("loginId",login)
                .when()
                .get("/users/{loginId}")
                .then()
                .extract()
                .response();
        System.out.println(response.prettyPrint());
        System.out.println(response.statusCode());
        response.then().statusCode(200);
    }
}
