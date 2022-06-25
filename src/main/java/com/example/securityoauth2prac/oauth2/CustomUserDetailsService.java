package com.example.securityoauth2prac.oauth2;

import com.example.securityoauth2prac.domain.UserReader;
import com.example.securityoauth2prac.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserReader userReader;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userReader.findByProviderId(username).map(user -> createUser(username, user))
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String username,
                                                                          User user) {

        List<GrantedAuthority> grantedAuthority = new ArrayList<>(List.of(
                new SimpleGrantedAuthority(user.getRole().toString())));

        return new org.springframework.security.core.userdetails.User(user.getProviderId(),
                user.getPassword(),
                grantedAuthority);
    }
}
