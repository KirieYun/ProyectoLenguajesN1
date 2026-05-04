package proyecto.tablero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import proyecto.tablero.entity.Publicacion;

public interface PublicacionRepository extends JpaRepository<Publicacion, Integer> {

        @Query("""
                        SELECT p FROM Publicacion p
                        ORDER BY p.fechaPublicacion DESC
                        """)
        List<Publicacion> getPublicacionesOrdenadas();

        @Query("""
                        SELECT p FROM Publicacion p
                        WHERE p.category.id = :categoryId
                        ORDER BY p.fechaPublicacion DESC
                        """)
        List<Publicacion> getPublicacionesPorCategoriaOrdenadas(
                        @Param("categoryId") Integer categoryId);

        @Modifying
        @Transactional
        void deleteByUserId(Integer userId);

        List<Publicacion> findByUserIdOrderByFechaPublicacionDesc(int userId);
}