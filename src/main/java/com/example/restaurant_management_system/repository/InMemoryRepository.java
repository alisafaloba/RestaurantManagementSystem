package com.example.restaurant_management_system.repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementare de bază a CrudRepository care salvează elementele într-o listă în memorie.
 */
public class InMemoryRepository<T> implements CrudRepository<T> {

    private final List<T> storage = new ArrayList<>();
    private final Class<T> type;

    public InMemoryRepository(Class<T> type) {
        this.type = type;
    }

    @Override
    public void save(T entity) {
        String id = getIdValue(entity);
        if (id == null) {
            throw new IllegalArgumentException("Entity ID cannot be null");
        }

        // verificăm dacă există deja
        T existing = findById(id);
        if (existing == null) {
            storage.add(entity);
        } else {
            update(entity);
        }
    }

    @Override
    public void delete(String id) {
        storage.removeIf(e -> id.equals(getIdValue(e)));
    }

    @Override
    public void update(T entity) {
        String id = getIdValue(entity);
        if (id == null) {
            throw new IllegalArgumentException("Entity ID cannot be null");
        }

        for (int i = 0; i < storage.size(); i++) {
            if (id.equals(getIdValue(storage.get(i)))) {
                storage.set(i, entity);
                return;
            }
        }

        throw new IllegalArgumentException("Entity with ID " + id + " does not exist");
    }

    @Override
    public T findById(String id) {
        for (T entity : storage) {
            if (id.equals(getIdValue(entity))) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage);
    }

    /**
     * Extrage valoarea câmpului "id" din entitate folosind reflecția.
     */
    private String getIdValue(T entity) {
        try {
            Field idField = type.getDeclaredField("id");
            idField.setAccessible(true);
            Object value = idField.get(entity);
            return value != null ? value.toString() : null;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Entity must have a field named 'id'", e);
        }
    }
}
