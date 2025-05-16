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
                .securityMatcher( "/inventario/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        // .requestMatchers("/inventario/principal/**").hasAnyRole("Empleado", "Administrador")
                        // .requestMatchers("/inventario/usuarios/**").hasAnyRole("Administrador")
                        // .requestMatchers("/inventario/categoria/**").hasRole("Administrador")
                        // .requestMatchers("/inventario/proveedores/**").hasRole("Administrador")
                        // .requestMatchers("/inventario/productos/**").hasRole("Administrador")
                        // .requestMatchers("/inventario/ventas/**").hasAnyRole("Empleado", "Administrador")
                        // .requestMatchers("/inventario/comprobante/**").hasAnyRole("Empleado", "Administrador")
                        .anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/inventario/principal/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(empleadoSuccessHandler)
                        .failureUrl("/inventario/principal/login?error=true"))
                .logout(logout -> logout
                        .logoutUrl("/inventario/principal/cerrarEmpleado")
                        .logoutSuccessUrl("/inventario/principal/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .permitAll());
        return http.build();
    }
}
