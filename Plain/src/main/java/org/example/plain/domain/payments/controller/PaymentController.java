package org.example.plain.domain.payments.controller;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.payments.interfaces.PaymentsCheckService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentsCheckService paymentsCheckService;

    @ResponseBody
    @RequestMapping
    public String tossPayments(){
        return "payments";
    }
}
