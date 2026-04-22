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
import proyecto.tablero.entity.NormalUser;
import proyecto.tablero.entity.User;
import proyecto.tablero.service.NormalUserService;

@CrossOrigin(origins="*")
@Tag(name = "NormalUserController", description = "Controlador para gestionar usuarios normales")
@RestController
@RequestMapping("/normal-users")
public class NormalUserController {

    @Autowired
    private NormalUserService normalUserService;

    
    @GetMapping
    @Operation(summary = "Obtener todos los usuarios normales", description = "Devuelve una lista de todos los usuarios normales registrados")
    public List<NormalUser> getAllNormalUsers() {
        return normalUserService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario normal por ID", description = "Devuelve los detalles de un usuario normal específico")
    public NormalUser getNormalUserById(@PathVariable int id) {
        return normalUserService.getById(id);
    }
    

     @PostMapping("/add")
     @Operation(summary = "Agregar usuario normal", description = "Crea un nuevo usuario normal")
     public NormalUser addNormalUser(@RequestParam User user) {
         return normalUserService.add(user);
     }

     @Operation(summary = "Actualizar usuario normal", description = "Actualiza los datos de un usuario normal existente")
     @PostMapping("/{id}")
        public NormalUser updateNormalUser(@PathVariable int id, @RequestParam User user) {
            return normalUserService.update(id, user);
        }

        @Operation(summary = "Eliminar usuario normal", description = "Elimina un usuario normal existente")
        @DeleteMapping("/{id}")
        public void deleteNormalUser(@PathVariable int id) {
            normalUserService.delete(id);
        }


    
}
