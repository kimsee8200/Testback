package org.example.plain.domain.user.dto;

import java.util.Map;

public interface OAuth2Response {
    public String getProvider();
    public String getProviderId();
    public String getUsername();
    public String getEmail();
    public Map<?,?> getAttributes();
}
