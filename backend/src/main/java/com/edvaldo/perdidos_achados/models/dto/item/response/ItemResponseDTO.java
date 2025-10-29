package com.edvaldo.perdidos_achados.models.dto.item.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String categoria;
    private String imagemUrl;
    private String localRef;
    private String cidade;


    private String nomePostador;
}
