package com.edvaldo.perdidos_achados.models.dto.item.requeste;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCreateDTO {


    private String descricao;
    private String categoria;
    private String localRef;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Imagem ou url de imagem obrigatório")
    private String imagemUrl;

    @NotBlank(message = "Cidade é obrigatório")
    private String cidade;

    @NotBlank(message = "Contato é obrigatório")
    private String contato;
    
    @NotBlank(message = "Status do item é obrigatório")
    private String status;


}
