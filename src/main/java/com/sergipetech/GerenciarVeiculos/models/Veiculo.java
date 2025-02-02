package com.sergipetech.GerenciarVeiculos.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "veiculo", schema = "core")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_veiculo")
    private int id;
    private String marca;
    private String modelo;
    private String fabricante;
    private String cor;
    private Integer ano;
    private Double preco;

    public Veiculo() {}

    public Veiculo(String marca, String modelo, String fabricante, String cor, Integer ano, Double preco) {
        this.marca = marca;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.cor = cor;
        this.ano = ano;
        this.preco = preco;
    }
}
