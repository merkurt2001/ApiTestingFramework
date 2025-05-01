package stepdefinitions;

import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import utils.ApiContext;
import utils.RequestHelper;
import utils.ScenarioContext;

public class MySteps {

    @Given("I test the API")
    public void i_test_the_api() {
        ScenarioContext.setCurrentStepName("Given I test the API");

        String baseUri = "https://jsonplaceholder.typicode.com";
        String basePath = "/posts/1";

        Response response = RequestHelper.performGet(baseUri, basePath);
        ApiContext.setResponse(response); // ✅ Correct usage
    }
}
