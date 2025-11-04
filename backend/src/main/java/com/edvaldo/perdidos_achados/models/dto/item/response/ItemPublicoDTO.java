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
public class ItemPublicoDTO {
    private String nome;
    private String cidade;
    private String imagemUrl;
    private LocalDateTime dataPostado;
}
