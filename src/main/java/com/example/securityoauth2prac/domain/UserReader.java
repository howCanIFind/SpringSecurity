package com.example.securityoauth2prac.domain;

import com.example.securityoauth2prac.domain.user.User;

import java.util.Optional;

public interface UserReader {

    Optional<User> findByProviderId(String providerId);
}
