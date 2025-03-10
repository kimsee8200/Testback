package org.example.plain.domain.payments.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PaymentController {

    @RequestMapping
    public String tossPayments(){
        return "payments";
    }
}
