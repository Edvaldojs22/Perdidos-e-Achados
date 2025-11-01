package com.edvaldo.perdidos_achados.service.usuario;
import org.springframework.stereotype.Service;
import com.edvaldo.perdidos_achados.entity.Usuario;
import com.edvaldo.perdidos_achados.exception.usuario.UsuarioComItensException;
import com.edvaldo.perdidos_achados.exception.usuario.UsuarioNaoEncontradoException;
import com.edvaldo.perdidos_achados.repository.item.ItemRepository;
import com.edvaldo.perdidos_achados.repository.usuario.UsuarioRepositoy;

@Service
public class UsuarioService  {
    private final UsuarioRepositoy usuarioRepositoy;
    private final ItemRepository itemRepository;

    public UsuarioService (UsuarioRepositoy usuarioRepositoy,ItemRepository itemRepository){
        this.usuarioRepositoy = usuarioRepositoy;
        this.itemRepository = itemRepository;
    }
    public void deletarPorId(Long id){
        if(itemRepository.existsByUsuarioId(id)){
            throw new UsuarioComItensException("Usuário possui itens cadastrados. Exclua os itens antes de excluir o usuário.");
        }
        Usuario usuario = usuarioRepositoy.findById(id)
        .orElseThrow(()-> new UsuarioNaoEncontradoException("Usuário com ID " + id + " não encontrado"));
        usuarioRepositoy.delete(usuario);
    }
}
