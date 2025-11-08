package com.edvaldo.perdidos_achados.models.dto.usuario.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioCreateDTO {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Email(message = "Email inválido")
    @NotBlank(message = "Email é obrigatório")
    private String email;


    @NotBlank(message = "Numero de celular é obrigatório")
    @Pattern(
    regexp = "^\\d{11}$",
    message = "Número inválido. Use 11 dígitos: DDD + número")
   
    private String contato;
    
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    @NotBlank(message = "Senha é obrigatória")
    private String senha;

}