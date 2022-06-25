package com.example.securityoauth2prac.oauth2;

import com.example.securityoauth2prac.domain.user.UserRepository;
import com.example.securityoauth2prac.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {

    private final UserRepository userRepository;

    public User store(User user) {
        return userRepository.save(user);
    }
}
