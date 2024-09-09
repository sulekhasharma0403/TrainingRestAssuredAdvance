package Tests;

import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {
    protected String baseUri;
    protected String authorization;
    protected String headersFilePath;
    protected static String userToken;
    protected static String login;
    protected static int quoteId;
    protected static String activityId;

    @BeforeClass
    public void setup() {
        Properties prop = new Properties();
        Properties filePathProp = new Properties();
        try {
            FileInputStream input = new FileInputStream("src/test/resources/config.properties");
            prop.load(input);
            baseUri = prop.getProperty("baseUri");
            authorization = prop.getProperty("Authorization");
            FileInputStream filePathInput = new FileInputStream("src/test/resources/testData/filePath.properties");
            filePathProp.load(filePathInput);
            headersFilePath = filePathProp.getProperty("headersFilePath");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}