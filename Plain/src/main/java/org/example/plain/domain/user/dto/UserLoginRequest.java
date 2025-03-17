package org.example.plain.domain.user.dto;

import lombok.Builder;

@Builder
public record UserLoginRequest(
        String id,
        String password
) {
}
