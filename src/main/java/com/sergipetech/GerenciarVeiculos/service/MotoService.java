package com.sergipetech.GerenciarVeiculos.service;

import com.sergipetech.GerenciarVeiculos.dto.VeiculoDTO;
import com.sergipetech.GerenciarVeiculos.models.Moto;
import com.sergipetech.GerenciarVeiculos.models.Veiculo;
import com.sergipetech.GerenciarVeiculos.repository.genetics.AbstractGenericRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class MotoService extends AbstractGenericRepository<Moto, Integer> {

    public MotoService(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public void salvar(VeiculoDTO veiculo) {
        Integer idVeiculo = save("core.veiculo", "id",new Veiculo(veiculo.getMarca(), veiculo.getModelo(), veiculo.getFabricante(), veiculo.getCor(), veiculo.getAno(), veiculo.getPreco()));
        save(getTableName(), null, new Moto(veiculo.getCilindrada(), idVeiculo));

    }

    @Override
    protected String getTableName() {
        return "comum.moto";
    }

    @Override
    protected RowMapper<Moto> getRowMapper() {
        return null;
    }

    @Override
    protected String getIdColumn() {
        return "id_moto";
    }
}
