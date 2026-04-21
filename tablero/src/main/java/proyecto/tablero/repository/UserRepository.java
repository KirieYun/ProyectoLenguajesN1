package proyecto.tablero.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import proyecto.tablero.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
}
