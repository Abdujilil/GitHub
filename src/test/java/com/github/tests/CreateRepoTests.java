package com.github.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.github.pojos.requestPayload.PostRepo;
import com.github.pojos.responsePayload.repo.Owner;
import com.github.pojos.responsePayload.repo.Permissions;
import com.github.pojos.responsePayload.repo.Repo;
import com.github.utilities.JsonUtils;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.github.utilities.APIUtils.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class CreateRepoTests extends TestBase {

    @Test()
    public void createNewRepoPartialOptions() {
        ObjectNode node = JsonUtils.createNode();
        String repoName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-M-d-hh-mm-s-SSS"));
        node.put("name","CreateFromAPI"+repoName);
        node.put("description","This repo is created using RestAssured");
        node.put("public",true);
        Response response = postRequest(node, "user/repos");

        assertEquals(response.statusCode(),201);
        assertEquals(response.contentType(),"application/json; charset=utf-8");

        deleteRepo(response.path("owner.login"), response.path("name"));
    }

    @Test()
    public void verifyNewRepoPartialOptions() {
        ObjectNode node = JsonUtils.createNode();
        String repoName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-M-d-hh-mm-s-SSS"));
        node.put("name","CreateFromAPI"+repoName);
        node.put("description","This repo is created using RestAssured");
        node.put("public",true);
        Response response = postRequest(node, "user/repos");
        Repo repo = response.body().as(Repo.class);

        assertEquals(repo.getName(),"CreateFromAPI"+repoName);
        assertEquals(repo.getFull_name(),"ownerName/CreateFromAPI"+repoName);
        assertFalse(repo.isPrivate());

        deleteRepo(response.path("owner.login"), response.path("name"));
    }

    @Test()
    public void verifyNewRepoOwnerPartialOptions() {
        ObjectNode node = JsonUtils.createNode();
        String repoName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-M-d-hh-mm-s-SSS"));
        node.put("name","CreateFromAPI"+repoName);
        node.put("description","This repo is created using RestAssured");
        node.put("public",true);
        Response response = postRequest(node, "user/repos");
        JsonNode nodeOwner = JsonUtils.parse(response.body().asString());
        Owner owner = JsonUtils.fromJson(nodeOwner.get("owner"),Owner.class);

        assertEquals(owner.getLogin(),"ownerName");
        assertEquals(owner.getHtml_url(), "https://github.com/ownerName");
        assertEquals(owner.getType(),"User");

        deleteRepo(response.path("owner.login"), response.path("name"));
    }

    @Test()
    public void verifyNewRepoPartialOptions2() {
        String repoName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-M-d-hh-mm-s-SSS"));
        String name = "CreateFromAPI"+repoName;
        String description = "This repo is created using RestAssured";
        PostRepo postRepo = new PostRepo(name,description,true);
        Response response = postRequest(postRepo, "user/repos");
        Repo repo = response.body().as(Repo.class);

        assertEquals(repo.getHtml_url(),"https://github.com/ownerName/CreateFromAPI"+repoName);
        assertEquals(repo.getDescription(),"This repo is created using RestAssured");
        assertEquals(repo.getCreated_at(),LocalDate.now(ZoneId.of("GMT")));

        deleteRepo(response.path("owner.login"), response.path("name"));
    }

    @Test()
    public void verifyNewRepoPartialOptions3() {
        ObjectNode node = JsonUtils.createNode();
        String repoName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-M-d-hh-mm-s-SSS"));
        node.put("name","CreateFromAPI"+repoName);
        node.put("description","This repo is created using RestAssured");
        node.put("public",true);
        Response response = postRequest(node, "user/repos");
        Repo repo = response.body().as(Repo.class);

        assertEquals(repo.getForks_count(),0);
        assertEquals(repo.getDefault_branch(),"main");

        deleteRepo(response.path("owner.login"), response.path("name"));
    }

    @Test()
    public void verifyNewRepoPartialOptions4() {
        String repoName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-M-d-hh-mm-s-SSS"));
        String name = "CreateFromAPI"+repoName;
        String description = "This repo is created using RestAssured";
        PostRepo postRepo = new PostRepo(name,description,true);
        Response response = postRequest(postRepo, "user/repos");
        JsonNode nodePermissions = JsonUtils.parse(response.body().asString());
        Permissions permissions = JsonUtils.fromJson(nodePermissions.get("permissions"),Permissions.class);

        assertTrue(permissions.isAdmin());
        assertTrue(permissions.isPush());
        assertTrue(permissions.isPull());

        deleteRepo(response.path("owner.login"), response.path("name"));
    }

    @Test()
    public void getRepoPartialOptions() {
        Response newPartialRepo = createRepoPartialOptions();
        Map<String, Object> pathParams = new LinkedHashMap<>();
        pathParams.put("owner",newPartialRepo.path("owner.login"));
        pathParams.put("repo",newPartialRepo.path("name"));
        Response response = getRequest(pathParams, "/repos/{owner}/{repo}");

        response.then()
                .assertThat().statusCode(200)
                .and()
                .assertThat().contentType("application/json; charset=utf-8");

        deleteRepo(response.path("owner.login"), response.path("name"));
    }

    @Test()
    public void getRepoPartialOptions2() {
        Response newPartialRepo = createRepoPartialOptions();
        Map<String, Object> pathParams = new LinkedHashMap<>();
        pathParams.put("owner",newPartialRepo.path("owner.login"));
        pathParams.put("repo",newPartialRepo.path("name"));
        Response response = getRequest(pathParams, "/repos/{owner}/{repo}");
        Repo repo = response.body().as(Repo.class);

        assertEquals(repo.getName(),newPartialRepo.path("name"));
        assertEquals(repo.getFull_name(),"ownerName/"+newPartialRepo.path("name"));
        assertFalse(repo.isPrivate());

        deleteRepo(response.path("owner.login"), response.path("name"));
    }

    @Test()
    public void getRepoOwnerPartialOptions() {
        Response newPartialRepo = createRepoPartialOptions();
        Map<String, Object> pathParams = new LinkedHashMap<>();
        pathParams.put("owner",newPartialRepo.path("owner.login"));
        pathParams.put("repo",newPartialRepo.path("name"));
        Response response = getRequest(pathParams, "/repos/{owner}/{repo}");
        JsonNode node = JsonUtils.parse(response.body().asString());
        Owner owner = JsonUtils.fromJson(node.get("owner"),Owner.class);

        assertEquals(owner.getLogin(),"ownerName");
        assertEquals(owner.getHtml_url(),"https://github.com/ownerName");
        assertEquals(owner.getType(),"User");

        deleteRepo(response.path("owner.login"), response.path("name"));
    }

    @Test()
    public void getRepoPartialOptions4() {
        Response newPartialRepo = createRepoPartialOptions();
        Map<String, Object> pathParams = new LinkedHashMap<>();
        pathParams.put("owner",newPartialRepo.path("owner.login"));
        pathParams.put("repo",newPartialRepo.path("name"));
        Response response = getRequest(pathParams, "/repos/{owner}/{repo}");
        Repo repo = response.body().as(Repo.class);

        assertEquals(repo.getDescription(),"This repo is created using RestAssured");
        assertEquals(repo.getCreated_at(),LocalDate.now(ZoneId.of("GMT")));
        assertEquals(repo.getForks_count(),0);

        deleteRepo(response.path("owner.login"), response.path("name"));
    }

    @Test()
    public void getRepoPartialOptions5() {
        Response newPartialRepo = createRepoPartialOptions();
        Map<String, Object> pathParams = new LinkedHashMap<>();
        pathParams.put("owner",newPartialRepo.path("owner.login"));
        pathParams.put("repo",newPartialRepo.path("name"));
        Response response = getRequest(pathParams, "/repos/{owner}/{repo}");
        JsonNode node = JsonUtils.parse(response.body().asString());
        Permissions permissions = JsonUtils.fromJson(node.get("permissions"),Permissions.class);

        assertTrue(permissions.isAdmin());
        assertTrue(permissions.isPull());
        assertTrue(permissions.isPush());

        deleteRepo(response.path("owner.login"), response.path("name"));
    }

    @Test()
    public void deleteRepoPartialOptions() {
        Response newPartialRepo = createRepoPartialOptions();
        Map<String, Object> pathParams = new LinkedHashMap<>();
        pathParams.put("owner",newPartialRepo.path("owner.login"));
        pathParams.put("repo",newPartialRepo.path("name"));
        Response response = deleteRequest(pathParams, "/repos/{owner}/{repo}");

        response
                .then()
                .statusCode(204)
                .and()
                .header("X-Accepted-OAuth-Scopes", is("delete_repo"));
    }

    @Test()
    public void deleteRepoPartialOptionsNegative() {
        Response newPartialRepo = createRepoPartialOptions();
        Map<String, Object> pathParams = new LinkedHashMap<>();
        pathParams.put("owner",newPartialRepo.path("owner.login"));
        pathParams.put("repo",newPartialRepo.path("name"));
        deleteRequest(pathParams, "/repos/{owner}/{repo}");
        Response response = deleteRequest(pathParams, "/repos/{owner}/{repo}");
        response
                .then()
                .statusCode(404)
                .and()
                .body("message",is("Not Found"));
    }

}
