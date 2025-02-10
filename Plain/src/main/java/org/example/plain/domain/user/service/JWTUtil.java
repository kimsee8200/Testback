package org.example.plain.domain.user.service;


import org.springframework.beans.factory.annotation.Value;

public class JWTUtil {
    @Value("${spring.jwt.screat}")
    private String tokenScreat;

    public String makeJwtToken(){
    }

}
