package com.edvaldo.perdidos_achados.models.dto.usuario.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthResponse {
    private String token;

    public AuthResponse(String tpken){
        this.token = tpken;
    }
   
}
