package proyecto.tablero.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import proyecto.tablero.entity.AdminUser;
import proyecto.tablero.entity.User;
import proyecto.tablero.repository.AdminUserRepository;
import proyecto.tablero.repository.UserRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository,
            AdminUserRepository adminUserRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {

            if (userRepository.findByCorreo("admin").isEmpty()) {
                User admin = new User();
                admin.setCorreo("admin");
                admin.setContrasena(passwordEncoder.encode("admin"));
                userRepository.save(admin);



                AdminUser adminUser = new AdminUser();
                adminUser.setUser(admin);
                adminUserRepository.save(adminUser);
            }

            if (userRepository.findByCorreo("user").isEmpty()) {
                User user = new User();
                user.setCorreo("user");
                user.setContrasena(passwordEncoder.encode("user"));

                userRepository.save(user);
            }
        };
    }
}