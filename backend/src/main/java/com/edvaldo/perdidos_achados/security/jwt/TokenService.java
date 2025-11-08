package com.edvaldo.perdidos_achados.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import com.edvaldo.perdidos_achados.entity.Usuario;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;
  

    private Key getSigningKey() {
         byte[] keyBytes = this.secret.getBytes(StandardCharsets.UTF_8);
            return Keys.hmacShaKeyFor(keyBytes);
    }

    public String gerarToken(Usuario usuario){
        return Jwts.builder()
            .setIssuer("perdidos-achados-api")
            .setSubject(usuario.getEmail())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 3600000L))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    // Método para VALIDAR o token e extrair o Subject (username)
    public String extrairNomeUsuario(String tokenJWT) {
    try {
       
        // Se a validação falhar (expirado, assinatura incorreta), o JJWT lança uma exceção.
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(tokenJWT)
            .getBody()
            .getSubject();
            
    } catch (Exception e) {
        // SE HOUVER EXCEÇÃO (Token Inválido/Expirado), retorne null para que o filtro continue.
        System.out.println("Erro ao validar token JWT: " + e.getMessage());
        return null; 
    }
}


 
}
