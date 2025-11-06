package com.edvaldo.perdidos_achados.models.dto.item.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponseCompletoDTO  {
    private long id;
    private String nome;
    private String descricao;
    private String imagemUrl;
    private String categoria;
    private String status;
    private String setor;
    private String localRef;
    private String contato;
    private LocalDateTime dataPostado;
    private LocalDateTime atualizadoEm;
    private Long usuarioId ;
}
