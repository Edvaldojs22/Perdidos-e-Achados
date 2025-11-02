package com.edvaldo.perdidos_achados.controller.item;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edvaldo.perdidos_achados.entity.Item;
import com.edvaldo.perdidos_achados.models.dto.item.requeste.ItemCreateDTO;
import com.edvaldo.perdidos_achados.models.dto.item.requeste.ItemEditDTO;
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
        responseDTO.setId(itemSalvo.getId());
        responseDTO.setNome(itemSalvo.getNome());
        responseDTO.setDescricao(itemSalvo.getDescricao());
        responseDTO.setImagemUrl(itemSalvo.getImagemUrl());
        responseDTO.setCategoria(itemSalvo.getCategoria());
        responseDTO.setCidade(itemSalvo.getCidade());
        responseDTO.setContato(itemSalvo.getContato());
        responseDTO.setStatus(itemSalvo.getStatus());
        responseDTO.setLocalRef(itemSalvo.getLocalRef());
        responseDTO.setNomePostador(itemSalvo.getUsuario().getNome());
      
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<ItemEditDTO>editar(@PathVariable Long id, @RequestBody @Valid ItemEditDTO dto, Authentication authentication){
        Item itemEditado = itemService.editarItemPorId(id,dto );
        
        ItemEditDTO responseItem = new ItemEditDTO();
        responseItem.setNome(itemEditado.getNome());
        responseItem.setCategoria(itemEditado.getCategoria());
        responseItem.setCidade(itemEditado.getCidade());
        responseItem.setContato(itemEditado.getContato());
        responseItem.setDescricao(itemEditado.getDescricao());
        responseItem.setImagemUrl(itemEditado.getImagemUrl());
        responseItem.setLocalRef(itemEditado.getLocalRef());
        responseItem.setStatus(itemEditado.getStatus());
      
        return ResponseEntity.ok(responseItem);
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Void>deletar(@PathVariable Long id, Authentication authentication) {
        itemService.deletarItemPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/achado/{id}")
    public ResponseEntity<String>confirmarRecuperacao(@PathVariable Long id, Authentication authentication){
        String mensagem = itemService.confirmarRecuperacaoDoItem(id);
        return ResponseEntity.ok(mensagem);
    }

}
