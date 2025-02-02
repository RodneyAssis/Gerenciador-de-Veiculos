package com.sergipetech.GerenciarVeiculos.repository.genetics;

import jakarta.persistence.Column;
import org.springframework.jdbc.core.RowMapper;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GenericRowMapper<T> implements RowMapper<T> {

    private final Class<T> type;
    private final Map<String, Method> setterCache = new HashMap<>();

    public GenericRowMapper(Class<T> type) {
        this.type = type;
        cacheSetters();
    }

    private void cacheSetters() {
        Class<?> currentClass = type;

        while (currentClass != null) { // Percorre a hierarquia de classes
            for (Field field : currentClass.getDeclaredFields()) {
                String setterName = "set" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
                try {
                    Method setter = currentClass.getMethod(setterName, field.getType()); // Busca na classe correta
                    setterCache.put(field.getName(), setter);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            currentClass = currentClass.getSuperclass();
        }
    }


    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            T instance = type.getDeclaredConstructor().newInstance();
            Class<?> currentClass = type;

            while (currentClass != null) { // Percorre a hierarquia da classe
                try {
                    for (Field field : currentClass.getDeclaredFields()) {
                        field.setAccessible(true);
                        Column columnAnnotation = field.getAnnotation(Column.class);
                        String columnName = (columnAnnotation != null) ? columnAnnotation.name() : field.getName();
                        Object value = rs.getObject(columnName);

                        if (value != null) {
                            Method setter = setterCache.get(field.getName());
                            if (setter != null) {
                                setter.invoke(instance, convertValue(value, field.getType()));
                            }
                        }
                    }
                } catch (Exception e) {
                    return instance;
                }
                currentClass = currentClass.getSuperclass(); // Move para a superclasse
            }

            return instance;
        } catch (Exception e) {
            throw new SQLException("Erro ao mapear objeto", e);
        }
    }


    private Object convertValue(Object value, Class<?> targetType) {
        if (value == null) {
            return null;
        }

        if (value instanceof java.sql.Timestamp && targetType == java.time.LocalDateTime.class) {
            return ((java.sql.Timestamp) value).toLocalDateTime();
        }

        if (value instanceof java.sql.Date && targetType == java.time.LocalDate.class) {
            return ((java.sql.Date) value).toLocalDate();
        }

        if (value instanceof java.math.BigDecimal && targetType == int.class || targetType == Integer.class) {
            assert value instanceof Integer;
            return value;
        }

        if (value instanceof java.math.BigDecimal && targetType == int.class || targetType == Integer.class) {
            assert value instanceof java.math.BigDecimal;
            return ((java.math.BigDecimal) value).intValue();
        }

        // Adicione mais conversões conforme necessário

        return value;
    }
}