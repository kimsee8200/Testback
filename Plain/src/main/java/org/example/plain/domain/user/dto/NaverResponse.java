package org.example.plain.domain.user.dto;

import java.util.Map;

public class NaverResponse implements OAuth2Response{

    private final Map<String, Object> additionalParameters;

    public NaverResponse(Map<String, Object> additionalParameters) {
        this.additionalParameters = (Map<String, Object>) additionalParameters.get("response");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return additionalParameters.get("id").toString();
    }

    @Override
    public String getUsername() {
        return additionalParameters.get("name").toString();
    }

    @Override
    public String getEmail() {
        return additionalParameters.get("email").toString();
    }

    @Override
    public Map<?, ?> getAttributes() {
        return additionalParameters;
    }
}
