package com.edvaldo.perdidos_achados.controller;

import com.edvaldo.perdidos_achados.security.jwt.TokenService;
import com.edvaldo.perdidos_achados.service.UserDetailsServiceImpl;

// Nosso Porteiro (Passo 4)
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Necessário para o Spring gerenciar e injetar em SecurityConfig
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserDetailsServiceImpl userDetailsService;

     public JwtAuthenticationFilter(TokenService tokenService, UserDetailsServiceImpl userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Tenta extrair o token do cabeçalho da requisição
        String tokenJWT = extrairToken(request);

        if (tokenJWT != null) {
            // 2. Valida o token e tenta extrair o username (email)
            // Se o token for inválido/expirado, o extrairNomeUsuario retorna null (graças ao try-catch que colocamos lá).
            String username = tokenService.extrairNomeUsuario(tokenJWT);
            
            if (username != null) {
                // 3. Token válido: Carrega o objeto UserDetails completo do banco
                UserDetails user = userDetailsService.loadUserByUsername(username);

                // 4. Cria o objeto de autenticação do Spring Security
                var authentication = new UsernamePasswordAuthenticationToken(
                    user, 
                    null, // A senha (credencial) já foi verificada e não é mais necessária aqui
                    user.getAuthorities() // As Roles/Permissões
                );

                // 5. Instala o usuário no contexto do Spring
                // A partir daqui, o Spring sabe que o usuário está autenticado para esta requisição.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 6. Continua a cadeia de filtros (passa para a próxima etapa, que pode ser o controller ou outro filtro)
        filterChain.doFilter(request, response);
    }

    /**
     * Extrai o token JWT do cabeçalho "Authorization" (formato: Bearer <token>)
     * @param request A requisição HTTP
     * @return O token JWT puro ou null
     */
    private String extrairToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        
        // Verifica se o cabeçalho existe e se começa com "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Retorna a string do token removendo o prefixo "Bearer " (que tem 7 caracteres)
            return authHeader.substring(7); 
        }
        return null;
    }
}