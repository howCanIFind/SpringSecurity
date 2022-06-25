package com.example.securityoauth2prac.domain;

import com.example.securityoauth2prac.domain.user.UserRepository;
import com.example.securityoauth2prac.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader{

    private final UserRepository userRepository;

    @Override
    public Optional<User> findByProviderId(String providerId) {
        return userRepository.findByProviderId(providerId);
    }
}
