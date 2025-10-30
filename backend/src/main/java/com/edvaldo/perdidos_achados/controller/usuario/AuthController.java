package com.edvaldo.perdidos_achados.controller.usuario;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edvaldo.perdidos_achados.config.jwtUtil.JwtUtil;
import com.edvaldo.perdidos_achados.models.dto.usuario.request.AuthRequest;
import com.edvaldo.perdidos_achados.models.dto.usuario.response.AuthResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request){
        try{
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
            );

            String token = jwtUtil.gerarToken((UserDetails) auth.getPrincipal());
            return ResponseEntity.ok(new AuthResponse(token));
        }catch(AuthenticationException e){
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
    }
}

