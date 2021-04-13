package com.github.utilities;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class APIUtils {

    private static RequestSpecification requestSpec() {
        return given().log().all()
                      .accept("application/vnd.github.v3+json")
                      .contentType(ContentType.JSON)
                      .header("Authorization",ConfigurationReader.get("access_token"));
    }

    public static Response postRequest(Object body, String endPoint) {
        return given()
                     .spec(requestSpec())
                     .body(body)
               .when()
                      .post(endPoint);
    }

    public static Response getRequest(Map<String, Object> pathParams, String endPoint) {
        return given()
                      .spec(requestSpec())
                      .pathParams(pathParams)
               .when()
                      .get(endPoint);
    }

    public static Response deleteRequest(Map<String, Object> pathParams, String endPoint) {
        return given()
                      .spec(requestSpec())
                      .pathParams(pathParams)
               .when()
                      .delete(endPoint);
    }

    public static Response createRepoPartialOptions() {
        ObjectNode node = JsonUtils.createNode();
        String repoName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-M-d-hh-mm-s-SSS"));
        node.put("name","CreateFromAPI"+repoName);
        node.put("description","This repo is created using RestAssured");
        node.put("public",true);
        return postRequest(node,"user/repos");
    }

    public static void deleteRepo(String owner, String repoName) {
        Map<String, Object> pathParams = new LinkedHashMap<>();
        pathParams.put("owner",owner);
        pathParams.put("repo",repoName);
        given()
                .spec(requestSpec())
                .pathParams(pathParams)
        .when()
                .delete("/repos/{owner}/{repo}");
    }
}
