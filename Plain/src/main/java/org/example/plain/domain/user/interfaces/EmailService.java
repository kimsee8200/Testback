package org.example.plain.domain.user.interfaces;

public interface EmailService {
    void sendVerificationEmail(String to, String code);
} 