package proyecto.tablero.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import proyecto.tablero.entity.PublicacionesGuardadas;

public interface PublicacionesGuardadasRepository extends JpaRepository<PublicacionesGuardadas, Integer> {
    
}
