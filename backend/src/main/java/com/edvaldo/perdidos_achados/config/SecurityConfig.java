package com.edvaldo.perdidos_achados.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

     @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // desativa CSRF para facilitar testes
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/usuarios/novo").permitAll() // libera rota de cadastro
                .anyRequest().authenticated() // exige autenticação para outras rotas
            );

        return http.build();
    }
}
