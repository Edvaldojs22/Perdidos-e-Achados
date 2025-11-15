package com.edvaldo.perdidos_achados.controller.usuario;

import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.edvaldo.perdidos_achados.entity.Usuario;
import com.edvaldo.perdidos_achados.models.dto.usuario.request.UsuarioEditDTO;
import com.edvaldo.perdidos_achados.service.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController (UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @DeleteMapping("/usuario")
    public ResponseEntity<Void>deletarUsuario(Authentication authentication){
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        usuarioService.deletarPorId(usuarioLogado.getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/usuario")
    public ResponseEntity<UsuarioEditDTO>editar( @RequestBody @Valid UsuarioEditDTO dto, Authentication authenticacao){
        Usuario usuarioLogado = (Usuario) authenticacao.getPrincipal();

        Usuario usuarioEditado = usuarioService.editarUsuario(usuarioLogado.getId(),dto);

        UsuarioEditDTO responseDTO = new UsuarioEditDTO();
        responseDTO.setNome(usuarioEditado.getNome());
        responseDTO.setContato(usuarioEditado.getContato());
        responseDTO.setEmail(usuarioEditado.getEmail());
    
        return ResponseEntity.ok(responseDTO);

    }

}
