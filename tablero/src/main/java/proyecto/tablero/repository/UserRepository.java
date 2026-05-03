package proyecto.tablero.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import proyecto.tablero.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByCorreo(String correo);
}
