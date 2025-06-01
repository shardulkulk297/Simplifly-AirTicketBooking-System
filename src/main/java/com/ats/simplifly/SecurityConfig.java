package com.ats.simplifly;

import com.ats.simplifly.model.Customer;
import com.ats.simplifly.model.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception{
        http.csrf((csrf)->csrf.disable())
                .authorizeHttpRequests(authorize->authorize
                        .requestMatchers("/api/user/signup").permitAll()
                        .requestMatchers("/api/customer/add")
                        .permitAll()
                        .requestMatchers("/api/flightOwner/add").permitAll()
                        .requestMatchers("/api/customer/**").hasAuthority("CUSTOMER")
                        .requestMatchers("/api/flightOwner/**").hasAuthority("FLIGHTOWNER")
                        .requestMatchers("/api/flight/**").hasAuthority("FLIGHTOWNER")
                        .requestMatchers("/api/flight/route/add").hasAnyAuthority("FLIGHTOWNER", "MANAGER")
                        .requestMatchers("/api/flightOwner/deleteAccount").hasAnyAuthority("FLIGHTOWNER", "MANAGER")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());
        return http.build();

    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager getAuthManager(AuthenticationConfiguration auth) throws Exception{
        return auth.getAuthenticationManager();
    }



}
