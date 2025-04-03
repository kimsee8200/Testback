package org.example.plain.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.user.entity.EmailVerification;
import org.example.plain.domain.user.repository.EmailVerificationRepository;
import org.example.plain.domain.user.interfaces.EmailService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {
    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailService emailService;

    public void sendVerificationCode(String email) {
        String code = generateVerificationCode();
        emailVerificationRepository.deleteById(email);
        emailVerificationRepository.save(new EmailVerification(email, code));
        emailService.sendVerificationEmail(email, code);
    }

    public boolean verifyCode(String email, String code) {
        return emailVerificationRepository.findById(email)
                .map(verification -> {
                    if (verification.getCode().equals(code)) {
                        emailVerificationRepository.deleteById(email);
                        return true;
                    }
                    return false;
                })
                .orElse(false);
    }

    String generateVerificationCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
} 