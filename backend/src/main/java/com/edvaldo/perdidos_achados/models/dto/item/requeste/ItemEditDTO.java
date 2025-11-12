package com.edvaldo.perdidos_achados.models.dto.item.requeste;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import com.edvaldo.perdidos_achados.entity.enums.Categoria;
import com.edvaldo.perdidos_achados.entity.enums.Setor;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemEditDTO {

    
    private String descricao;
    private String localRef;

    @DecimalMin(value = "0.0", inclusive = false, message = "A recompensa deve ser maior que zero")
    private BigDecimal recompensa;

   @NotBlank(message = "Nome é obrigatório")
    private String nome;

    
    private MultipartFile imagem;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Categoria é obrigatoria")
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Setor é obrigatório")
    private Setor setor;

    @NotBlank(message = "Contato é obrigatório")
    private String contato;

}
