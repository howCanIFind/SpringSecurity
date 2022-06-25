package com.example.securityoauth2prac.domain.user;

import com.example.securityoauth2prac.oauth2.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(
        access = AccessLevel.PROTECTED
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String providerId;
    private String email;
    private String password;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    public User(String providerId, String email, String password, String name, ProviderType providerType) {
            this.providerId = providerId;
            this.email = email;
            this.password = password;
            this.nickname = name;
            this.providerType = providerType;
            this.role = Role.ROLE_USER;
    }

    public static User OauthSignUp(String providerId, String email, String password, String name,
                                   ProviderType providerType) {
        return new User(providerId, email, password, name, providerType);
    }
}
