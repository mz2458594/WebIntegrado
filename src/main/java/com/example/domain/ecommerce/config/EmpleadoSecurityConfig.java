package com.example.domain.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import com.example.domain.ecommerce.security.EmpleadoSuccessHandler;

@Configuration
@Order(2)
public class EmpleadoSecurityConfig {

    private final EmpleadoSuccessHandler empleadoSuccessHandler;

    public EmpleadoSecurityConfig(EmpleadoSuccessHandler empleadoSuccessHandler) {
        this.empleadoSuccessHandler = empleadoSuccessHandler;
    }

    @Bean
    public SecurityFilterChain empleadoFilterC(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .securityMatcher("/comprobantes/**", "/login")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(empleadoSuccessHandler)
                        .failureUrl("/login?error=true"))
                .logout(logout -> logout
                        .logoutUrl("/cerrarEmpleado")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .permitAll());
        return http.build();
    }
}
