package com.edvaldo.perdidos_achados.controller.item;

import java.io.IOException;
import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edvaldo.perdidos_achados.entity.Item;
import com.edvaldo.perdidos_achados.models.dto.item.requeste.ItemFormDTO;
import com.edvaldo.perdidos_achados.models.dto.item.requeste.ItemEditDTO;
import com.edvaldo.perdidos_achados.models.dto.item.response.ItemResponseCompletoDTO;
import com.edvaldo.perdidos_achados.service.item.ItemService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;   

    public ItemController(ItemService itemService){
        this.itemService = itemService;
    
    }

    @PostMapping( value =  "/novo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ItemResponseCompletoDTO>criarItem( @Valid @ModelAttribute ItemFormDTO dto) throws IOException{
        Item itemSalvo = itemService.cadastrarItem(dto);

        ItemResponseCompletoDTO responseItem = new ItemResponseCompletoDTO();
        responseItem.setId(itemSalvo.getId());
        responseItem.setNome(itemSalvo.getNome());
        responseItem.setDescricao(itemSalvo.getDescricao());
        responseItem.setImagemUrl(itemSalvo.getImagemUrl());
        responseItem.setCategoria(itemSalvo.getCategoria());
        responseItem.setStatus(itemSalvo.getStatus());
        responseItem.setCidade(itemSalvo.getCidade());
        responseItem.setLocalRef(itemSalvo.getLocalRef());
        responseItem.setContato(itemSalvo.getContato());
        responseItem.setDataPostado(itemSalvo.getDataPostado());
       
        return ResponseEntity.status(HttpStatus.CREATED).body(responseItem);
    }


    @PutMapping( value =  "/editar/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ItemResponseCompletoDTO>editar(@PathVariable Long id, @ModelAttribute @Valid ItemEditDTO dto, Authentication authentication) throws IOException{
        Item itemEditado = itemService.editarItemPorId(id,dto );
        
       ItemResponseCompletoDTO responseItem = new ItemResponseCompletoDTO();
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

    @GetMapping
    public ResponseEntity<Object>items(Authentication authentication){
        List<Object> itens = itemService.todosItens();
        return ResponseEntity.ok(itens);
    }
    
}
