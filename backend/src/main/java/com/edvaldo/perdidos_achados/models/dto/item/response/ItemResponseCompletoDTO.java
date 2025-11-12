package com.edvaldo.perdidos_achados.models.dto.item.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.edvaldo.perdidos_achados.entity.enums.Categoria;
import com.edvaldo.perdidos_achados.entity.enums.Setor;
public record ItemResponseCompletoDTO (
        long id,
        String nome,
        String descricao,
        String imagemUrl,
        Categoria categoria,
        String status,
        Setor setor,
        String localRef,
        String contato,
        BigDecimal recompensa,
        LocalDateTime dataPostado,
        LocalDateTime atualizadoEm,
        Long usuarioId
) {}
