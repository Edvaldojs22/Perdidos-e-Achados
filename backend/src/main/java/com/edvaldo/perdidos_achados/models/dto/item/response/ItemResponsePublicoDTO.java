package com.edvaldo.perdidos_achados.models.dto.item.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponsePublicoDTO {
    private String nome;
    private String setor;
    private String imagemUrl;
    private BigDecimal recompensa;
    private LocalDateTime dataPostado;
   
}
