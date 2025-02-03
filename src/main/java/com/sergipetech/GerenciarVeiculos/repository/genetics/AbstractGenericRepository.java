package com.sergipetech.GerenciarVeiculos.repository.genetics;

import com.sergipetech.GerenciarVeiculos.repository.repositories.GenericRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


import java.util.List;

public abstract class AbstractGenericRepository<T, ID> implements GenericRepository<T, ID> {

    protected final JdbcTemplate jdbcTemplate;

    public AbstractGenericRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected abstract String getTableName();

    protected abstract RowMapper<T> getRowMapper();

    protected abstract String getIdColumn();

    protected abstract String getJoinColumn();

    protected abstract String getJoinForeignKey();

    protected abstract String getJoinTableName();

    public <T> int save(String tableName, String idColumn, T entity) {
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();


        String columns = Arrays.stream(fields).filter(field -> !"id".equals(field.getName())).map(field -> camelToSnake(field.getName())).collect(Collectors.joining(", "));

        String placeholders = Arrays.stream(fields).filter(field -> !"id".equals(field.getName())).map(f -> "?").collect(Collectors.joining(", "));

        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";

        // Obtendo os valores dos campos do objeto
        Object[] values = Arrays.stream(fields).filter(field -> !"id".equals(field.getName())).map(field -> {
            field.setAccessible(true);
            try {
                return field.get(entity);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Erro ao acessar o campo: " + field.getName(), e);
            }
        }).toArray();

        KeyHolder keyHolder = new GeneratedKeyHolder();


        if (idColumn != null) {
            // Salvando registros no bando e retornando o id
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

    public <T> int update(String tableName, T entity) {
        Class<?> clazz = entity.getClass();
        List<Field> fields = new ArrayList<>();

        // Percorre toda a hierarquia da classe (incluindo superclasse)
        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }

        // Obtém o campo ID automaticamente (assumindo que a anotação @Id é usada)
        Field idField = fields.stream()
                .filter(field -> field.isAnnotationPresent(Id.class)) // Verifica se há anotação @Id
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Campo ID não encontrado na entidade."));

        String idColumn = idField.isAnnotationPresent(Column.class) ?
                idField.getAnnotation(Column.class).name() : camelToSnake(idField.getName());

        List<Field> nonNullFields = fields.stream()
                .filter(field -> {
                    field.setAccessible(true);
                    try {
                        return field.get(entity) != null; // Mantém apenas os campos com valor
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Erro ao acessar o campo: " + field.getName(), e);
                    }
                })
                .collect(Collectors.toList());

        // Constrói a cláusula SET apenas com os campos não nulos
        String setClause = nonNullFields.stream()
                .map(field -> {
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    String columnName = (columnAnnotation != null) ? columnAnnotation.name() : camelToSnake(field.getName());
                    return columnName + " = ?";
                })
                .collect(Collectors.joining(", "));

        String sql = "UPDATE " + tableName + " SET " + setClause + " WHERE " + idColumn + " = ?";

        // Obtém os valores dos campos não nulos
        Object[] values = nonNullFields.stream()
                .map(field -> {
                    try {
                        return field.get(entity);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Erro ao acessar o campo: " + field.getName(), e);
                    }
                })
                .toArray();

        idField.setAccessible(true);
        Object idValue;
        try {
            idValue = idField.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Erro ao acessar o campo ID", e);
        }

        Object[] allValues = Arrays.copyOf(values, values.length + 1);
        allValues[values.length] = idValue;

        return jdbcTemplate.update(sql, allValues);
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
    public T findById(ID id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumn() + " = ?";
        return jdbcTemplate.queryForObject(sql, getRowMapper(), id);
    }

    @Override
    public List<T> findAll() {
        String sql = "SELECT * FROM " + getTableName() + " t " +
                "JOIN " + getJoinTableName() + " jt ON jt." + getJoinColumn() + " = t." + getJoinForeignKey();
        return jdbcTemplate.query(sql, getRowMapper());
    }

    @Override
    public T findByIdWithJoin(ID id) {
        String sql = "SELECT * FROM " + getTableName() + " t " +
                "JOIN " + getJoinTableName() + " jt ON jt." + getJoinColumn() + " = t." + getJoinForeignKey() +
                " WHERE t." + getIdColumn() + " = ?";

        return jdbcTemplate.queryForObject(sql, getRowMapper(), id);
    }

    @Override
    public void deletebyId(ID id) {
        String sqlDeleteChildren = "DELETE FROM " + getJoinTableName() +
                " WHERE " + getJoinColumn() + " IN (" +
                "SELECT " + getJoinForeignKey() + " FROM " + getTableName() +
                " WHERE " + getIdColumn() + " = ?)";

        jdbcTemplate.update(sqlDeleteChildren, id);


        String sqlDeleteParent = "DELETE FROM " + getTableName() +
                " WHERE " + getIdColumn() + " = ?";

        jdbcTemplate.update(sqlDeleteParent, id);

    }
}
