package com.edvaldo.perdidos_achados.service.item;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.edvaldo.perdidos_achados.entity.Item;
import com.edvaldo.perdidos_achados.entity.Usuario;
import com.edvaldo.perdidos_achados.exception.item.AcessoNegadoException;
import com.edvaldo.perdidos_achados.exception.item.ItemNaoEncontradoException;
import com.edvaldo.perdidos_achados.models.dto.item.requeste.ItemCreateDTO;
import com.edvaldo.perdidos_achados.repository.item.ItemRepository;

import jakarta.validation.Valid;
@Service
public class ItemService {

    private final  ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }


    public Item cadastrarItem(@Valid ItemCreateDTO dto){
        Authentication authenticacao = SecurityContextHolder.getContext().getAuthentication();

        if(authenticacao == null || authenticacao.getPrincipal() instanceof String && authenticacao.getPrincipal().equals("anonymousUser")){
            throw new AcessoNegadoException("Acesso negado. Usuário não autenticado.");
        }
        
        Usuario usuario = (Usuario) authenticacao.getPrincipal();
        Item item = new Item();
        item.setNome(dto.getNome());
        item.setDescricao(dto.getDescricao());
        item.setCategoria(dto.getCategoria());
        item.setImagemUrl(dto.getImagemUrl());
        item.setUsuario(usuario);

        return itemRepository.save(item);
        
    }

    public void deletarPorId (Long id, String emailLogado ){
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNaoEncontradoException("Item não encontrado"));

         if(!item.getUsuario().getEmail().equals(emailLogado)) {
                throw new AcessoNegadoException("Você não tem permissão para excluir esse item");
        }
        
        itemRepository.delete(item);
    }
}
