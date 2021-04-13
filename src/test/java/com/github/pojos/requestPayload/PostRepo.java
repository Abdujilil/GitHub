package com.github.pojos.requestPayload;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostRepo {

    private String name;
    private String description;
    private String homepage;
    @JsonProperty("private")
    private boolean isPrivate;
    private boolean has_issues;
    private boolean has_projects;
    private boolean has_wiki;
    private String team_id;
    private boolean auto_init;
    private String gitignore_template;
    private String license_template;
    private boolean allow_squash_merge;
    private boolean allow_merge_commit;
    private boolean allow_rebase_merge;
    private boolean delete_branch_on_merge;
    private boolean has_downloads;
    private boolean is_template;

    public PostRepo(String name, String description, boolean isPrivate) {
        this.name = name;
        this.description = description;
        this.isPrivate = isPrivate;
    }

}
