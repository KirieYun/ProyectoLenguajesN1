package proyecto.tablero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proyecto.tablero.entity.AdminUser;

public interface AdminUserRepository extends JpaRepository<AdminUser, Integer> {
    
}
