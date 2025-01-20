package domain.member.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class Member {
    private String id;
    private String role;
    private String name;
    private String password;
    private String email;

    public Member(String id, String role, String name, String password, String email) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
