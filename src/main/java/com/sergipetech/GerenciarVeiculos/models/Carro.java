package com.sergipetech.GerenciarVeiculos.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "carro", schema = "comum")
@AllArgsConstructor
@Getter @Setter
public class Carro extends Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carro")
    private int id;
    @Column(name = "quantidade_portas")
    private int quantidadePortas;
    @Column(name = "tipo_combustivel")
    private String tipoCombustivel;
    @Column(name = "fk_veiculo")
    @JsonIgnore
    private int fkVeiculo;

    public Carro(Integer quantidadePortas, String tipoCombustivel, Integer fkVeiculo) {
        this.quantidadePortas = quantidadePortas;
        this.tipoCombustivel = tipoCombustivel;
        this.fkVeiculo = fkVeiculo;
    }

    public Carro() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidadePortas() {
        return quantidadePortas;
    }

    public void setQuantidadePortas(int quantidadePortas) {
        this.quantidadePortas = quantidadePortas;
    }

    public String getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(String tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public int getFkVeiculo() {
        return fkVeiculo;
    }

    public void setFkVeiculo(int fkVeiculo) {
        this.fkVeiculo = fkVeiculo;
    }
}
