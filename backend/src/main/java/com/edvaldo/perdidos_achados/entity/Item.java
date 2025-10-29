package com.edvaldo.perdidos_achados.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "items")
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;
    private String descricao;
    private String imagemUrl;
    private String categoria;
    private Boolean entregue = false;
    private String cidade;
    private String localRef;

    @CreationTimestamp
    private LocalDateTime dataPostado;

    @UpdateTimestamp
    private LocalDateTime atualizadoEm;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
}
