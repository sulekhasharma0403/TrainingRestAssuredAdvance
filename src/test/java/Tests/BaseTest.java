package Tests;

import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {
    protected String baseUri;
    protected String authorization;
    protected String email;
    protected String password;
    protected String headersFilePath;
    protected String newUserLogin;
    protected String newUserEmail;
    protected String newUserPassword;
    protected String tag;
    protected String author;
    protected static String userToken;
    protected static String login;
    protected static int quoteId;
    protected static int activityId;

    @BeforeClass
    public void setup() {
        Properties prop = new Properties();
        Properties filePathProp = new Properties();
        Properties newUserProp = new Properties();
        try {
            FileInputStream input = new FileInputStream("src/test/resources/config.properties");
            prop.load(input);
            baseUri = prop.getProperty("baseUri");
            authorization = prop.getProperty("Authorization");
            email = prop.getProperty("email");
            password = prop.getProperty("password");
            FileInputStream filePathInput = new FileInputStream("src/test/resources/testData/filePath.properties");
            filePathProp.load(filePathInput);
            headersFilePath = filePathProp.getProperty("headersFilePath");
            tag = filePathProp.getProperty("queryParamTypeTag");
            author = filePathProp.getProperty("queryParamTypeAuthor");
            FileInputStream newUserDetails = new FileInputStream("src/test/resources/testData/newUserDetails.properties");
            newUserProp.load(newUserDetails);
            newUserLogin = newUserProp.getProperty("newUserLogin");
            newUserEmail = newUserProp.getProperty("newUserEmail");
            newUserPassword = newUserProp.getProperty("newUserPassword");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}