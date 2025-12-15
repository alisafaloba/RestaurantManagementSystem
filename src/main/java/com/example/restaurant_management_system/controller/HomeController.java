package com.example.restaurant_management_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String home() {
        // Returnează numele fișierului HTML din templates fără extensie
        return "home/home";
    }
}
