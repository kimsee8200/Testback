package org.example.plain.domain.user.interfaces;

public interface EmailCheckService {
    String makeCode();
    void sendCodeToEmail(String email);
    boolean checkEmailCode(String code);
}
