package org.example.plain.domain.user.dto;

import lombok.*;
import org.example.plain.domain.user.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private String id;
    private String username;
    private String email;
    private String password;

    public UserRequest(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public UserRequest(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
