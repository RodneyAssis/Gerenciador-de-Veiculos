package com.sergipetech.GerenciarVeiculos.repository.genetics;

import com.sergipetech.GerenciarVeiculos.repository.repositories.GenericRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


import java.util.Arrays;
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

    public <T> int save(String tableName, String idColumn, T entity) {
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();

        // Obtendo os nomes das colunas e os placeholders (?, ?, ?)
        String columns = Arrays.stream(fields)
                .filter(field ->!"id".equals(field.getName()))
                .map(field ->camelToSnake(field.getName()))
                .collect(Collectors.joining(", "));

        String placeholders = Arrays.stream(fields)
                .filter(field ->!"id".equals(field.getName()))
                .map(f -> "?")
                .collect(Collectors.joining(", "));

        // Construindo a Query SQL
        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";

        // Obtendo os valores dos campos do objeto
        Object[] values = Arrays.stream(fields)
                .filter(field ->!"id".equals(field.getName()))
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        return field.get(entity);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Erro ao acessar o campo: " + field.getName(), e);
                    }
                }).toArray();

        KeyHolder keyHolder = new GeneratedKeyHolder();

        if (idColumn != null){
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                for (int i = 0; i < values.length; i++) {
                    ps.setObject(i + 1, values[i]);
                }
                return ps;
            }, keyHolder);

            Map<String, Object> generatedId = keyHolder.getKeyList().get(0);

            return ((Number) generatedId.get(idColumn)).intValue();
        }

        jdbcTemplate.update(sql, values);

        return 0;
    }

    private String camelToSnake(String field) {
        StringBuilder snakeCaseString = new StringBuilder();
        char[] chars = field.toCharArray();
        for (char c : chars) {
            if (Character.isUpperCase(c)) {
                snakeCaseString.append("_");
                snakeCaseString.append(Character.toLowerCase(c));
            } else {
                snakeCaseString.append(c);
            }
        }
        return snakeCaseString.toString();
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
