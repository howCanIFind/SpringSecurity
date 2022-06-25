package com.example.securityoauth2prac.oauth2.controller;

import com.example.securityoauth2prac.oauth2.OAuth2UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OAuth2Controller {

    @GetMapping("/test")
    public void test(@AuthenticationPrincipal OAuth2UserDetailsImpl userDetails) {
        log.info(userDetails.getUsername());
        log.info(userDetails.getAuthorities().toString());
        log.info(userDetails.getPassword());

    }

    @GetMapping("/test2")
    public void test2(Authentication authentication) {
        log.info(authentication.getName());
        log.info(String.valueOf(authentication.isAuthenticated()));
//        log.info(userDetails.getUsername());

    }
}
