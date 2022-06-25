package com.example.securityoauth2prac.oauth2;

import com.example.securityoauth2prac.domain.UserReader;
import com.example.securityoauth2prac.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserReader userReader;

    private final PasswordEncoder passwordEncoder;

    private final UserStore userStore;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        System.out.println("CustomOAuth2USerService loadUser 시작");
        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println("인증유저" + oAuth2User);
        System.out.println("***********useRequest********");
        System.out.println(userRequest);
        System.out.println("clientRegistration : " +userRequest.getClientRegistration().getClientName());
        System.out.println("clientRegistration : " +userRequest.getClientRegistration().getClientId());
        System.out.println("accestoken : " +userRequest.getAccessToken().getTokenValue());
        System.out.println("additionaparameter : " +userRequest.getAdditionalParameters());
        System.out.println("***********END********");


        try {
            return process(userRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest oAuth2UserRequest, OAuth2User user) {
        log.info("CustomOAuth2USerService process 시작");
        ProviderType providerType = ProviderType.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = providerType.getOauth2UserInfo(user.getAttributes());

        Optional<User> userOptional = userReader.findByProviderId(providerType + userInfo.getId());
        User savedUser;
        if(userOptional.isPresent()) {
            savedUser = userOptional.get();
            valid(providerType, savedUser);
        } else {
            savedUser = createUser(userInfo, providerType);
        }
        log.info("savedUser {}", savedUser.getId());
        return new OAuth2UserDetailsImpl(savedUser, user.getAttributes());
    }

    private void valid(ProviderType providerType, User savedUser) {
        if (providerType != savedUser.getProviderType()) {
            throw new IllegalStateException("Looks like you're signed up with " + providerType +
                    " account. Please use your " + savedUser.getProviderType() + " account to login.");
        }
    }

    private User createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        log.info("create 시작");
        String password = passwordEncoder.encode("4dayWorks");
        User user = User.OauthSignUp(providerType + userInfo.getId(), userInfo.getEmail(),
                password,
                userInfo.getName(),
                providerType);
        return userStore.store(user);
    }

}
