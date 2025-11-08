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
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemFormDTO {

    private String descricao;
    private String localRef;
     private MultipartFile imagem;

    @DecimalMin(value = "0.0", inclusive = false, message = "A recompensa deve ser maior que zero")
    private BigDecimal recompensa;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Categoria é obrigatória")
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Setor é obrigatório")
    private Setor setor;

    @NotBlank(message = "Numero de celular é obrigatório")
    @Pattern(
    regexp = "^(\\(?\\d{2}\\)?\\s?)?(9\\d{4}-?\\d{4})$",
    message = "Formato inválido. Use (XX) 9XXXX-XXXX ou 9XXXXXXXX"
    ) 
    private String contato;
    

}
