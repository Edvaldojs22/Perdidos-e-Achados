package com.edvaldo.perdidos_achados.controller.usuario;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.edvaldo.perdidos_achados.entity.Usuario;
import com.edvaldo.perdidos_achados.models.dto.usuario.request.UsuarioEditDTO;
import com.edvaldo.perdidos_achados.service.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



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

    @PutMapping("/editar/{id}")
    public ResponseEntity<UsuarioEditDTO>editar(@PathVariable Long id, @RequestBody @Valid UsuarioEditDTO dto, Authentication authenticacao){
        Usuario usuarioEditado = usuarioService.editarUsuario(id, dto);

        UsuarioEditDTO responseDTO = new UsuarioEditDTO();
        responseDTO.setNome(usuarioEditado.getNome());
        responseDTO.setContato(usuarioEditado.getContato());
        responseDTO.setEmail(usuarioEditado.getEmail());
    
        return ResponseEntity.ok(responseDTO);

    }

}
