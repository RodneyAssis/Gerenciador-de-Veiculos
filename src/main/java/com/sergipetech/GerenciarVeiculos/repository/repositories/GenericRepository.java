package com.sergipetech.GerenciarVeiculos.repository.repositories;

import java.util.List;

public interface GenericRepository<T, ID> {
    T findById(ID id);

    List<T> findAll();

    void save(T t);

    void deletebyId(ID id);

    void update(T t);
}
