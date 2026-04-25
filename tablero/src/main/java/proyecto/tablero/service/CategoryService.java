package proyecto.tablero.service;
import proyecto.tablero.entity.Category;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import proyecto.tablero.repository.CategoryRepository;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;

     public Category add(Category category) {
        return categoryRepository.save(category);
    }

    public java.util.List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

     public Category update(int id, Category category) {
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory == null) {
            return null;
        }
        existingCategory.setNombre(category.getNombre());
        return categoryRepository.save(existingCategory);
    }

     public void delete(int id) {
         categoryRepository.deleteById(id);
     }
    
}
