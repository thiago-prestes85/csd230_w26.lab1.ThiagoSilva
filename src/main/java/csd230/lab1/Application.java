package csd230.lab1;

import csd230.lab1.entities.UserEntity;
import csd230.lab1.repositories.UserEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // Option A (Lecture 2.9): Global CORS configuration
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Allow access to all /api endpoints from any origin
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }


    @Bean
    CommandLineRunner seedUsers(UserEntityRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            // Create admin user if it doesn't exist
            UserEntity admin = userRepository.findByUsername("admin").orElse(null);
            if (admin == null) {
                userRepository.save(new UserEntity("admin", passwordEncoder.encode("admin"), "ROLE_ADMIN"));
            }

            // Create regular user if it doesn't exist
            UserEntity user = userRepository.findByUsername("user").orElse(null);
            if (user == null) {
                userRepository.save(new UserEntity("user", passwordEncoder.encode("user"), "ROLE_USER"));
            }

            System.out.println("Lecture 2.6 users ready: admin/admin and user/user");
        };
    }
}
