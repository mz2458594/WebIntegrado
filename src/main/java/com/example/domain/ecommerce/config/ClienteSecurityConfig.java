package com.example.domain.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.domain.ecommerce.security.ClienteSuccessHandler;
import com.example.domain.ecommerce.security.CustomAuthenticationFailureHandler;

@Configuration
@Order(1)
public class ClienteSecurityConfig {

    private final ClienteSuccessHandler clienteSuccessHandler;

    public ClienteSecurityConfig(ClienteSuccessHandler clienteSuccessHandler) {
        this.clienteSuccessHandler = clienteSuccessHandler;
    }

    @Bean
    public SecurityFilterChain clienteFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
        .securityMatcher("/targus/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/targus/usuario/form_crear").permitAll()
                        .requestMatchers("/targus/principal/**").permitAll()
                        .requestMatchers("/targus/usuario/**").hasRole("Cliente")
                        .requestMatchers("/targus/venta/**").permitAll()
                        .requestMatchers("/targus/producto/**").permitAll()
                        .anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/targus/principal/iniciar_crear")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(clienteSuccessHandler)
                        .failureHandler(new CustomAuthenticationFailureHandler()))
                .logout(logout -> logout
                        .logoutUrl("/targus/principal/cerrar")
                        .logoutSuccessUrl("/targus/principal/iniciar_crear")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .permitAll());
        return http.build();
    }
}
