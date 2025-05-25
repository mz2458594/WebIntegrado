package com.example.domain.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
// import com.example.domain.ecommerce.security.JwtFilter;
import com.example.domain.ecommerce.security.JwtUtil;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     private final JwtUtil jwtUtil;

//     public SecurityConfig(JwtUtil jwtUtil) {
//         this.jwtUtil = jwtUtil;
//     }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http
//     // , AuthenticationProvider authenticationProvider
//     )
//             throws Exception {
//         // http.authenticationProvider(authenticationProvider);
//         http.csrf(csrf -> csrf.disable())
//                 .cors(Customizer.withDefaults())
//                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                 .authorizeHttpRequests(auth -> auth
//                         .requestMatchers(
//                             "/api/**",
//                             "/swagger-ui/**"
//                         ).permitAll()
//                         .anyRequest()
//                         // .authenticated()
//                         );

//         return http.build();
//     }

// }
