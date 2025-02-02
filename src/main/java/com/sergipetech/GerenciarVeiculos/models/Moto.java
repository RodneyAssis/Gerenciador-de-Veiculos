package com.sergipetech.GerenciarVeiculos.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "moto", schema = "comum")
@AllArgsConstructor
@Getter @Setter
public class Moto extends Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_moto")
    private int id;
    private String cilindrada;
    @Column(name = "fk_veiculo")
    @JsonIgnore
    private int fkVeiculo;

    public Moto() {}

    public Moto(String marca, String modelo, String fabricante, String cor, Integer ano, Double preco, int id, String cilindrada, int fkVeiculo) {
        super(marca, modelo, fabricante, cor, ano, preco);
        this.id = id;
        this.cilindrada = cilindrada;
        this.fkVeiculo = fkVeiculo;
    }

    public Moto(String cilindrada, int fkVeiculo) {
        this.cilindrada = cilindrada;
        this.fkVeiculo = fkVeiculo;
    }

    public Moto(int id, String cilindrada) {
        this.cilindrada = cilindrada;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(String cilindrada) {
        this.cilindrada = cilindrada;
    }

    public int getFkVeiculo() {
        return fkVeiculo;
    }

    public void setFkVeiculo(int fkVeiculo) {
        this.fkVeiculo = fkVeiculo;
    }


}
