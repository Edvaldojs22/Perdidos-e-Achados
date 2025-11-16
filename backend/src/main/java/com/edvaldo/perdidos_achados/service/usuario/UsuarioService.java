package com.edvaldo.perdidos_achados.service.usuario;
import org.springframework.stereotype.Service;
import com.edvaldo.perdidos_achados.entity.Usuario;
import com.edvaldo.perdidos_achados.exception.usuario.UsuarioComItensException;
import com.edvaldo.perdidos_achados.exception.usuario.UsuarioNaoEncontradoException;
import com.edvaldo.perdidos_achados.models.dto.usuario.request.UsuarioEditDTO;
import com.edvaldo.perdidos_achados.repository.item.ItemRepository;
import com.edvaldo.perdidos_achados.repository.usuario.UsuarioRepositoy;

import jakarta.validation.Valid;

@Service
public class UsuarioService  {
    private final UsuarioRepositoy usuarioRepositoy;
    private final ItemRepository itemRepository;

    public UsuarioService (UsuarioRepositoy usuarioRepositoy,ItemRepository itemRepository){

        this.usuarioRepositoy = usuarioRepositoy;
        this.itemRepository = itemRepository;
    }

    public void deletarPorId(Long id){
        if(itemRepository.existsByUsuarioId(id)){
            throw new UsuarioComItensException("É preciso excluir os itens antes");
        }
        Usuario usuario = usuarioRepositoy.findById(id)
        .orElseThrow(()-> new UsuarioNaoEncontradoException("Usuário com ID " + id + " não encontrado"));
        usuarioRepositoy.delete(usuario);
    }

    public Usuario editarUsuario(Long id,@Valid UsuarioEditDTO dto){
        Usuario usuario = usuarioRepositoy.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

    
        if (dto.getNome() != null) usuario.setNome(dto.getNome());
        if (dto.getNome() != null) usuario.setEmail(dto.getEmail());
        if (dto.getNome() != null) usuario.setNome(dto.getNome());
        if (dto.getNome() != null) usuario.setContato(dto.getContato());

        return usuarioRepositoy.save(usuario);

    }
}
