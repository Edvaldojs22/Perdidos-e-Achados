package com.edvaldo.perdidos_achados.repository.item;


import org.springframework.data.jpa.repository.JpaRepository;

import com.edvaldo.perdidos_achados.entity.Item;

public interface ItemRepository extends JpaRepository<Item,Long> {
    boolean existsByUsuarioId(Long id);
    
}

  