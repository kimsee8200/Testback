package org.example.plain.domain.user.dto;

import java.util.Map;

public class KakaoResponse implements OAuth2Response{

    private final Map<String, Object> attributes;
    public KakaoResponse(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getUsername() {
        Map<String, Object> account = (Map<String,Object>) attributes.get("kakao_account");
        return account.get("name").toString();
    }

    @Override
    public String getEmail() {
        Map<String, Object> account = (Map<String,Object>) attributes.get("kakao_account");
        return account.get("email").toString();
    }

    @Override
    public Map<?, ?> getAttributes() {
        return attributes;
    }
}
