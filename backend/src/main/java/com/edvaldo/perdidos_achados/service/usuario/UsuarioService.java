package com.edvaldo.perdidos_achados.service.usuario;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edvaldo.perdidos_achados.entity.Usuario;
import com.edvaldo.perdidos_achados.exception.usuario.EmailJaCadastradoException;
import com.edvaldo.perdidos_achados.exception.usuario.UsuarioComItensException;
import com.edvaldo.perdidos_achados.exception.usuario.UsuarioNaoEncontradoException;
import com.edvaldo.perdidos_achados.repository.item.ItemRepository;
import com.edvaldo.perdidos_achados.repository.usuario.UsuarioRepositoy;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositoy usuarioRepositoy;

    @Autowired
    private ItemRepository itemRepository;

    public Usuario cadastrar(Usuario usuario){
        if (usuarioRepositoy.findByEmail(usuario.getEmail()).isPresent()) {
        throw new EmailJaCadastradoException("E-mail já cadastrado");
    }
        return usuarioRepositoy.save(usuario);
    }
    

    public void deletarPorId(Long id){
        if(itemRepository.existsByUsuarioId(id)){
            throw new UsuarioComItensException("Usuário possui itens cadastrados. Exclua os itens antes de excluir o usuário.");
        }
        Usuario usuario = usuarioRepositoy.findById(id)
        .orElseThrow(()-> new UsuarioNaoEncontradoException("Usuário com ID " + id + " não encontrado"));
        usuarioRepositoy.delete(usuario);
    }
}
