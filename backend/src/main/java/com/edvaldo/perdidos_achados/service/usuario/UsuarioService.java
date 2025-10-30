package com.edvaldo.perdidos_achados.service.usuario;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edvaldo.perdidos_achados.entity.Usuario;
import com.edvaldo.perdidos_achados.exception.usuario.EmailJaCadastradoException;
import com.edvaldo.perdidos_achados.exception.usuario.UsuarioComItensException;
import com.edvaldo.perdidos_achados.exception.usuario.UsuarioNaoEncontradoException;
import com.edvaldo.perdidos_achados.models.dto.usuario.request.UsuarioCreateDTO;
import com.edvaldo.perdidos_achados.repository.item.ItemRepository;
import com.edvaldo.perdidos_achados.repository.usuario.UsuarioRepositoy;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepositoy usuarioRepositoy;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
  
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepositoy.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

    return org.springframework.security.core.userdetails.User
        .withUsername(usuario.getEmail())
        .password(usuario.getSenha())
        .authorities(new ArrayList<>()) // ou roles se quiser
        .build();
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
