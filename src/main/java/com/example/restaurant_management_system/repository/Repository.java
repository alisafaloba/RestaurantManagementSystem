package com.example.restaurant_management_system.repository;
import java.util.List;

public interface Repository<T> {
    void add(T entity);
    void update(T entity);
    void delete(T entity);
    T findById(int id);
    List<T> findAll();
}