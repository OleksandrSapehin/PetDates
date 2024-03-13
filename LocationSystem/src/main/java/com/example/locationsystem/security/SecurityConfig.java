package com.example.locationsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
       public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http

                .authorizeHttpRequests(auth->auth
                        .requestMatchers(HttpMethod.GET, "/index*", "/static/**", "/*.js", "/*.json", "/*.ico", "/rest")
                        .permitAll()
                        .requestMatchers("/admin")
                        .hasRole("ADMIN")
                        .requestMatchers("/auth/login", "/auth/registration","/error")
                        .permitAll()
                        .anyRequest()
                        .hasAnyRole("USER","ADMIN")

                )

                .formLogin(formLogin -> formLogin
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/mainPage",true)
                        .failureUrl("/auth/login?error")
                )

                .logout(logout-> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login")
                );


        return http.build();
    }
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
