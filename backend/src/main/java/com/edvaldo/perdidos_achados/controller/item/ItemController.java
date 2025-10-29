package com.edvaldo.perdidos_achados.controller.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;


    @PostMapping("novo")
    public ResponseEntity<ItemResponseDTO>criarItem(@RequestBody @Valid ItemCreateDTO dto){
        Item itemSalvo = itemService.cadastrarItem(dto);

        ItemResponseDTO responseDTO = new ItemResponseDTO();
        responseDTO.setCategoria(dto.getCategoria());
        responseDTO.setDescricao(dto.getDescricao());
        responseDTO.setImagemUrl(dto.getImagemUrl());
        responseDTO.setLocalRef(dto.getLocalRef());
        responseDTO.setCidade(dto.getCidade());
        responseDTO.setId(itemSalvo.getId());
        responseDTO.setNomePostador(itemSalvo.getUsuario().getNome());
      
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    
}
