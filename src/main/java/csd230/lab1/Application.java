package csd230.lab1;

import csd230.lab1.entities.UserEntity;
import csd230.lab1.repositories.UserEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // Seed default users for Lecture 2.6
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
