package com.sergipetech.GerenciarVeiculos.repository.genetics;

import com.sergipetech.GerenciarVeiculos.repository.genetics.GenericRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public abstract class AbstractGenericRepository<T, ID> implements GenericRepository<T, ID> {

    protected final JdbcTemplate jdbcTemplate;

    public AbstractGenericRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected abstract String getTableName();

    protected abstract RowMapper<T> getRowMapper();

    protected abstract String getIdColumn();

    @Override
    public void save(T entity) {
        throw new UnsupportedOperationException("Método save deve ser implementado na subclasse.");
    }

    @Override
    public void update(T entity) {
        throw new UnsupportedOperationException("Método update deve ser implementado na subclasse.");
    }

    @Override
    public void deletebyId(ID id) {
        String sql = "DELETE FROM " + getTableName() + " WHERE " + getIdColumn() + " = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public T findById(ID id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumn() + " = ?";
        return jdbcTemplate.queryForObject(sql, getRowMapper(), id);
    }

    @Override
    public List<T> findAll() {
        String sql = "SELECT * FROM " + getTableName();
        return jdbcTemplate.query(sql, getRowMapper());
    }
}
