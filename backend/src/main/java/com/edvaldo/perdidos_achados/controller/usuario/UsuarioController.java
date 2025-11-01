package com.edvaldo.perdidos_achados.controller.usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edvaldo.perdidos_achados.service.usuario.UsuarioService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController (UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deletarUsuario(@PathVariable Long id ){
        usuarioService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
