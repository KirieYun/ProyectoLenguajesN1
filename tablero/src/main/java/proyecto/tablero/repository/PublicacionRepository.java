package proyecto.tablero.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import proyecto.tablero.entity.Publicacion;

public interface PublicacionRepository extends JpaRepository<Publicacion, Integer> {
    
}
