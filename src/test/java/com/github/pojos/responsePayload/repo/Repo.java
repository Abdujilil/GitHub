package com.github.pojos.responsePayload.repo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Repo {

    private long id;
    private String node_id;
    private String name;
    private String full_name;
    @JsonProperty("private")
    private boolean isPrivate;
    private Owner owner;
    private String html_url;
    private String description;
    private LocalDate created_at;
    private int forks_count;
    private String default_branch;
    private Permissions permissions;


}
