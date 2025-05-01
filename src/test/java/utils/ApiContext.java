package utils;

import io.restassured.response.Response;

public class ApiContext {
    // ThreadLocal to ensure each thread (test scenario) has its own instance
    private static final ThreadLocal<Response> responseThreadLocal = new ThreadLocal<>();

    // Method to set the API response (call this after making an API request)
    public static void setResponse(Response apiResponse) {
        responseThreadLocal.set(apiResponse);
    }

    // Method to get the current API response for the scenario
    public static Response getResponse() {
        return responseThreadLocal.get();
    }

    // Clear the response after the scenario has been completed
    public static void clear() {
        responseThreadLocal.remove();
    }
}
