package org.example.plain.domain.user.service;

import org.example.plain.domain.user.dto.*;
import org.example.plain.domain.user.entity.UserEntity;
import org.example.plain.domain.user.interfaces.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    public interface OauthLoadUser{
        OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException;
    }

    @Component
    public class OAuthImpl implements OauthLoadUser{

        @Override
        public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
            return CustomOauth2UserService.super.loadUser(oAuth2UserRequest);
        }
    }

    private OauthLoadUser oauthLoadUser;
    private final UserService userService;


    public CustomOauth2UserService(@Lazy UserService userService, @Lazy OauthLoadUser oauthLoadUser) {
        this.userService = userService;
        this.oauthLoadUser = oauthLoadUser;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = oauthLoadUser.loadUser(userRequest);
        String comeFrom = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = null;

        oAuth2Response = switch (comeFrom) {
            case "google":
                yield new GoogleResponse(oAuth2User.getAttributes());
            case "naver":
                yield new NaverResponse(oAuth2User.getAttributes());
            case "kakao":
                yield new KakaoResponse(oAuth2User.getAttributes());
            default:
                throw new IllegalStateException("Unexpected value: " + comeFrom);
        };

        User user = userService.getUserByEmail(oAuth2Response.getEmail());
        if(user == null){
            user = new User(oAuth2Response.getUsername(),oAuth2Response.getEmail());
            return new CustomOAuth2User(user);
        }else{
            return new CustomOAuth2User(user);
        }
    }
}
