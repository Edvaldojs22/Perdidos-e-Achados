package com.edvaldo.perdidos_achados.models;

import java.time.LocalDate;

import javax.xml.crypto.Data;

import org.hibernate.annotations.ManyToAny;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ind;

    @NotBlank(message = "Nome obrigatório")
    private String nome;

    private String descricao;

    private String imagemUrl;

    private LocalDate dataPostado;

    private Boolean entregue;

    @ManyToAny
    @JoinColumn(name = "usuario_id")
    private Integer usuarioId;
    
}
