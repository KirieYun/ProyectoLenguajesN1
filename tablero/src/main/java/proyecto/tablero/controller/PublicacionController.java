package proyecto.tablero.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import proyecto.tablero.entity.Publicacion;
import proyecto.tablero.service.PublicacionService;
import proyecto.tablero.service.UserService;

@CrossOrigin(origins = "*")
@Tag(name = "PublicacionController", description = "Controlador para gestionar publicaciones")
@RestController
@RequestMapping("/publicaciones")
public class PublicacionController {
    
private final UserService userService;
@Autowired
private PublicacionService publicacionService;

PublicacionController(UserService userService) {
    this.userService = userService;
}

    @GetMapping
        @Operation(summary = "Obtener todas las publicaciones", description = "Devuelve una lista de todas las publicaciones registradas")
    public List<proyecto.tablero.entity.Publicacion> getAllPublicaciones() {
        return publicacionService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una publicación por ID", description = "Devuelve una publicación específica según su ID")
    public proyecto.tablero.entity.Publicacion getPublicacionById(@PathVariable int id) {
        return publicacionService.getById(id);
    }

    @PostMapping("/add")
     @Operation(summary = "Agregar un nueva publicacion", description = "Crea un nueva publicacion")
     public Publicacion addPublicacion(@RequestParam String titulo, @RequestParam String contenido, @RequestParam Integer categoryId,@RequestParam(required = false) MultipartFile file, @RequestParam(value = "userId", defaultValue = "1") int userId) {
         return publicacionService.addPublicacion(titulo, contenido, categoryId, file, userService.getById(userId));

     }

        @Operation(summary = "Actualizar una publicacion", description = "Actualiza los datos de una publicacion existente")
        @PostMapping("/{id}")
        public Publicacion update(@PathVariable int id, @RequestParam String titulo, @RequestParam String contenido, @RequestParam Integer categoryId,@RequestParam(required = false) MultipartFile file) {
            return publicacionService.update(id, titulo, contenido, categoryId, file);
        }

        @Operation(summary = "Eliminar una publicacion", description = "Elimina una publicacion existente")
        @DeleteMapping("/{id}")
        public void delete(@PathVariable int id) {
            publicacionService.delete(id);
        }

        @Operation(summary = "Obtener publicaciones por categoría", description = "Devuelve una lista de publicaciones filtradas por categoría")
        @GetMapping("/category/{categoryId}")
        public List<Publicacion> getByCategory(@RequestParam(value = "categoryId", defaultValue = "1") Integer categoryId) {
            return publicacionService.getByCategory(categoryId);
        }

}
