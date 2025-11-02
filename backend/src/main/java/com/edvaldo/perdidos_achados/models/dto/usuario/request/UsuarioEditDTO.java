package com.edvaldo.perdidos_achados.models.dto.usuario.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioEditDTO {
    private String nome;
    
    
    @Email(message = "Email inválido")
    private String email;
    
    @NotBlank(message = "Numero de celular é obrigatório")
    @Pattern(
    regexp = "^\\(?\\d{2}\\)?\\s?9\\d{4}-?\\d{4}$",
    message = "Formato inválido. Use o padrão (XX) 9XXXX-XXXX")
    private String contato;
}
