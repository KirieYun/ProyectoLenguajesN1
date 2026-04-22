package proyecto.tablero.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import proyecto.tablero.entity.AdminUser;

@Service
@AllArgsConstructor
public class AdminUserService {
    
    private AdminUserService adminUserService;

        public AdminUser add(AdminUser adminUser) {
            return adminUserService.add(adminUser);
        }
    
        public List<AdminUser> getAll() {
            return adminUserService.getAll();
        }
    
        public AdminUser getById(int id) {
            return adminUserService.getById(id);
        }
    
        public AdminUser update(int id, String correo, String contraseña, String nombre) {
            return adminUserService.update(id, correo, contraseña, nombre);
        }
    
        public void delete(int id) {
            adminUserService.delete(id);
        }




}
