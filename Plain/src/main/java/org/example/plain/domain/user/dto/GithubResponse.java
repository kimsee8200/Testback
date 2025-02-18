package org.example.plain.domain.user.dto;

import java.util.Map;


public class GithubResponse implements OAuth2Response{

    private final Map<String, Object> attributes;

    public GithubResponse(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProvider() {
        return "github";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getUsername() {
        return attributes.get("name").toString();
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }

    @Override
    public Map<?, ?> getAttributes() {
        return attributes;
    }
}
