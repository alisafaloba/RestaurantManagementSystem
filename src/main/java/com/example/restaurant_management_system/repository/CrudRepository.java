package com.example.restaurant_management_system.repository;

import java.util.List;

public interface CrudRepository<T>{
    void  save(T entity);


    void delete(String id);

    void update(T entity);
    T findById(String id);
    List<T> findAll();
}