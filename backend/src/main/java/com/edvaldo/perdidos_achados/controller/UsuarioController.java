package com.edvaldo.perdidos_achados.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edvaldo.perdidos_achados.entity.Usuario;
import com.edvaldo.perdidos_achados.models.dto.UsuarioDTO;
import com.edvaldo.perdidos_achados.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("novo")
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioDTO dto){
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());

        Usuario salvo = usuarioService.cadastrar(usuario);

        UsuarioDTO resposta = new UsuarioDTO();
        resposta.setNome(salvo.getNome());
        resposta.setEmail(salvo.getEmail());
        resposta.setSenha(salvo.getSenha());
        
        return ResponseEntity.ok(resposta);
        
    }
    

    
}
