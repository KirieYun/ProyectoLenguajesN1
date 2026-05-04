package proyecto.tablero.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import proyecto.tablero.entity.AdminUser;
import proyecto.tablero.entity.User;
import proyecto.tablero.service.AdminUserService;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@Tag(name = "AdminUserController", description = "Controlador para gestionar usuarios administradores")
@RestController
@RequestMapping("/admin-users")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios administradores", description = "Devuelve una lista de todos los usuarios administradores registrados")
    public List<AdminUser> getAllAdminUsers() {
        return adminUserService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario administrador por ID", description = "Devuelve los detalles de un usuario administrador específico")
    public AdminUser getAdminUserById(@PathVariable int id) {
        return adminUserService.getById(id);
    }

    @PostMapping("/add")
    @Operation(summary = "Agregar usuario administrador", description = "Crea un nuevo usuario administrador")
    public AdminUser addAdminUser(@RequestParam User user) {
        return adminUserService.add(user);
    }

    @Operation(summary = "Actualizar usuario administrador", description = "Actualiza los datos de un usuario administrador existente")
    @PostMapping("/{id}")
    public AdminUser updateAdminUser(@PathVariable int id, @RequestParam User user) {
        return adminUserService.update(id, user);
    }

    @Operation(summary = "Eliminar usuario administrador", description = "Elimina un usuario administrador existente")
    @DeleteMapping("/{id}")
    public void deleteAdminUser(@PathVariable int id) {
        adminUserService.deleteByUserId(id);
    }

    @GetMapping("/ids")
    public List<Integer> getAdmins() {
        return adminUserService.getAll()
                .stream()
                .map(a -> a.getUser().getId())
                .toList();
    }

}
