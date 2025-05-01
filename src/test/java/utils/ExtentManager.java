package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static final ExtentReports extent = new ExtentReports();
    private static ExtentTest featureTest;
    private static final ThreadLocal<ExtentTest> scenarioTest = new ThreadLocal<>();
    private static String currentFeatureName = "";

    static {
        // Setup reporter
        ExtentSparkReporter spark = new ExtentSparkReporter("target/extent-report.html");
        spark.config().setEncoding("UTF-8");
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("API Test Report");
        spark.config().setReportName("Cucumber API Test Execution");

        // NOTE: No setCSS or setChartVisibilityOn in ExtentReports 5+

        extent.attachReporter(spark);
    }

    public static void createFeatureTest(String featureName) {
        if (!featureName.equals(currentFeatureName)) {
            featureTest = extent.createTest("📁 Feature: " + featureName);
            currentFeatureName = featureName;
        }
    }

    public static void createScenarioTest(String scenarioName) {
        ExtentTest scenario = featureTest.createNode("✅ Scenario: " + scenarioName);
        scenarioTest.set(scenario);
    }

    public static ExtentTest getTest() {
        return scenarioTest.get();
    }

    public static void flush() {
        extent.flush();
    }

    public static void remove() {
        scenarioTest.remove();
    }
}
