package com.github.pojos.responsePayload.repo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class Owner {

    private String login;
    private String html_url;
    private String type;
    private Map<String,Object> additionalProperties = new HashMap<>();

}
