package proyecto.tablero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import proyecto.tablero.entity.Publicacion;
import proyecto.tablero.entity.PublicacionesGuardadas;

public interface PublicacionesGuardadasRepository extends JpaRepository<PublicacionesGuardadas, Integer> {
    

    @Query("""
    SELECT pg.publicacion
    FROM PublicacionesGuardadas pg
    WHERE pg.user.id = :userId
    ORDER BY pg.fechaGuardado DESC
""")
List<Publicacion> findGuardadasByUsuarioOrdenadas(
    @Param("userId") Integer userId
);




}
