package com.example.domain.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.domain.ecommerce.security.ClienteSuccessHandler;

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
        .securityMatcher("/iniciar_crear")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/comprobantes/**").hasRole("Empleado")
                        .anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/iniciar_crear")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(clienteSuccessHandler)
                        .failureUrl("/iniciar_crear?error=true"))
                .logout(logout -> logout
                        .logoutUrl("/cerrar")
                        .logoutSuccessUrl("/iniciar_crear")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .permitAll());
        return http.build();
    }
}
