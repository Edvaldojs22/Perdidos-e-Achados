package com.edvaldo.perdidos_achados.models.dto.item.requeste;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemFormDTO {


    private String descricao;
    private String categoria;
    private String localRef;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Imagem ou url de imagem obrigatório")
    private MultipartFile imagem;

    @NotNull(message = "Cidade é obrigatório")
    private String cidade;

    @NotBlank(message = "Numero de celular é obrigatório")
    @Pattern(
    regexp = "^\\(?\\d{2}\\)?\\s?9\\d{4}-?\\d{4}$",
    message = "Formato inválido. Use o padrão (XX) 9XXXX-XXXX")
    private String contato;
    

}
