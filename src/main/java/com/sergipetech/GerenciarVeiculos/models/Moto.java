package com.sergipetech.GerenciarVeiculos.models;

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
    private Integer cilindrada;
    private Integer fkVeiculo;

    public Moto() {}

    public Moto(Integer cilindrada, Integer fkVeiculo) {
        this.cilindrada = cilindrada;
        this.fkVeiculo = fkVeiculo;
    }
}
