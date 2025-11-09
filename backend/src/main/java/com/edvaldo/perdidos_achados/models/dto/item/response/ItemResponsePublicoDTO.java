package com.edvaldo.perdidos_achados.models.dto.item.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.edvaldo.perdidos_achados.entity.enums.Categoria;
import com.edvaldo.perdidos_achados.entity.enums.Setor;

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
    private Setor setor;
    private Categoria categoria;
    private String imagemUrl;
    private BigDecimal recompensa;
    private LocalDateTime dataPostado;
    private String status;
   
}
