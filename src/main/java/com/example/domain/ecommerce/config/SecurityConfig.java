package com.example.domain.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.domain.ecommerce.security.JwtFilter;
import com.example.domain.ecommerce.services.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    // Define qué rutas son públicas y cuáles requieren autenticación
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas que no requieren autenticación
                .requestMatchers(
                    "/api/auth/**",  // Endpoints de autenticación
                    "/api/users/createUser", 
                    "/swagger-ui/**", // Documentación Swagger
                    "/v3/api-docs/**"
                ).permitAll()
                // Todas las demás rutas requieren autenticación
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            .authenticationProvider(authenticationProvider())
            // Filtro JWT antes del filtro de autenticación estándar
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    
    //Este bean para autenticar usuarios usando CustomUserDetailsService
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // Configuramos el servicio que cargará los usuarios
        authProvider.setUserDetailsService(userDetailsService);
        // Configuramos el codificador de contraseñas
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    //Este bean necesario para el proceso de login
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
