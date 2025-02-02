package com.sergipetech.GerenciarVeiculos.controller;


import com.sergipetech.GerenciarVeiculos.dto.VeiculoDTO;
import com.sergipetech.GerenciarVeiculos.models.Carro;
import com.sergipetech.GerenciarVeiculos.service.CarroService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("carro")
public class CarroController {

    private final CarroService carroService;

    public CarroController(CarroService carroService) {
        this.carroService = carroService;
    }

    @GetMapping
    public List<Carro> listarCarros() {
        List<Carro> carros = new ArrayList<Carro>();

        return carros;
    }

    @PostMapping
    public ResponseEntity<?> adicionarCarro(@RequestBody VeiculoDTO veiculoDTO) {
        carroService.salvar(veiculoDTO);
        return ResponseEntity.ok().build();
    }
}
