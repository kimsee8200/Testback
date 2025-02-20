package org.example.plain.domain.classLecture.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public class CodeGenerator {

    public static String generateCode(){
        try {
            String uuid = UUID.randomUUID().toString();

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(uuid.getBytes(StandardCharsets.UTF_8));

            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash).substring(0, 10);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("알고리즘을 찾을 수 없습니다.", e);
        }
    }
}
