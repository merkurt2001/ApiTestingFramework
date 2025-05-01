package utils;

import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;

public class ApiLogger {

    public static void logRequest(RequestSpecification requestSpec, String method, String endpoint, String body) {
        String requestDetails = "<b>REQUEST</b><br>" +
                "Endpoint: " + endpoint + "<br>" +
                "Method: " + method + "<br>" +
                "Request Body: " + (body != null ? body : "N/A") + "<br>";
        ExtentManager.getTest().info(requestDetails);
    }

    public static void logResponse(Response response) {
        String responseDetails = "<b>RESPONSE</b><br>" +
                "Status Code: " + response.getStatusCode() + "<br>" +
                "<pre>" + response.getBody().prettyPrint() + "</pre>";
        ExtentManager.getTest().info(responseDetails);
    }

    public static void logFailureResponse(Response response) {
        try {
            String responseBody = response.getBody().prettyPrint();
            ExtentManager.getTest().fail("❗ Failed API Response:<br><pre>" + responseBody + "</pre>");
        } catch (Exception e) {
            ExtentManager.getTest().fail("❗ Failed to capture API response");
        }
    }
}
