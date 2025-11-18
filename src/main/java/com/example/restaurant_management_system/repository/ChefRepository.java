package com.example.restaurant_management_system.repository;

import com.example.restaurant_management_system.model.Chef;
import org.springframework.stereotype.Repository;


@Repository
public class ChefRepository extends InFileRepository<Chef> {

    public ChefRepository() {
        super(Chef.class,"chefs.json");
    }
}
