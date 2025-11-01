package com.edvaldo.perdidos_achados.service.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edvaldo.perdidos_achados.entity.Usuario;
import com.edvaldo.perdidos_achados.exception.usuario.EmailJaCadastradoException;
import com.edvaldo.perdidos_achados.models.dto.usuario.request.UsuarioCreateDTO;
import com.edvaldo.perdidos_achados.repository.usuario.UsuarioRepositoy;

@Service
public class AuthService {

    private final UsuarioRepositoy usuarioRepositoy;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepositoy usuarioRepositoy,PasswordEncoder passwordEncoder){
        this.usuarioRepositoy = usuarioRepositoy;
        this.passwordEncoder = passwordEncoder;
    }


     public Usuario cadastrar(UsuarioCreateDTO dto){
        if (usuarioRepositoy.findByEmail(dto.getEmail()).isPresent()) {
        throw new EmailJaCadastradoException("E-mail já cadastrado");
    }
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
       
        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());
        usuario.setSenha(senhaCriptografada);

        return usuarioRepositoy.save(usuario);
    }
  
   
    
}
