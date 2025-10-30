package com.edvaldo.perdidos_achados.controller.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edvaldo.perdidos_achados.entity.Usuario;
import com.edvaldo.perdidos_achados.models.dto.usuario.request.UsuarioCreateDTO;
import com.edvaldo.perdidos_achados.models.dto.usuario.response.UsuarioResponseDTO;

import com.edvaldo.perdidos_achados.service.usuario.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("novo")
    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioCreateDTO dto){
        Usuario salvo = usuarioService.cadastrar(dto);

        UsuarioResponseDTO resposta = new UsuarioResponseDTO();
        resposta.setNome(salvo.getNome());
        resposta.setEmail(salvo.getEmail());
     
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
        
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deletarUsuario(@PathVariable Long id ){
        usuarioService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
