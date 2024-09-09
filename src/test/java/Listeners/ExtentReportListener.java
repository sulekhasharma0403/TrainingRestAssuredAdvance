package Listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;
import org.apache.log4j.Logger;

import java.util.List;

public class ExtentReportListener implements IReporter {

    private static final Logger logger = Logger.getLogger(ExtentReportListener.class);
    private ExtentReports extent;

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        // Initialize Extent Reports
        String reportPath = outputDirectory + "\\extentReport.html";
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportPath);
        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config().setReportName("Test Report");
        htmlReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Host Name", "IN-D2GZ9K3");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User Name", "Sulekha Sharma");

        // Loop through suites and collect test results
        for (ISuite suite : suites) {
            // Create a single ExtentTest for the suite
            ExtentTest suiteTest = extent.createTest(suite.getName());

            boolean suitePassed = true; // Track suite status

            // Iterate through each suite result
            for (ISuiteResult suiteResult : suite.getResults().values()) {
                // Add test results under the suite node
                for (ITestResult result : suiteResult.getTestContext().getPassedTests().getAllResults()) {
                    ExtentTest test = suiteTest.createNode(result.getMethod().getMethodName());
                    test.pass("Test Passed");
                }

                for (ITestResult result : suiteResult.getTestContext().getFailedTests().getAllResults()) {
                    ExtentTest test = suiteTest.createNode(result.getMethod().getMethodName());
                    test.fail(result.getThrowable());
                    suitePassed = false; // Mark suite as failed if any test fails
                }

                for (ITestResult result : suiteResult.getTestContext().getSkippedTests().getAllResults()) {
                    ExtentTest test = suiteTest.createNode(result.getMethod().getMethodName());
                    test.skip("Test Skipped");
                    suitePassed = false; // Mark suite as failed if any test is skipped
                }
            }

            //Log the suite status
            if (suitePassed) {
                suiteTest.pass("Test Suite: "+suite.getName()+" - Passed");
            } else {
                suiteTest.fail("Test Suite: "+suite.getName()+" - Failed");
            }
        }

        // Generate the report
        extent.flush();

        // Log the location of the generated report
        logger.info("Report generated at: " + reportPath);
    }
}