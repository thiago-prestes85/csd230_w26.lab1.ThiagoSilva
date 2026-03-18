package csd230.lab1.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> user) {

        String username = user.get("username");
        String password = user.get("password");

        if (username.equals("admin") && password.equals("admin")) {
            return Map.of("token", "admin-token");
        }

        if (username.equals("user") && password.equals("user")) {
            return Map.of("token", "user-token");
        }

        throw new RuntimeException("Invalid credentials");
    }
}