package com.sergipetech.GerenciarVeiculos.controller;


import com.sergipetech.GerenciarVeiculos.dto.TipoCategoriaEnum;
import com.sergipetech.GerenciarVeiculos.dto.VeiculoDTO;
import com.sergipetech.GerenciarVeiculos.models.Carro;
import com.sergipetech.GerenciarVeiculos.models.Moto;
import com.sergipetech.GerenciarVeiculos.service.CarroService;
import com.sergipetech.GerenciarVeiculos.service.MotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/veiculo")
public class VeiculoController {

    private final MotoService motoService;
    private final CarroService carroService;

    public VeiculoController(MotoService motoService, CarroService carroService) {
        this.motoService = motoService;
        this.carroService = carroService;
    }

    @GetMapping("/moto/listar")
    public ResponseEntity<List<Moto>> listarMoto() {
        return ResponseEntity.ok().body(motoService.buscar());

    }

    @GetMapping("/carro/listar")
    public ResponseEntity<List<Carro>> listarCarros() {
        return ResponseEntity.ok().body(carroService.buscar());
    }

    @GetMapping("/moto/{id}")
    public ResponseEntity<?> buscarMoto(@PathVariable(name = "id") Integer id){
        Optional<Moto> consultarMoto = motoService.buscarPorId(id);
        if (consultarMoto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID não encontrado ou produto não existe");
        }
        return ResponseEntity.ok(consultarMoto);
    }

    @GetMapping("/carro/{id}")
    public ResponseEntity<?> buscarCarro(@PathVariable(name = "id") Integer id){
        Optional<Carro> consultarCarro = carroService.buscarPorId(id);
        if (consultarCarro.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID não encontrado ou produto não existe");
        }
        return ResponseEntity.ok(consultarCarro);
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody VeiculoDTO veiculoDTO) {
        if (veiculoDTO.getCategoria().equals(TipoCategoriaEnum.Moto)) {
            motoService.salvar(veiculoDTO);
            return ResponseEntity.ok().build();
        } else if (veiculoDTO.getCategoria().equals(TipoCategoriaEnum.Carro)) {
            carroService.salvar(veiculoDTO);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> alterarMoto(@RequestBody VeiculoDTO veiculoDTO) {
        if (veiculoDTO.getCategoria().equals(TipoCategoriaEnum.Moto)) {
            motoService.atualizarDadosMoto(veiculoDTO);
            return ResponseEntity.ok().build();
        } else if (veiculoDTO.getCategoria().equals(TipoCategoriaEnum.Carro)) {
            carroService.atualizarDadosCarro(veiculoDTO);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/moto/{id}")
    public ResponseEntity<?> excluirMoto(@PathVariable("id") Integer id) {
        motoService.excluir(id);
        return ResponseEntity.ok().body("Conteudo Excluido com sucesso:" + id);
    }

    @DeleteMapping("/carro/{id}")
    public ResponseEntity<?> excluirCarro(@PathVariable("id") Integer id) {
        motoService.excluir(id);
        return ResponseEntity.ok().body("Conteudo Excluido com sucesso:" + id);
    }
}
