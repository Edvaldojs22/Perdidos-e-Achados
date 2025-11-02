package com.edvaldo.perdidos_achados.models.dto.item.requeste;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemEditDTO {

    private String nome;
    private String descricao;
    private String imagemUrl;
    private String categoria;
    private String status;
    private String cidade;
    private String localRef;
    private String contato;
}
