package com.edvaldo.perdidos_achados.models.dto.item.requeste;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCreateDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    private String descricao;

    @NotBlank(message = "Imagem ou url de imagem obrigatório")
    private String imagemUrl;

    private String categoria;
    
    @NotBlank(message = "Referência de onde encontrou é obrigatório")
     private String localRef;

    @NotNull(message = "Id do usuário autenticado é necessário")
    private Integer usuarioId;

}
