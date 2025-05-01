package hooks;

import io.cucumber.java.*;
import utils.ExtentManager;
import utils.ApiLogger;
import utils.ApiContext;
import utils.ScenarioContext;

public class ExtentReportHooks {

    @Before
    public void beforeScenario(Scenario scenario) {
        String uri = scenario.getUri().toString();
        String featureName;

        if (uri.startsWith("classpath:")) {
            uri = uri.replace("classpath:", "");
        }

        int featuresIndex = uri.indexOf("features/");
        if (featuresIndex != -1) {
            featureName = uri.substring(featuresIndex + "features/".length()).replace(".feature", "");
        } else {
            featureName = uri.substring(uri.lastIndexOf('/') + 1).replace(".feature", "");
        }

        ExtentManager.createFeatureTest("&#128193; Feature: " + featureName);
        ExtentManager.createScenarioTest("&#128257; <b style='font-size: 16px;'>"
                + scenario.getName() + "</b>");

        ExtentManager.getTest().info("&#128522; <b>Starting scenario:</b> " + scenario.getName());

        scenario.getSourceTagNames().forEach(tag ->
                ExtentManager.getTest().assignCategory(tag.replace("@", ""))
        );
    }

    @BeforeStep
    public void beforeStep() {
        String stepText = ScenarioContext.getCurrentStepName();
        if (stepText != null) {
            ExtentManager.getTest().info("&#10148; <b>Step started:</b> " + stepText);

            if (ApiContext.getResponse() != null) {
                // Log response only (cannot log request from response)
                String responseBody = ApiContext.getResponse().getBody().asPrettyString();
                ExtentManager.getTest().info("&#128257; <b>Response:</b><pre>" + responseBody + "</pre>");
            }
        }
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        String stepText = ScenarioContext.getCurrentStepName();
        if (stepText != null) {
            if (scenario.isFailed()) {
                ExtentManager.getTest().fail("&#10060; <b>Step failed:</b> " + stepText);
                if (ApiContext.getResponse() != null) {
                    String responseBody = ApiContext.getResponse().getBody().asPrettyString();
                    ExtentManager.getTest().fail("&#128293; <b>Response:</b><pre>" + responseBody + "</pre>");
                }
            } else {
                ExtentManager.getTest().pass("&#9989; <b>Step passed:</b> " + stepText);
            }
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (!scenario.isFailed()) {
            ExtentManager.getTest().pass("&#127881; <b>Scenario Passed:</b> " + scenario.getName());
        } else if (ApiContext.getResponse() != null) {
            ExtentManager.getTest().fail("&#128293; <b>Scenario Failed:</b> " + scenario.getName());
            ApiLogger.logFailureResponse(ApiContext.getResponse());
        }

        // Clear test context for the next scenario
        ScenarioContext.clear();
        ApiContext.clear();
        ExtentManager.remove();
    }

    @AfterAll
    public static void afterAll() {
        ExtentManager.flush();
    }
}
