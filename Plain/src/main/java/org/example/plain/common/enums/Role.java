package org.example.plain.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum Role {
    NORMAL("ROLE_USER",new SimpleGrantedAuthority("ROLE_USER")),
    TEACHER("ROLE_TEACHER", new SimpleGrantedAuthority("ROLE_TEACHER")),
    LEADER_CLASS("ROLE_LEADER", new SimpleGrantedAuthority("ROLE_LEADER")),;

    private final String role;
    private final SimpleGrantedAuthority authority;
}
