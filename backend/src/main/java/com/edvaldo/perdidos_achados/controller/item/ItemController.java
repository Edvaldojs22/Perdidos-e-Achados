package com.edvaldo.perdidos_achados.controller.item;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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


        ItemResponseCompletoDTO responseItem = new ItemResponseCompletoDTO(
        itemSalvo.getId(),
        itemSalvo.getNome(),
        itemSalvo.getDescricao(),
        itemSalvo.getImagemUrl(),
        itemSalvo.getCategoria(),
        itemSalvo.getStatus(),
        itemSalvo.getSetor(),
        itemSalvo.getLocalRef(),
        itemSalvo.getContato(),
        itemSalvo.getRecompensa(),
        itemSalvo.getDataPostado(),
        itemSalvo.getAtualizadoEm(),
        itemSalvo.getUsuario().getId()
        );
       
        return ResponseEntity.status(HttpStatus.CREATED).body(responseItem);
    }


    @PutMapping( value =  "/item/{itemId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ItemResponseCompletoDTO>editar(
        @PathVariable Long itemId,
        @Valid @RequestPart("dados") ItemEditDTO dto,
        @RequestPart(value = "imagem", required = false) MultipartFile imagem ) throws IOException{

        if (imagem != null && !imagem.isEmpty()) {
          dto.setImagem(imagem);
        }

       

        Item itemEditado = itemService.editarItemPorId(itemId,dto );
        
       ItemResponseCompletoDTO responseItem = new ItemResponseCompletoDTO(
               itemEditado.getId(),
               itemEditado.getNome(),
               itemEditado.getDescricao(),
               itemEditado.getImagemUrl(),
               itemEditado.getCategoria(),
               itemEditado.getStatus(),
               itemEditado.getSetor(),
               itemEditado.getLocalRef(),
               itemEditado.getContato(),
               itemEditado.getRecompensa(),
               itemEditado.getDataPostado(),
               itemEditado.getAtualizadoEm(),
               itemEditado.getUsuario().getId()
       );
      
        return ResponseEntity.ok(responseItem);
    }
    
    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<Void>deletar(@PathVariable Long itemId, Authentication authentication) {
         Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        itemService.deletarItemPorId(itemId, usuarioLogado.getId());

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/item/recuperar/{itemId}")
    public ResponseEntity<String>confirmarRecuperacao(@PathVariable Long itemId, Authentication authentication){
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        String mensagem = itemService.confirmarRecuperacaoDoItem(itemId,usuarioLogado.getId());
        return ResponseEntity.ok(mensagem);
    }

    @GetMapping("/itens")
    public ResponseEntity<Object>items(){
        List<Object> itens = itemService.todosItens();
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<ItemResponseCompletoDTO> itemInfo (@PathVariable Long itemId){
         ItemResponseCompletoDTO item = itemService.itemPorId(itemId);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/meus-itens")
    public ResponseEntity<List<ItemResponseCompletoDTO>> buscaItens(Authentication authentication){
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
         List<ItemResponseCompletoDTO> itens = itemService.buscarItensDoUsuario(usuarioLogado.getId());
        return ResponseEntity.ok(itens);

    }
}
