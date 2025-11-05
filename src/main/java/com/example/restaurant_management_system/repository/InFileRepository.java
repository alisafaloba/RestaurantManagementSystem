package com.example.restaurant_management_system.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class InFileRepository<T> implements CrudRepository<T> {

    private final File file;
    private final Class<T> type;
    private final ObjectMapper mapper = new ObjectMapper();
    private List<T> storage;

    public InFileRepository(Class<T> type, String filename) {
        this.type = type;
        this.file = new File("src/main/resources/data/" + filename);
        loadFromFile();
    }

    private void loadFromFile() {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                storage = new ArrayList<>();
                saveToFile();
            } else {
                storage = mapper.readValue(file, new TypeReference<List<T>>() {});
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading data from " + file.getName(), e);
        }
    }

    private void saveToFile() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, storage);
        } catch (IOException e) {
            throw new RuntimeException("Error saving data to " + file.getName(), e);
        }
    }

    @Override
    public void save(T entity) {
        String id = getIdValue(entity);
        T existing = findById(id);
        if (existing == null) {
            storage.add(entity);
        } else {
            update(entity);
            return;
        }
        saveToFile();
    }

    @Override
    public void delete(String id) {
        storage.removeIf(e -> id.equals(getIdValue(e)));
        saveToFile();
    }

    @Override
    public void update(T entity) {
        String id = getIdValue(entity);
        for (int i = 0; i < storage.size(); i++) {
            if (id.equals(getIdValue(storage.get(i)))) {
                storage.set(i, entity);
                saveToFile();
                return;
            }
        }
        throw new IllegalArgumentException("Entity with ID " + id + " not found");
    }

    @Override
    public T findById(String id) {
        return storage.stream()
                .filter(e -> id.equals(getIdValue(e)))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage);
    }

    private String getIdValue(T entity) {
        try {
            Field idField = findField(type, "id");
            idField.setAccessible(true);
            Object value = idField.get(entity);
            return value != null ? value.toString() : null;
        } catch (Exception e) {
            throw new RuntimeException("Entity must have an 'id' field", e);
        }
    }

    private Field findField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }
}
