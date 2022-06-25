package com.example.securityoauth2prac.oauth2;

import com.example.securityoauth2prac.domain.user.User;

public interface UserStore {

    User store(User user);
}
