package org.example.plain.domain.user.controller;

import jakarta.validation.Valid;
import org.example.plain.common.ResponseBody;
import org.example.plain.common.ResponseMaker;
import org.example.plain.domain.member.dto.Member;
import org.example.plain.domain.user.dto.CustomUserDetails;
import org.example.plain.domain.user.dto.User;
import org.example.plain.domain.user.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity createAccount(@RequestBody User user) throws Exception {
        userService.createUser(user);
        return ResponseMaker.noContent();
    }

    @PatchMapping("/update")
    public ResponseEntity updateAccount(@RequestBody User user, Authentication authentication) throws Exception {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        if (!user.getId().equals(customUserDetails.getUser().getId())) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        }
        userService.updateUser(user);
        return ResponseMaker.noContent();
    }


    @DeleteMapping
    public ResponseEntity deleteAccount(@RequestBody String uId, Model model) throws Exception {
        userService.deleteUser(uId);
        return ResponseMaker.noContent();
    }

    @GetMapping("/info")
    public ResponseEntity<ResponseBody<User>> getUserInfo(String userId){
        User user = userService.getUser(userId);
        return new ResponseMaker<User>().ok(user);
    }

}
