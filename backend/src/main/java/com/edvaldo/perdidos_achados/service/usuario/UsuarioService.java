package com.edvaldo.perdidos_achados.service.usuario;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edvaldo.perdidos_achados.entity.Usuario;
import com.edvaldo.perdidos_achados.exception.usuario.EmailJaCadastradoException;
import com.edvaldo.perdidos_achados.repository.usuario.UsuarioRepositoy;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositoy usuarioRepositoy;

    public Usuario cadastrar(Usuario usuario){
        if (usuarioRepositoy.findByEmail(usuario.getEmail()).isPresent()) {
        throw new EmailJaCadastradoException("E-mail já cadastrado");
    }
        return usuarioRepositoy.save(usuario);
    }
    
}
