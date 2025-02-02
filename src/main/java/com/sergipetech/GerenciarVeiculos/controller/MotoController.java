package com.sergipetech.GerenciarVeiculos.controller;

import com.sergipetech.GerenciarVeiculos.dto.VeiculoDTO;
import com.sergipetech.GerenciarVeiculos.models.Moto;
import com.sergipetech.GerenciarVeiculos.service.MotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("moto")
public class MotoController {
    private MotoService motoService;

    public MotoController(MotoService motoService) {
        this.motoService = motoService;
    }

    @GetMapping
    public List<Moto> listarCarros() {
        List<Moto> motos = new ArrayList<Moto>();
        return motos;
    }

    @PostMapping
    public ResponseEntity<Moto> adicionarMoto(@RequestBody VeiculoDTO veiculoDTO) {
        motoService.salvar(veiculoDTO);
        return ResponseEntity.ok().build();
    }
}
