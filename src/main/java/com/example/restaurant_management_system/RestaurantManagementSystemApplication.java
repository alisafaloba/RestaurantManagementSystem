package com.example.restaurant_management_system;

import com.example.restaurant_management_system.model.Bill;
import com.example.restaurant_management_system.model.Customer;
import com.example.restaurant_management_system.model.Order;
import com.example.restaurant_management_system.repository.BillRepository;
import com.example.restaurant_management_system.repository.CustomerRepository;
import com.example.restaurant_management_system.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestaurantManagementSystemApplication {


    public static void main(String[] args) {
        SpringApplication.run(RestaurantManagementSystemApplication.class, args);
    }



}

