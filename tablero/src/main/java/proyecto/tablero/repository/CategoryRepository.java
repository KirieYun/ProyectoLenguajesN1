package proyecto.tablero.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import proyecto.tablero.entity.Category;
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
}
