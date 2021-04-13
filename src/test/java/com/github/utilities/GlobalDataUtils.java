package com.github.utilities;

import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GlobalDataUtils {

    private static String repoOwner;
    private static String repoName;
    private static Response postRepoResponse;
    private static Response getRepoResponse;
    private static Response deleteRepoResponse;

}
