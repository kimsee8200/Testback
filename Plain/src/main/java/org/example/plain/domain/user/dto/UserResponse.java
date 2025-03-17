package org.example.plain.domain.user.dto;

import org.example.plain.domain.user.entity.User;

public record UserResponse (
         String id,
         String username,
         String email
) {


    public static UserResponse chaingeUsertoUserResponse (User user){
        return new UserResponse(user.getId(),user.getUsername(),user.getEmail());
    }
}
