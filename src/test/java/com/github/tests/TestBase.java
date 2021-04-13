package com.github.tests;


import com.github.utilities.ConfigurationReader;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.baseURI;

public class TestBase {

    public String token;

    @BeforeClass
    public void beforeClass() {
        baseURI = ConfigurationReader.get("github_api_url");
        token = ConfigurationReader.get("access_token");
    }
}
