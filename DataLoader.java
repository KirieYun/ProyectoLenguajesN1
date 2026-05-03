package soda.api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import soda.api.entity.User;
import soda.api.repository.UserRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                userRepository.save(new User(
                        "admin",
                        passwordEncoder.encode("admin"),
                        "ROLE_ADMIN"
                ));
            }

            if (userRepository.findByUsername("user").isEmpty()) {
                userRepository.save(new User(
                        "user",
                        passwordEncoder.encode("user"),
                        "ROLE_USER"
                ));
            }
        };
    }
}