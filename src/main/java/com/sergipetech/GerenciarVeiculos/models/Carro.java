package com.sergipetech.GerenciarVeiculos.models;

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
    private Integer quantidadePortas;
    @Column(name = "tipo_combustivel")
    private String tipoCombustivel;
    private Integer fkVeiculo;

    public Carro(Integer quantidadePortas, String tipoCombustivel, Integer fkVeiculo) {
        this.quantidadePortas = quantidadePortas;
        this.tipoCombustivel = tipoCombustivel;
        this.fkVeiculo = fkVeiculo;
    }
}
