package com.edvaldo.perdidos_achados.models.dto.item.requeste;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemEditDTO {

    
    private String descricao;
    private String categoria;
    private String localRef;

   @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Imagem ou url de imagem obrigatório")
    private MultipartFile imagem;

    @NotBlank(message = "Setor é obrigatório")
    private String setor;

    @NotBlank(message = "Contato é obrigatório")
    private String contato;
    
    @NotBlank(message = "Status do item é obrigatório")
    private String status;
}
