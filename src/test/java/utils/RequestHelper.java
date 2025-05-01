package utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

public class RequestHelper {

    public static Response performGet(String baseUri, String basePath) {
        String fullUrl = baseUri + basePath;

        RequestSpecification request = given()
                .baseUri(baseUri)
                .basePath(basePath);

        ApiLogger.logRequest(request, "GET", fullUrl, null);
        Response response = request.get();
        ApiLogger.logResponse(response);

        return response;
    }
}
