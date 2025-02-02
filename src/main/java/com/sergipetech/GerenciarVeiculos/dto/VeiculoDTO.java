package com.sergipetech.GerenciarVeiculos.dto;

public class VeiculoDTO {
    private String fabricante;
    private String modelo;
    private String marca;
    private Integer ano;
    private String cor;
    private Double preco;
    private TipoCategoriaEnum categoria;
    private Integer quantidadePortas;
    private String tipoCombustivel;
    private Integer cilindrada;

    public VeiculoDTO(String fabricante, String modelo, String marca, Integer ano, String cor, Double preco, String categoria, Integer quantidadePortas, String tipoCombustivel, Integer cilindrada) {
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.cor = cor;
        this.preco = preco;
        if (categoria.equals(TipoCategoriaEnum.Carro.name())){
            this.categoria = TipoCategoriaEnum.Carro;
            this.quantidadePortas = quantidadePortas;
            this.tipoCombustivel = tipoCombustivel;
        } else if (categoria.equals(TipoCategoriaEnum.Moto.name())) {
            this.categoria = TipoCategoriaEnum.Moto;
            this.cilindrada = cilindrada;
        }
    }

    public String getFabricante() {
        return fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public String getMarca() {
        return marca;
    }

    public Integer getAno() {
        return ano;
    }

    public String getCor() {
        return cor;
    }

    public Double getPreco() {
        return preco;
    }

    public TipoCategoriaEnum getCategoria() {
        return categoria;
    }

    public Integer getQuantidadePortas() {
        return quantidadePortas;
    }

    public String getTipoCombustivel() {
        return tipoCombustivel;
    }

    public Integer getCilindrada() {
        return cilindrada;
    }
}
