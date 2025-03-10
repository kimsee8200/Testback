package org.example.plain.domain.payments.domain;

import lombok.Data;

@Data
public class PaymentsToss extends Payments {
    private String paymentsKey;
    private String orderId;
    private Integer amount;
}
