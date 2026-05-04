package proyecto.tablero.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import proyecto.tablero.entity.AdminUser;
import proyecto.tablero.entity.User;
import proyecto.tablero.repository.AdminUserRepository;

@Service
@AllArgsConstructor
public class AdminUserService {

    private AdminUserRepository adminUserRepository;

    public AdminUser add(User user) {
        AdminUser adminUser = new AdminUser();
        adminUser.setUser(user);
        return adminUserRepository.save(adminUser);
    }

    public List<AdminUser> getAll() {
        return adminUserRepository.findAll();
    }

    public AdminUser getById(int id) {
        return adminUserRepository.findById(id).orElse(null);
    }

    public AdminUser update(int id, User user) {
        AdminUser existingAdminUser = adminUserRepository.findById(id).orElse(null);
        if (existingAdminUser == null) {
            return null;
        }
        existingAdminUser.setUser(user);
        return adminUserRepository.save(existingAdminUser);
    }

    public void deleteByUserId(int userId) {
        AdminUser admin = adminUserRepository.findAll()
                .stream()
                .filter(a -> a.getUser().getId() == userId)
                .findFirst()
                .orElse(null);

        if (admin != null) {
            adminUserRepository.delete(admin);
        }
    }
}
