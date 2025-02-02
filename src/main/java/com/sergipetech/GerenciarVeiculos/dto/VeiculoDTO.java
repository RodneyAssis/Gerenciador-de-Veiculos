package com.sergipetech.GerenciarVeiculos.dto;

public class VeiculoDTO {
    private int id;
    private String fabricante;
    private String modelo;
    private String marca;
    private Integer ano;
    private String cor;
    private Double preco;
    private TipoCategoriaEnum categoria;
    private Integer idCarroMoto;
    private Integer quantidadePortas;
    private String tipoCombustivel;
    private String cilindrada;

    public VeiculoDTO(String fabricante, String modelo, String marca, Integer ano, String cor, Double preco, String categoria, Integer quantidadePortas, String tipoCombustivel, String cilindrada) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public TipoCategoriaEnum getCategoria() {
        return categoria;
    }

    public void setCategoria(TipoCategoriaEnum categoria) {
        this.categoria = categoria;
    }

    public Integer getIdCarroMoto() {
        return idCarroMoto;
    }

    public void setIdCarroMoto(Integer idCarroMoto) {
        this.idCarroMoto = idCarroMoto;
    }

    public Integer getQuantidadePortas() {
        return quantidadePortas;
    }

    public void setQuantidadePortas(Integer quantidadePortas) {
        this.quantidadePortas = quantidadePortas;
    }

    public String getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(String tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public String getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(String cilindrada) {
        this.cilindrada = cilindrada;
    }
}
