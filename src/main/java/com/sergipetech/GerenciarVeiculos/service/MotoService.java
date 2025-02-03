package com.sergipetech.GerenciarVeiculos.service;

import com.sergipetech.GerenciarVeiculos.dto.TipoCategoriaEnum;
import com.sergipetech.GerenciarVeiculos.dto.VeiculoDTO;
import com.sergipetech.GerenciarVeiculos.models.Moto;
import com.sergipetech.GerenciarVeiculos.models.Veiculo;
import com.sergipetech.GerenciarVeiculos.repository.genetics.AbstractGenericRepository;
import com.sergipetech.GerenciarVeiculos.repository.genetics.GenericRowMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MotoService extends AbstractGenericRepository<Moto, Integer> {

    public MotoService(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public Optional<Moto> buscarPorId(Integer id) {
        var moto = Optional.ofNullable(findById(id));
        if (moto.isPresent() && moto.get().getFkVeiculo() != 0) {
            moto = Optional.ofNullable(findByIdWithJoin(id));
            return moto;
        }
        return Optional.ofNullable(moto.get());
    }

    public List<VeiculoDTO> buscar() {
        var lista = findAll();
        List<VeiculoDTO> veiculos = new ArrayList<>();
        for (Moto moto : lista) {
            VeiculoDTO veiculoDTO = new VeiculoDTO();
            BeanUtils.copyProperties(moto, veiculoDTO);
            veiculoDTO.setCategoria(TipoCategoriaEnum.Moto);
            veiculos.add(veiculoDTO);
        }
        return veiculos;
    }

    public void salvar(VeiculoDTO veiculo) {
        Integer idVeiculo = save("core.veiculo", "id", new Veiculo(veiculo.getMarca(), veiculo.getModelo(), veiculo.getFabricante(), veiculo.getCor(), veiculo.getAno(), veiculo.getPreco()));
        save(getTableName(), null, new Moto(veiculo.getCilindrada(), idVeiculo));

    }

    public ResponseEntity<Moto> atualizarDadosMoto(VeiculoDTO veiculo) {
        update(getTableName(), new Moto(veiculo.getId(), veiculo.getCilindrada()));
        return null;
    }

    public void excluir(Integer id) {
        deletebyId(id);
    }

    @Override
    protected RowMapper<Moto> getRowMapper() {
        return new GenericRowMapper<>(Moto.class);
    }

    @Override
    protected String getIdColumn() {return "id_moto";}

    @Override
    protected String getJoinColumn() {return "id";}

    @Override
    protected String getJoinForeignKey() {return "fk_veiculo";}

    @Override
    protected String getTableName() {return "comum.moto";}

    @Override
    protected String getJoinTableName() {return "core.veiculo";}

    @Override
    public <T> int save(String tableName, String idColumn, T entity) {
        return super.save(tableName, idColumn, entity);
    }

    @Override
    public <T> int update(String tableName, T entity) {
        return super.update(tableName, entity);
    }
}
