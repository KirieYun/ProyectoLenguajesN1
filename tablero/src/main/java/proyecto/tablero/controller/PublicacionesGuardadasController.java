package proyecto.tablero.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import proyecto.tablero.entity.Publicacion;
import proyecto.tablero.entity.PublicacionesGuardadas;
import proyecto.tablero.service.PublicacionService;
import proyecto.tablero.service.PublicacionesGuardadasService;
import proyecto.tablero.service.UserService;

@CrossOrigin(origins = "*")
@Tag(name = "PublicacionesGuardadasController", description = "Controlador para gestionar publicaciones guardadas por los usuarios")
@RestController
@RequestMapping("/publicaciones-guardadas")
public class PublicacionesGuardadasController {
    
    @Autowired
    private PublicacionesGuardadasService publicacionGuardadaService;
    @Autowired
    private UserService userService;
    @Autowired
    private PublicacionService publicacionService;

    @GetMapping
    @Operation(summary = "Obtener todas las publicaciones guardadas", description = "Devuelve una lista de todas las publicaciones guardadas por los usuarios")
    public List<Publicacion> getAllPublicacionesGuardadas() {
        return publicacionGuardadaService.getGuardadasByUsuarioOrdenadas(null);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una publicación guardada por ID", description = "Devuelve una publicación guardada específica según su ID")
    public Publicacion getPublicacionGuardadaById(@PathVariable int id) {
        return publicacionGuardadaService.getById(id).getPublicacion();
    }   

    @GetMapping("/{userId}")
    @Operation(summary = "Obtener publicaciones guardadas por usuario", description = "Devuelve una lista de publicaciones guardadas por un usuario específico")
    public List<Publicacion> getPublicacionesGuardadasByUserId(@PathVariable int userId) {
        return publicacionGuardadaService.getGuardadasByUsuarioOrdenadas(userId);
    }

    @PostMapping("/add")
    @Operation(summary = "Agregar una publicación guardada", description = "Agrega una nueva publicación a la lista de publicaciones guardadas por un usuario")
    public PublicacionesGuardadas addPublicacionGuardada(@RequestParam int userId, @RequestParam int publicacionId) {
        PublicacionesGuardadas pg = new PublicacionesGuardadas();
        pg.setUser(userService.getById(userId));
        pg.setPublicacion(publicacionService.getById(publicacionId));
        return pg;
    }

        @DeleteMapping("/delete/{id}")
    @Operation(summary = "Eliminar una publicación guardada", description = "Elimina una publicación guardada específica según su ID")
    public void deletePublicacionGuardada(@PathVariable int id) {
        publicacionGuardadaService.delete(id);
    }

    

}
