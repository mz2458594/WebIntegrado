package com.example.domain.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            .anyRequest().permitAll()
            // .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
            // .requestMatchers("/","/iniciar_crear", "/infoEmp", "/form_crear", "/registrar/{id}", "/buscar" , "/añadir" , "/recuperar", "/carro" , "/ofertas", "/cerrar").permitAll() // Página principal permitida para todos
            //     .requestMatchers("/adminProducto/**", "/adminUsuarios/**").hasRole("Administrador") // Solo ADMIN
            //     .anyRequest().authenticated()
            )
            .formLogin(form -> form.disable()
                // .loginPage("/iniciar_crear")
                // .usernameParameter("correo")
                // .passwordParameter("password")
                // .defaultSuccessUrl("/acceder", true)
                // .failureUrl("/iniciar_crear?error=true")
            )
            .logout(logout -> logout.disable()
                // .logoutUrl("/cerrar")
                // .logoutSuccessUrl("/iniciar_crear")
                // .invalidateHttpSession(true)
                // .deleteCookies("JSESSIONID")
                // .clearAuthentication(true)
                // .permitAll()
            );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

