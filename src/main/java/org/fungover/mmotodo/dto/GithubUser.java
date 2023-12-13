package org.fungover.mmotodo.dto;

public record GithubUser(String userName,
                         String name,
                         int id,
                         String avatarUrl,
                         String githubProfile,
                         String email, String createdAt, String updatedAt) {

}
