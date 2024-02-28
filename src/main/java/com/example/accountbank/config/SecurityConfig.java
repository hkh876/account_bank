package com.example.accountbank.config;

import com.example.accountbank.custom.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

import static com.example.accountbank.constant.AccountBankConstants.MEMBER_LOGIN_URL;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final DataSource dataSource;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, DataSource dataSource) {
        this.customUserDetailsService = customUserDetailsService;
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);

        return jdbcTokenRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF disable
        http.csrf((csrf) -> {
            csrf.disable();
        });

        // Access permission
        http.authorizeHttpRequests((auth) -> {
          auth.requestMatchers("/account_bank/**").hasRole("USER");
          auth.anyRequest().permitAll();
        });

        // Remember me
        http.rememberMe((remember) -> {
            remember.rememberMeParameter("rememberMe");
            remember.tokenValiditySeconds(30 * 24 * 60 * 60);
            remember.userDetailsService(customUserDetailsService);
            remember.tokenRepository(tokenRepository());
        });

        // Custom login
        http.formLogin((login) -> {
            login.loginPage(MEMBER_LOGIN_URL);
            login.defaultSuccessUrl("/", true);
            login.usernameParameter("email");
            login.passwordParameter("password");
        });

        // Custom logout
        http.logout((logout) -> {
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"));
            logout.logoutSuccessUrl(MEMBER_LOGIN_URL);
        });

        return http.build();
    }
}
