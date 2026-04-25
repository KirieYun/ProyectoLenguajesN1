package proyecto.tablero.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import proyecto.tablero.entity.Category;
import proyecto.tablero.service.CategoryService;

@CrossOrigin(origins="*")
@Tag(name = "CategoryController", description = "Controlador para gestionar categorías")
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Obtener todas las categorías", description = "Devuelve una lista de todas las categorías registradas")
    public List<Category> getAllCategories() {
        return categoryService.getAll();
    }

     @GetMapping("/{id}")
     @Operation(summary = "Obtener una categoría por ID", description = "Devuelve una categoría específica según su ID")
     public Category getCategoryById(@PathVariable int id) {
         return categoryService.getById(id);
     }

     @PostMapping("/add")
     @Operation(summary = "Agregar una nueva categoría", description = "Crea una nueva categoría")
     public Category addCategory(@RequestParam Category category) {
         return categoryService.add(category);
     }

     @Operation(summary = "Actualizar una categoría", description = "Actualiza los datos de una categoría existente")
     @PostMapping("/{id}")
        public Category update(@PathVariable int id, @RequestParam Category category) {
             return categoryService.update(id, category);
        }

        @Operation(summary = "Eliminar una categoría", description = "Elimina una categoría existente")
        @DeleteMapping("/{id}")
        public void delete(@PathVariable int id) {
            categoryService.delete(id);
        }
    
}
