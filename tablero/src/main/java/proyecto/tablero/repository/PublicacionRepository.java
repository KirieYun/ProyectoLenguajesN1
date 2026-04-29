package proyecto.tablero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import proyecto.tablero.entity.Publicacion;

public interface PublicacionRepository extends JpaRepository<Publicacion, Integer> {
    List<Publicacion> findByCategoryId(Integer categoryId);
}
