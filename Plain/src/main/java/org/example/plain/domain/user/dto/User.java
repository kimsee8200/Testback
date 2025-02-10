package org.example.plain.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.example.plain.common.enums.Role;
import org.example.plain.domain.user.entity.UserEntity;

@Data
@AllArgsConstructor
public class User {
    private String id;
    private String username;
    private String email;
    private String password;

    public User(UserEntity user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
