package org.example.plain.domain.user.controller;

import org.example.plain.common.ResponseBody;
import org.example.plain.common.ResponseMaker;
import org.example.plain.domain.user.dto.CustomUserDetails;
import org.example.plain.domain.user.dto.UserRequestResponse;
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
    public ResponseEntity createAccount(@RequestBody UserRequestResponse userRequestResponse) throws Exception {
        userService.createUser(userRequestResponse);
        return ResponseMaker.noContent();
    }

    @PatchMapping("/update")
    public ResponseEntity updateAccount(@RequestBody UserRequestResponse userRequestResponse, Authentication authentication) throws Exception {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        if (!userRequestResponse.getId().equals(customUserDetails.getUser().getId())) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        }
        userService.updateUser(userRequestResponse);
        return ResponseMaker.noContent();
    }


    @DeleteMapping
    public ResponseEntity deleteAccount(@RequestBody String uId, Model model) throws Exception {
        userService.deleteUser(uId);
        return ResponseMaker.noContent();
    }

    @GetMapping("/info")
    public ResponseEntity<ResponseBody<UserRequestResponse>> getUserInfo(String userId){
        UserRequestResponse userRequestResponse = userService.getUser(userId);
        return new ResponseMaker<UserRequestResponse>().ok(userRequestResponse);
    }

}
