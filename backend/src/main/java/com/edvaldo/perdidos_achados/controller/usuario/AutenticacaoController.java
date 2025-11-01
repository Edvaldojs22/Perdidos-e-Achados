package com.edvaldo.perdidos_achados.controller.usuario;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edvaldo.perdidos_achados.entity.Usuario;

import com.edvaldo.perdidos_achados.models.dto.usuario.request.LoginRequestDTO;
import com.edvaldo.perdidos_achados.models.dto.usuario.request.UsuarioCreateDTO;
import com.edvaldo.perdidos_achados.models.dto.usuario.response.LoginResponseDTO;
import com.edvaldo.perdidos_achados.models.dto.usuario.response.UsuarioResponseDTO;
import com.edvaldo.perdidos_achados.security.jwt.TokenService;
import com.edvaldo.perdidos_achados.service.auth.AuthService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
  
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final TokenService tokenService;


    public AutenticacaoController(AuthenticationManager authenticationManager,AuthService authService,TokenService tokenService){
        this.authenticationManager = authenticationManager;
        this.authService = authService;
         this.tokenService = tokenService;
    }

 
    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioCreateDTO dto){
        Usuario salvo = authService.cadastrar(dto);

        String tokenJWT = tokenService.gerarToken(salvo);

        UsuarioResponseDTO resposta = new UsuarioResponseDTO(
            tokenJWT,salvo.getNome(),salvo.getEmail()
            );
       
     
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
        
    }

    @PostMapping("/login")
    public ResponseEntity<?>login(@Valid @RequestBody LoginRequestDTO dto){

        try {
           
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha())
            );

            Usuario usuarioAutenticado = (Usuario) auth.getPrincipal();
            String token = tokenService.gerarToken(usuarioAutenticado);

             return ResponseEntity.ok(new LoginResponseDTO(token,usuarioAutenticado.getId(),usuarioAutenticado.getNome()));
            
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou senha inválidos");
        }
       

    }
}

