package org.example.plain.domain.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class registerController {

    @GetMapping("/sign_up")
    public String singUp(){
        return "hello";
    }
}
