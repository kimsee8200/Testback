package org.example.plain.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.plain.common.enums.Role;
import org.example.plain.domain.user.dto.User;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    private String id;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "name")
    private String username;
    private String password;
    private String email;

    // 프록시로 null 검증.
    public void setId(String id) {
        if (id != null) {
            this.id = id;
        }
    }

    public void setRole(Role role) {
        if (role != null) {
            this.role = role;
        }
    }

    public void setUsername(String username) {
        if (username != null) {
            this.username = username;
        }
    }

    public void setPassword(String password) {
        if (password != null) {
            this.password = password;
        }
    }

    public void setEmail(String email) {
        if (email != null) {
            this.email = email;
        }
    }

    public UserEntity (User user) {
       this.id = user.getId();
       this.email = user.getEmail();
       this.username = user.getUsername();
       this.password = user.getPassword();
    }
}
