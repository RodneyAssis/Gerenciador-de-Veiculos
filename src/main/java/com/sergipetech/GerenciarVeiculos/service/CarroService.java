package com.sergipetech.GerenciarVeiculos.service;

import com.sergipetech.GerenciarVeiculos.dto.VeiculoDTO;
import com.sergipetech.GerenciarVeiculos.models.Carro;
import com.sergipetech.GerenciarVeiculos.models.Veiculo;
import com.sergipetech.GerenciarVeiculos.repository.genetics.AbstractGenericRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CarroService extends AbstractGenericRepository<Carro, UUID> {

    public CarroService(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public void salvar(VeiculoDTO veiculo) {
        Integer idVeiculo = save("core.veiculo", "id",new Veiculo(veiculo.getMarca(), veiculo.getModelo(), veiculo.getFabricante(), veiculo.getCor(), veiculo.getAno(), veiculo.getPreco()));
        save(getTableName(), null, new Carro(veiculo.getQuantidadePortas(), veiculo.getTipoCombustivel(), idVeiculo));
    }

    @Override
    protected String getTableName() {
        return "comum.carro";
    }

    @Override
    protected RowMapper<Carro> getRowMapper() {
        return null;
    }

    @Override
    protected String getIdColumn() {
        return "";
    }
}
