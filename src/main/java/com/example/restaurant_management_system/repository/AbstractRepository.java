package com.example.restaurant_management_system.repository;

import java.util.List;

public interface AbstractRepository <T>{
    void  save(T entity);
    void delete(Integer id);
    void update(T entity);
    T findById(Integer id);
    List<T> findAll();
}
