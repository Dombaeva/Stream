package com.dmdev.jdbc.starter.dao;

import com.dmdev.jdbc.starter.entity.Role;
import com.dmdev.jdbc.starter.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {

    boolean delete(K id);

    E save(E entity);

    void update(E entity);

    Optional<E> findById(K id);

    List<E> findAll();
}
