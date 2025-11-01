package com.edvaldo.perdidos_achados.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.edvaldo.perdidos_achados.controller.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter){
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // 1. DESABILITAR CSRF: Essencial para APIs REST/JWT
                .csrf(csrf -> csrf.disable()) 
                // 2. Definir como stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                
                // 3. Definir as permissões de acesso
                .authorizeHttpRequests(authorize -> authorize
                    // Permite acesso público (sem autenticação) a estas rotas:
                    .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/cadastrar").permitAll() // <-- ESTA É A CHAVE!
                    
                    // Qualquer outra requisição deve ser autenticada (JWT)
                    .anyRequest().authenticated()
                )
                // === NOVO BLOCO: TRATAMENTO DE EXCEÇÕES ===
                .exceptionHandling(handling -> handling
                    // Configura o manipulador para o erro 401 (Não Autenticado)
                    .authenticationEntryPoint((request, response, authException) -> {
                        
                        // 1. Define o status HTTP 401
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        
                        // 2. Define o tipo de conteúdo como JSON
                        response.setContentType("application/json;charset=UTF-8");
                        
                        // 3. Escreve a mensagem JSON no corpo da resposta
                        String jsonResponse = String.format(
                            "{\"status\": 401, \"erro\": \"%s\", \"mensagem\": \"Esta rota requer um Token JWT válido (Bearer Token).\"}",
                            authException.getMessage() != null ? authException.getMessage() : "Não Autorizado"
                        );
                        
                        response.getWriter().write(jsonResponse);
                    })
                )
                
                // 4. Adicionar seu filtro JWT (se já estiver pronto)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
