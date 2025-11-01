package com.edvaldo.perdidos_achados.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.edvaldo.perdidos_achados.repository.usuario.UsuarioRepositoy;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UsuarioRepositoy usuarioRepositoy;

    public UserDetailsServiceImpl(UsuarioRepositoy usuarioRepositoy){
        this.usuarioRepositoy = usuarioRepositoy;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return usuarioRepositoy.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário  não encontrado: " + username ));
        
    }


    
}
