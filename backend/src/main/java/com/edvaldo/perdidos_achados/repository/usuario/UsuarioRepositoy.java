package com.edvaldo.perdidos_achados.repository.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edvaldo.perdidos_achados.entity.Usuario;

@Repository
public interface UsuarioRepositoy  extends JpaRepository<Usuario,Long>{
    Optional<Usuario>findByEmail(String email);
}

