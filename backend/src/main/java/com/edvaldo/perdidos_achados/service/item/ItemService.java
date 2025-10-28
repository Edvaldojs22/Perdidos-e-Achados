package com.edvaldo.perdidos_achados.service.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edvaldo.perdidos_achados.entity.Item;
import com.edvaldo.perdidos_achados.entity.Usuario;
import com.edvaldo.perdidos_achados.models.dto.item.requeste.ItemCreateDTO;
import com.edvaldo.perdidos_achados.repository.item.ItemRepository;
import com.edvaldo.perdidos_achados.repository.usuario.UsuarioRepositoy;

@Service
public class ItemService {
    @Autowired
    private  ItemRepository itemRepository;

    @Autowired
    private UsuarioRepositoy usuarioRepositoy;

    public Item cadastrarItem(ItemCreateDTO dto){
        Usuario usuario = usuarioRepositoy.findById(dto.getUsuarioId())
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Item item = new Item();
        item.setNome(dto.getNome());
        item.setDescricao(dto.getDescricao());
        item.setCategoria(dto.getCategoria());
        item.setImagemUrl(dto.getImagemUrl());
        item.setUsuario(usuario);

        return itemRepository.save(item);
        
    }

}
