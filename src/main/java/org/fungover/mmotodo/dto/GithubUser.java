package org.fungover.mmotodo.dto;

public record GithubUser(String userName,
                         String name,
                         int id,
                         String role,
                         String avatarUrl,
                         String githubProfile,
                         String email,
                         String createdAt,
                         String updatedAt) {

}
