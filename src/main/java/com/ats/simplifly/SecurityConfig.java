package com.ats.simplifly;

import com.ats.simplifly.model.Customer;
import com.ats.simplifly.model.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception{
        http
                .csrf((csrf)->csrf.disable())
                .authorizeHttpRequests(authorize->authorize
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/user/signup").permitAll()
                        .requestMatchers("/api/customer/add")
                        .permitAll()
                        .requestMatchers("/api/flightOwner/add").permitAll()
                        .requestMatchers("/api/manager/add").permitAll()
                        .requestMatchers("/api/user/getToken").authenticated()
                        .requestMatchers("/api/user/getLoggedInUserDetails").authenticated()
                        /*
                        FlightOwner API'S
                         */
                        .requestMatchers("/api/flightOwner/getOwner").hasAuthority("FLIGHTOWNER")
                        .requestMatchers("/api/flightOwner/editProfile").hasAuthority("FLIGHTOWNER")
                        .requestMatchers("/api/flightOwner/upload/logo").hasAuthority("FLIGHTOWNER")
                        .requestMatchers("/api/flightOwner/upload/logo/{id}").permitAll()

                        /*
                       Route API'S
                         */
                        .requestMatchers("/api/flight/route/add").hasAnyAuthority("FLIGHTOWNER", "MANAGER")
                        .requestMatchers("/api/flght/route/update/{routeId}").hasAnyAuthority("FLIGHTOWNER", "MANAGER")
                        .requestMatchers("/api/flight/route/getAll").hasAnyAuthority("FLIGHTOWNER", "MANAGER")
                        .requestMatchers("/api/flight/route/getById/{routeId}").hasAnyAuthority("FLIGHTOWNER", "MANAGER")
                        /*
                        FLIGHT API'S
                         */
                        .requestMatchers("/api/flight/add").hasAnyAuthority("FLIGHTOWNER")
                        .requestMatchers("/api/flight/getAll").hasAnyAuthority("FLIGHTOWNER", "MANAGER")
                        .requestMatchers("/api/flight/getById/{flightId}").hasAnyAuthority("FLIGHTOWNER", "MANAGER")
                        .requestMatchers("/api/flight/update/{flightId}").hasAnyAuthority("FLIGHTOWNER")
                        .requestMatchers("/api/flight/delete/{flightId}").hasAnyAuthority("FLIGHTOWNER")
                        .requestMatchers("/api/flight/getAll").hasAnyAuthority("MANAGER", "CUSTOMER")
                        .requestMatchers("/api/flight/getAllFlights").hasAuthority("FLIGHTOWNER")
                        .requestMatchers("/api/flight/schedule/search").permitAll()
                        .requestMatchers("/api/flight/route/getAll").authenticated()
                        /*
                        FLIGHT SCHEDULING API'S
                         */
                        .requestMatchers("/api/flight/schedule/add").hasAuthority("FLIGHTOWNER")
                        .requestMatchers("/api/flight/schedule/update/{scheduleId}").hasAuthority("FLIGHTOWNER")
                        .requestMatchers("/api/flight/schedule/delete/{scheduleId}").hasAuthority("FLIGHTOWNER")
                        .requestMatchers("/api/flight/schedule/getFlightSchedule").hasAnyAuthority("FLIGHTOWNER", "MANAGER")
                        .requestMatchers("/api/flight/schedule/getAll").hasAuthority("FLIGHTOWNER")
                        .requestMatchers("/api/flight/schedule/getSchedule/{scheduleId}").hasAnyAuthority("CUSTOMER", "FLIGHTOWNER")
                        .requestMatchers("/api/flight/schedule/getSeats/{scheduleId}").hasAnyAuthority("CUSTOMER", "FLIGHTOWNER")
                        /*
                        Booking API'S
                         */
                        .requestMatchers("/api/booking/book").hasAuthority("CUSTOMER")
                        .requestMatchers("/api/passenger/add").hasAuthority("CUSTOMER")
                        .requestMatchers("/api/booking/cancel").hasAuthority("CUSTOMER")
                        .requestMatchers("/api/booking/getBookings").hasAuthority("CUSTOMER")
                         /*
                         DELETE API's
                          */
                        .requestMatchers("/api/flightOwner/deleteAccount").hasAnyAuthority("FLIGHTOWNER", "MANAGER")
                        .requestMatchers("/api/customer/delete/{customerId}").hasAnyAuthority("CUSTOMER", "MANAGER")
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
