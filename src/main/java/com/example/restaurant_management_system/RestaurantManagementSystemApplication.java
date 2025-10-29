package com.example.restaurant_management_system;

import com.example.restaurant_management_system.model.Bill;
import com.example.restaurant_management_system.repository.BillRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestaurantManagementSystemApplication {


    public static void main(String[] args) {
        SpringApplication.run(RestaurantManagementSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner loadData(BillRepository billRepository) {
        return args -> {
            billRepository.save(new Bill("B1", "O1", 120.5));
            billRepository.save(new Bill("B2", "O2", 89.99));
            billRepository.save(new Bill("B3", "O3", 45.0));
        };
    }
}
