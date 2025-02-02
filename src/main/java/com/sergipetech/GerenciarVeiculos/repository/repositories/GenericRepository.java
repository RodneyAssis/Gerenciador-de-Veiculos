package com.sergipetech.GerenciarVeiculos.repository.repositories;

import java.util.List;

public interface GenericRepository<T, ID> {
    T findById(ID id);

    List<T> findAll();

    <T> int save(String tableName, String idColumn, T entity);

    <T> int update(String tableName, String idColumn, T entity);

    void deletebyId(ID id);


    T findByIdWithJoin(ID id);
}
