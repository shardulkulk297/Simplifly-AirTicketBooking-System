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

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf((csrf)->csrf.disable())
                .authorizeHttpRequests(authorize->authorize
                        .requestMatchers("/api/user/signup").permitAll()
                        .requestMatchers("/api/customer/add")
                        .permitAll()
                        .requestMatchers("/api/flightOwner/add").permitAll()
                        .requestMatchers("/api/customer/editProfile").hasAuthority("CUSTOMER")
                        .requestMatchers("/api/customer/deleteAccount").hasAnyAuthority("CUSTOMER", "MANAGER")
                        .requestMatchers("/api/customer/getCustomer").hasAuthority("CUSTOMER")
                        .requestMatchers("/api/flightOwner/editProfile").hasAuthority("FLIGHTOWNER")
                        .requestMatchers("/api/flight/add").hasAuthority("FLIGHTOWNER")
                        .requestMatchers("/api/flight/route/add").hasAuthority("FLIGHTOWNER")
                        .requestMatchers("/api/flightOwner/deleteAccount").hasAnyAuthority("FLIGHTOWNER", "MANAGER")
                        .requestMatchers("/api/flightOwner/getFlightOwner").hasAuthority("FLIGHTOWNER")
                        .anyRequest().authenticated()
                )
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
