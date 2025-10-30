package com.edvaldo.perdidos_achados.config.jwtUtil;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

@Value("${jwt.secret}")
private String secret;


     // Gera o token com claims personalizados
    public String gerarToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", userDetails.getUsername());
        return criarToken(claims, userDetails.getUsername());
    }

    // Cria o token com dados e assinatura
    private String criarToken(Map<String, Object> claims, String subject) {
      return Jwts.builder()
    .setClaims(claims)
    .setSubject(subject)
    .setIssuedAt(new Date(System.currentTimeMillis()))
    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
    .compact();

    }

     public String extrairUsername(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

   private boolean isTokenExpirado(String token) {
        Date expiration = Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getExpiration();
        return expiration.before(new Date());
    }

  public boolean validarToken(String token, UserDetails userDetails) {
        String username = extrairUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpirado(token);
    }

    private Key getSigningKey() {
    byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
}

 
}
