package com.sergipetech.GerenciarVeiculos.service;

import com.sergipetech.GerenciarVeiculos.dto.TipoCategoriaEnum;
import com.sergipetech.GerenciarVeiculos.dto.VeiculoDTO;
import com.sergipetech.GerenciarVeiculos.models.Carro;
import com.sergipetech.GerenciarVeiculos.models.Moto;
import com.sergipetech.GerenciarVeiculos.models.Veiculo;
import com.sergipetech.GerenciarVeiculos.repository.genetics.AbstractGenericRepository;
import com.sergipetech.GerenciarVeiculos.repository.genetics.GenericRowMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarroService extends AbstractGenericRepository<Carro, Integer> {

    public CarroService(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public List<VeiculoDTO> buscar() {
        var lista = findAll();
        List<VeiculoDTO> veiculos = new ArrayList<>();
        for (Carro carro : lista) {
            VeiculoDTO veiculoDTO = new VeiculoDTO();
            BeanUtils.copyProperties(carro, veiculoDTO);
            veiculoDTO.setCategoria(TipoCategoriaEnum.Carro);
            veiculos.add(veiculoDTO);
        }
        return veiculos;
    }

    public Optional<Carro> buscarPorId(Integer id) {
        var carro = Optional.ofNullable(findById(id));
        if (carro.isPresent() && carro.get().getFkVeiculo() != 0) {
            carro = Optional.ofNullable(findByIdWithJoin(id));
            return carro;
        }
        return Optional.ofNullable(carro.get());
    }

    public void salvar(VeiculoDTO veiculo) {
        Integer idVeiculo = save("core.veiculo", "id", new Veiculo(veiculo.getMarca(), veiculo.getModelo(), veiculo.getFabricante(), veiculo.getCor(), veiculo.getAno(), veiculo.getPreco()));
        save(getTableName(), null, new Carro(veiculo.getQuantidadePortas(), veiculo.getTipoCombustivel(), idVeiculo));
    }

    public void atualizarDadosCarro(VeiculoDTO veiculoDTO) {
    }

    public void excluir(Integer id) {
        deletebyId(id);
    }

    @Override
    protected String getIdColumn() {
        return "id_carro";
    }

    @Override
    protected String getJoinColumn() {
        return "id";
    }

    @Override
    protected String getJoinForeignKey() {
        return "fk_veiculo";
    }

    @Override
    protected String getTableName() {
        return "comum.carro";
    }

    @Override
    protected String getJoinTableName() {
        return "core.veiculo";
    }

    @Override
    protected RowMapper<Carro> getRowMapper() {
        return new GenericRowMapper<>(Carro.class);
    }

}
