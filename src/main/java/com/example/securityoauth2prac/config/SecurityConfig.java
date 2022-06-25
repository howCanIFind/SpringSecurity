package com.example.securityoauth2prac.config;


import com.example.securityoauth2prac.oauth2.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(AppProperties.class)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(
                        "/h2-console/**"
                        , "/favicon.ico"
                        , "/error"
                );
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and()
                .httpBasic()
                .disable()
                .csrf()
                .disable()
                .formLogin()
                .disable();

        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity
                .authorizeRequests()
                        .antMatchers("/oauth2/**",
                                "/test",
                                "/oauth/**").permitAll()
                        .anyRequest().authenticated();

        httpSecurity
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .and()
                .redirectionEndpoint()
                .baseUri("/oauth/callback/**")
                .and()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }
}
