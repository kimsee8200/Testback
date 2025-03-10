package org.example.plain.domain.payments.interfaces;

import org.example.plain.domain.payments.domain.Payments;

import java.net.URISyntaxException;

public interface PaymentsCheckService {
    public void CheckingForPayments(Payments payments) throws URISyntaxException;
}
