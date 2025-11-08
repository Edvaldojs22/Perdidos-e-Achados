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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.edvaldo.perdidos_achados.entity.Item;
import com.edvaldo.perdidos_achados.entity.Usuario;
import com.edvaldo.perdidos_achados.exception.item.ImagemVaziaException;
import com.edvaldo.perdidos_achados.models.dto.item.requeste.ItemFormDTO;
import com.edvaldo.perdidos_achados.models.dto.item.requeste.ItemEditDTO;
import com.edvaldo.perdidos_achados.models.dto.item.response.ItemResponseCompletoDTO;
import com.edvaldo.perdidos_achados.service.item.ItemService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;



    public ItemController(ItemService itemService){
        this.itemService = itemService;    
    }

    @PostMapping( value =  "/item/cadastrar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ItemResponseCompletoDTO>criarItem(@Valid @RequestPart("dados") ItemFormDTO dto,@RequestPart("imagem")MultipartFile imagem) throws IOException{

      if (imagem == null || imagem.isEmpty()) {
        throw new ImagemVaziaException("Imagem é obrigatória e não pode estar vazia.");
        }

        dto.setImagem(imagem);


        Item itemSalvo = itemService.cadastrarItem(dto);

        ItemResponseCompletoDTO responseItem = new ItemResponseCompletoDTO();
        responseItem.setId(itemSalvo.getId());
        responseItem.setNome(itemSalvo.getNome());
        responseItem.setDescricao(itemSalvo.getDescricao());
        responseItem.setImagemUrl(itemSalvo.getImagemUrl());
        responseItem.setCategoria(itemSalvo.getCategoria());
        responseItem.setStatus(itemSalvo.getStatus());
        responseItem.setSetor(itemSalvo.getSetor());
        responseItem.setLocalRef(itemSalvo.getLocalRef());
        responseItem.setContato(itemSalvo.getContato());
        responseItem.setDataPostado(itemSalvo.getDataPostado());
       
        return ResponseEntity.status(HttpStatus.CREATED).body(responseItem);
    }


    @PutMapping( value =  "/item/{itemId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ItemResponseCompletoDTO>editar(@PathVariable Long itemId, @ModelAttribute @Valid ItemEditDTO dto, Authentication authentication) throws IOException{
        Item itemEditado = itemService.editarItemPorId(itemId,dto );
        
       ItemResponseCompletoDTO responseItem = new ItemResponseCompletoDTO();
        responseItem.setNome(itemEditado.getNome());
        responseItem.setCategoria(itemEditado.getCategoria());
        responseItem.setSetor(itemEditado.getSetor());
        responseItem.setContato(itemEditado.getContato());
        responseItem.setDescricao(itemEditado.getDescricao());
        responseItem.setImagemUrl(itemEditado.getImagemUrl());
        responseItem.setLocalRef(itemEditado.getLocalRef());
        responseItem.setStatus(itemEditado.getStatus());
      
        return ResponseEntity.ok(responseItem);
    }
    
    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<Void>deletar(@PathVariable Long itemId, Authentication authentication) {
         Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        itemService.deletarItemPorId(itemId, usuarioLogado.getId());

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/item/{itemId}")
    public ResponseEntity<String>confirmarRecuperacao(@PathVariable Long itemId, Authentication authentication){
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        String mensagem = itemService.confirmarRecuperacaoDoItem(itemId,usuarioLogado.getId());
        return ResponseEntity.ok(mensagem);
    }

  @GetMapping("/itens")
    public ResponseEntity<Object>items(Authentication authentication){
        List<Object> itens = itemService.todosItens();
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<Object> itemInfo (@PathVariable Long itemId, Authentication authentication){
        Long usuarioId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
            usuarioId = usuarioLogado.getId();
        }

        Object item = itemService.itemPorId(itemId, usuarioId);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/meus-itens")
    public ResponseEntity<List<ItemResponseCompletoDTO>> buscaItens(Authentication authentication){
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
         List<ItemResponseCompletoDTO> itens = itemService.buscarItensDoUsuario(usuarioLogado.getId());
        return ResponseEntity.ok(itens);

    }
}
