package com.edvaldo.perdidos_achados.service.item;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import com.edvaldo.perdidos_achados.entity.Item;
import com.edvaldo.perdidos_achados.entity.Usuario;
import com.edvaldo.perdidos_achados.exception.item.AcessoNegadoException;
import com.edvaldo.perdidos_achados.exception.item.ItemNaoEncontradoException;
import com.edvaldo.perdidos_achados.models.dto.item.requeste.ItemFormDTO;
import com.edvaldo.perdidos_achados.models.dto.item.requeste.ItemEditDTO;
import com.edvaldo.perdidos_achados.models.dto.item.response.ItemResponseCompletoDTO;
import com.edvaldo.perdidos_achados.models.dto.item.response.ItemResponsePublicoDTO;
import com.edvaldo.perdidos_achados.repository.item.ItemRepository;
import com.edvaldo.perdidos_achados.service.image.ImagemService;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;

import jakarta.validation.Valid;
@Service
public class ItemService {

    private final  ItemRepository itemRepository;
     private final ImagemService imagemService;

    public ItemService(ItemRepository itemRepository,ImagemService imagemService){
        this.itemRepository = itemRepository;
        this.imagemService = imagemService;
    } 


    public Item cadastrarItem( ItemFormDTO dto) throws IOException{
        String imagemUrl = imagemService.salvarImagemFirebase(dto.getImagem());
        System.out.println("Url da imagem criada " + imagemUrl);

        Authentication authenticacao = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authenticacao.getPrincipal();

        Item item = new Item();
        item.setNome(dto.getNome());
        item.setDescricao(dto.getDescricao());
        item.setImagemUrl(imagemUrl);
        item.setCategoria(dto.getCategoria());
        item.setSetor(dto.getSetor());
        item.setContato(dto.getContato());
        item.setRecompensa(dto.getRecompensa());
        item.setLocalRef(dto.getLocalRef());
        item.setUsuario(usuario);

        return itemRepository.save(item);
        
    }

  public void deletarItemPorId(Long itemId, Long usuarioId) {
    Item item = itemRepository.findById(itemId)
        .orElseThrow(() -> new ItemNaoEncontradoException("Item não encontrado"));

    if (!item.getUsuario().getId().equals(usuarioId)) {
        throw new AcessoNegadoException("Você não tem permissão para excluir esse item");
    }

    String imagemUrl = item.getImagemUrl();
    if (imagemUrl != null && !imagemUrl.isEmpty()) {
        try {
            String nomeImagem = imagemUrl.substring(imagemUrl.lastIndexOf("/") + 1);
            int indexInterrogacao = nomeImagem.indexOf("?");
            if (indexInterrogacao != -1) {
                nomeImagem = nomeImagem.substring(0, indexInterrogacao);
            }

            Bucket bucket = StorageClient.getInstance().bucket();
            boolean deletado = bucket.get(nomeImagem).delete();

            if (deletado) {
                System.out.println("🧹 Imagem deletada do Firebase: " + nomeImagem);
            } else {
                System.err.println("⚠️ Imagem não encontrada no Firebase: " + nomeImagem);
            }
        } catch (Exception e) {
            System.err.println("Erro ao deletar imagem do Firebase: " + e.getMessage());
        }
    }

    itemRepository.delete(item);
}

    public Item editarItemPorId(Long itemId, @Valid ItemEditDTO dto) throws IOException{
        Authentication authenticacao = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authenticacao.getPrincipal();

        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new ItemNaoEncontradoException("Item não encontrado"));

        if(!item.getUsuario().getId().equals(usuario.getId())){
                throw new AcessoNegadoException("Você não tem permissão para editar ese item");
        }
        if (dto.getNome() != null) item.setNome(dto.getNome());
        if (dto.getDescricao() != null) item.setDescricao(dto.getDescricao());
        if (dto.getCategoria() != null) item.setCategoria(dto.getCategoria());
        if (dto.getSetor() != null) item.setSetor(dto.getSetor());
        if (dto.getLocalRef() != null) item.setLocalRef(dto.getLocalRef());
        if (dto.getContato() != null) item.setContato(dto.getContato());
        if(dto.getRecompensa() != null) item.setRecompensa(dto.getRecompensa());

         // Se uma nova imagem foi enviada, salva e atualiza a URL
        if (dto.getImagem() != null && !dto.getImagem().isEmpty()) {
        String novaImagemUrl = imagemService.salvarImagemFirebase(dto.getImagem());
        item.setImagemUrl(novaImagemUrl);
        System.out.println("Nova imagem enviada: " + dto.getImagem().getOriginalFilename());

     }

        return itemRepository.save(item);
    }

    public String confirmarRecuperacaoDoItem(Long itemId, Long usuarioId){
           Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new ItemNaoEncontradoException("Item não encontrado"));

              if(!item.getUsuario().getId().equals(usuarioId)){
                throw new AcessoNegadoException("Você não tem permissão para excluir esse item");
        }

        item.setStatus("ENCONTRADO");
        itemRepository.save(item);
        return "Parabéns o item foi Encontrado";
    }



    public ItemResponseCompletoDTO itemPorId(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNaoEncontradoException("Item não encontrado"));
        return new ItemResponseCompletoDTO(
                item.getId(),
                item.getNome(),
                item.getDescricao(),
                item.getImagemUrl(),
                item.getCategoria(),
                item.getStatus(),
                item.getSetor(),
                item.getLocalRef(),
                item.getContato(),
                item.getRecompensa(),
                item.getDataPostado(),
                item.getAtualizadoEm(),
                item.getUsuario().getId());
    }


    public List<Object> todosItens() {
    Authentication autenticacao = SecurityContextHolder.getContext().getAuthentication();
    List<Item> itens = itemRepository.findAll();
    System.out.println("Itens encontrados: " + itens.size());


    boolean autenticado = autenticacao != null 
        && autenticacao.isAuthenticated() 
        && !(autenticacao instanceof AnonymousAuthenticationToken);

    if (autenticado) {
        return itens.stream()
            .map(item -> new ItemResponseCompletoDTO(
                    item.getId(),
                    item.getNome(),
                    item.getDescricao(),
                    item.getImagemUrl(),
                    item.getCategoria(),
                    item.getStatus(),
                    item.getSetor(),
                    item.getLocalRef(),
                    item.getContato(),
                    item.getRecompensa(),
                    item.getDataPostado(),
                    item.getAtualizadoEm(),
                    item.getUsuario().getId()
            ))

            .collect(Collectors.toList());
    } else {
        return itens.stream()
            .map(item -> ItemResponsePublicoDTO.builder()
                .nome(item.getNome())
                .imagemUrl(item.getImagemUrl())
                .setor(item.getSetor())
                .recompensa(item.getRecompensa())
                .status(item.getStatus())
                .build())
            .collect(Collectors.toList());
    }
}

    public List<ItemResponseCompletoDTO>buscarItensDoUsuario(Long usuarioId){
        List<Item>itens = itemRepository.findByUsuarioId(usuarioId);

        if (itens == null || itens.isEmpty()) {
            throw new ItemNaoEncontradoException("Nenhum item encontrado");
        }

     return itens.stream()
            .map(item ->  new ItemResponseCompletoDTO(
                    item.getId(),
                    item.getNome(),
                    item.getDescricao(),
                    item.getImagemUrl(),
                    item.getCategoria(),
                    item.getStatus(),
                    item.getSetor(),
                    item.getLocalRef(),
                    item.getContato(),
                    item.getRecompensa(),
                    item.getDataPostado(),
                    item.getAtualizadoEm(),
                    item.getUsuario().getId()))
            .collect(Collectors.toList());

    }

}
