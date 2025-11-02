package com.edvaldo.perdidos_achados.service.item;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.edvaldo.perdidos_achados.entity.Item;
import com.edvaldo.perdidos_achados.entity.Usuario;
import com.edvaldo.perdidos_achados.exception.item.AcessoNegadoException;
import com.edvaldo.perdidos_achados.exception.item.ItemNaoEncontradoException;
import com.edvaldo.perdidos_achados.models.dto.item.requeste.ItemCreateDTO;
import com.edvaldo.perdidos_achados.models.dto.item.requeste.ItemEditDTO;
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
        Usuario usuario = (Usuario) authenticacao.getPrincipal();
        
        Item item = new Item();
        item.setNome(dto.getNome());
        item.setDescricao(dto.getDescricao());
        item.setImagemUrl(dto.getImagemUrl());
        item.setCategoria(dto.getCategoria());
        item.setCidade(dto.getCidade());
        item.setContato(dto.getContato());
        item.setStatus(dto.getStatus());
        item.setLocalRef(dto.getLocalRef());
        item.setUsuario(usuario);

        return itemRepository.save(item);
        
    }

    public void deletarItemPorId (Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) auth.getPrincipal();

        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new ItemNaoEncontradoException("Item não encontrado"));

         if(!item.getUsuario().getId().equals(usuario.getId())) {
                throw new AcessoNegadoException("Você não tem permissão para excluir esse item");
        }
        
        itemRepository.delete(item);
    }

    public Item editarItemPorId(Long id, @Valid ItemEditDTO dto){
        Authentication authenticacao = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authenticacao.getPrincipal();

        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new ItemNaoEncontradoException("Item não encontrado"));

        if(!item.getUsuario().getId().equals(usuario.getId())){
                throw new AcessoNegadoException("Você não tem permissão para excluir esse item");
        }
        if (dto.getNome() != null) item.setNome(dto.getNome());
        if (dto.getDescricao() != null) item.setDescricao(dto.getDescricao());
        if (dto.getImagemUrl() != null) item.setImagemUrl(dto.getImagemUrl());
        if (dto.getCategoria() != null) item.setCategoria(dto.getCategoria());
        if (dto.getStatus() != null) item.setStatus(dto.getStatus());
        if (dto.getCidade() != null) item.setCidade(dto.getCidade());
        if (dto.getLocalRef() != null) item.setLocalRef(dto.getLocalRef());
        if (dto.getContato() != null) item.setContato(dto.getContato());

        return itemRepository.save(item);
    }

    public String confirmarRecuperacaoDoItem(Long id){
        Authentication authenticacao = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authenticacao.getPrincipal();

           Item item = itemRepository.findById(id)
            .orElseThrow(() -> new ItemNaoEncontradoException("Item não encontrado"));

              if(!item.getUsuario().getId().equals(usuario.getId())){
                throw new AcessoNegadoException("Você não tem permissão para excluir esse item");
        }

        item.setStatus("Encontrado");
        itemRepository.save(item);
        return "Parabéns o item foi Encontrado";
    }

}
