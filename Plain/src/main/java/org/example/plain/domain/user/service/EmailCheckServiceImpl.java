package org.example.plain.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.user.interfaces.EmailCheckService;
import org.springframework.mail.javamail.JavaMailSender;

@RequiredArgsConstructor
public class EmailCheckServiceImpl implements EmailCheckService {

    private final JavaMailSender mailSender;

    @Override
    public String makeCode() {
        //Integer randomCode = Math.random();
        return "";
    }

    @Override
    public void sendCodeToEmail(String email) {

    }

    @Override
    public boolean checkEmailCode(String code) {
        return false;
    }
}
