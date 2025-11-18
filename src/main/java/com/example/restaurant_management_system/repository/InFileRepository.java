package com.example.restaurant_management_system.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InFileRepository<T> implements CrudRepository<T> {
    private final Class<T> type;
    private final String filePath;
    private final ObjectMapper objectMapper;
    private List<T> data;

    public InFileRepository(Class<T> type, String fileName) {
        this.type = type;
        this.filePath = "src/main/resources/data/" + fileName;
        this.objectMapper = new ObjectMapper();
        this.data = loadData();
    }

    // ---------- Private Helpers ----------
    private List<T> loadData() {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
                Files.write(path, "[]".getBytes());
            }

            var typeFactory = objectMapper.getTypeFactory();
            var listType = typeFactory.constructCollectionType(List.class, this.type);

            return objectMapper.readValue(path.toFile(), listType);

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveData() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
        } catch (IOException e) {
            throw new RuntimeException("Error saving data to file: " + e.getMessage(), e);
        }
    }

    private String getIdValue(T entity) {
        Class<?> current = type;
        while (current != null) {
            try {
                var field = current.getDeclaredField("id");
                field.setAccessible(true);
                return (String) field.get(entity);
            } catch (NoSuchFieldException ignored) {
                current = current.getSuperclass(); // check parent class
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Entity must have a field named 'id'");
    }


    // ---------- CRUD ----------
    @Override
    public void save(T entity) {
        String id = getIdValue(entity);
        if (findById(id) != null) {
            throw new IllegalArgumentException("Entity with ID " + id + " already exists.");
        }
        data.add(entity);
        saveData();
    }

    @Override
    public void delete(String id) {
        data.removeIf(e -> getIdValue(e).equals(id));
        saveData();
    }

    @Override
    public void update(T entity) {
        String id = getIdValue(entity);
        for (int i = 0; i < data.size(); i++) {
            if (getIdValue(data.get(i)).equals(id)) {
                data.set(i, entity);
                saveData();
                return;
            }
        }
        throw new NoSuchElementException("Entity with ID " + id + " not found for update.");
    }

    @Override
    public T findById(String id) {
        return data.stream()
                .filter(e -> getIdValue(e).equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(data);
    }
}
