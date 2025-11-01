package com.edvaldo.perdidos_achados.controller.item;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edvaldo.perdidos_achados.entity.Item;
import com.edvaldo.perdidos_achados.models.dto.item.requeste.ItemCreateDTO;
import com.edvaldo.perdidos_achados.models.dto.item.response.ItemResponseDTO;
import com.edvaldo.perdidos_achados.service.item.ItemService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/items")
public class ItemController {


    private final ItemService itemService;

    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }


    @PostMapping("/novo")
    public ResponseEntity<ItemResponseDTO>criarItem(@RequestBody @Valid ItemCreateDTO dto){
        Item itemSalvo = itemService.cadastrarItem(dto);

        ItemResponseDTO responseDTO = new ItemResponseDTO();
        responseDTO.setNome(dto.getNome());
        responseDTO.setCategoria(dto.getCategoria());
        responseDTO.setDescricao(dto.getDescricao());
        responseDTO.setImagemUrl(dto.getImagemUrl());
        responseDTO.setLocalRef(dto.getLocalRef());
        responseDTO.setCidade(dto.getCidade());
        responseDTO.setId(itemSalvo.getId());
        responseDTO.setNomePostador(itemSalvo.getUsuario().getNome());
      
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Void>deletarItem(@PathVariable Long id, Authentication authenticator){
        String emailLogado = authenticator.getName();

        itemService.deletarPorId(id,emailLogado);
        return ResponseEntity.noContent().build();
    }
}
