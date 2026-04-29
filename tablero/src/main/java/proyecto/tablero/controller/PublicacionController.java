package proyecto.tablero.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import proyecto.tablero.entity.Publicacion;
import proyecto.tablero.service.PublicacionService;

@CrossOrigin(origins = "*")
@Tag(name = "PublicacionController", description = "Controlador para gestionar publicaciones")
@RestController
@RequestMapping("/publicaciones")
public class PublicacionController {
    
@Autowired
private PublicacionService publicacionService;

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
     public Publicacion addPublicacion(@RequestParam String titulo, @RequestParam String contenido, @RequestParam Integer category_id,@RequestParam String Nombre,@RequestParam(required = false) MultipartFile file) {
         return publicacionService.addPublicacion(titulo, contenido, category_id, file);
     }

        @Operation(summary = "Actualizar una publicacion", description = "Actualiza los datos de una publicacion existente")
        @PostMapping("/{id}")
        public Publicacion update(@PathVariable int id, @RequestParam String titulo, @RequestParam String contenido, @RequestParam Integer category_id,@RequestParam String Nombre,@RequestParam(required = false) MultipartFile file) {
            return publicacionService.update(id, titulo, contenido, category_id, file);
        }

        @Operation(summary = "Eliminar una publicacion", description = "Elimina una publicacion existente")
        @DeleteMapping("/{id}")
        public void delete(@PathVariable int id) {
            publicacionService.delete(id);
        }

}
