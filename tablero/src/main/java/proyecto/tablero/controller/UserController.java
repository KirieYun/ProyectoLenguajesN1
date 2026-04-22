package proyecto.tablero.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import proyecto.tablero.entity.User;
import proyecto.tablero.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;



@CrossOrigin(origins = "*")
@Tag(name = "UserController", description = "Controlador para gestionar usuarios")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve una lista de todos los usuarios registrados")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

     @GetMapping("/{id}")
     @Operation(summary = "Obtener un usuario por ID", description = "Devuelve un usuario específico según su ID")
     public User getUserById(@PathVariable int id) {
         return userService.getById(id);
     }

     @PostMapping("/add")
     @Operation(summary = "Agregar un nuevo usuario", description = "Crea un nuevo usuario")
     public User addUser(@RequestParam String correo,@RequestParam String contraseña,@RequestParam String Nombre,@RequestParam(required = false) MultipartFile file) {
         return userService.addUser(correo, contraseña, Nombre, file);
     }

     @Operation(summary = "Actualizar un usuario", description = "Actualiza los datos de un usuario existente")
     @PostMapping("/{id}")
        public User update(@PathVariable int id, @RequestParam String correo,@RequestParam String contraseña,@RequestParam String Nombre,@RequestParam(required = false) MultipartFile file) {
            return userService.update(id, correo, contraseña, Nombre, file);
        }

        @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario existente")
        @DeleteMapping("/{id}")
        public void delete(@PathVariable int id) {
            userService.delete(id);
        }

        @Operation(summary = "Cambiar Foto de Perfil", description = "Actualiza la foto de perfil de un usuario existente")
        @PostMapping("/update-profile-picture/{id}")
        public User updateProfilePicture(@PathVariable int id, @RequestParam(required = false) MultipartFile file) {
            return userService.updateProfilePicture(id, file);
        }

}
