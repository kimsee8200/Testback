package org.example.plain.domain.payments.domain;

import lombok.Data;

@Data
public abstract class Payments {
    private String payLectureId;
    private String userId;
}
