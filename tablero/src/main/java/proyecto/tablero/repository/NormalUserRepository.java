package proyecto.tablero.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import proyecto.tablero.entity.NormalUser;

public interface NormalUserRepository extends JpaRepository<NormalUser, Integer> {
    
}
