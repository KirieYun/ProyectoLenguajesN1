package proyecto.tablero.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import proyecto.tablero.entity.User;
import proyecto.tablero.repository.PublicacionRepository;
import proyecto.tablero.repository.UserRepository;

@Service
@AllArgsConstructor

public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final AdminUserService adminUserService;
    private final NormalUserService normalUserService;
    private final PublicacionRepository publicacionRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User add(User user) {
        return userRepository.save(user);
    }

    public User addUser(String correo, String contraseña, String Nombre, MultipartFile file) {

        try {

            User user = new User();
            user.setCorreo(correo);
            user.setContrasena(passwordEncoder.encode(contraseña));
            user.setNombre(Nombre);

            if (file == null || file.isEmpty()) {
                user.setImgUrl(null);
                return userRepository.save(user);
            }

            

            String uploadDir = System.getProperty("user.dir") + "/uploads";
            File folder = new File(uploadDir);
            if (!folder.exists()) {
                folder.mkdirs();
                System.out.println("Carpeta creada en: " + folder.getAbsolutePath());
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new RuntimeException("Solo se permiten imágenes");
            }

            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            File dest = new File(folder, filename);
            file.transferTo(dest);

            System.out.println("Archivo guardado en: " + dest.getAbsolutePath());

            user.setImgUrl("/uploads/" + filename);

            return userRepository.save(user);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear usuario: " + e.getMessage());
        }
    }

    public List<User> getAll() {
        return userRepository.findAll();

    }

    public User getById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public void delete(int id) {
    try {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) return;

        if (publicacionRepository != null) {
            publicacionRepository.deleteByUserId(id);
        }

        if (user.getImgUrl() != null) {
            String path = System.getProperty("user.dir") + user.getImgUrl();
            File file = new File(path);
            if (file.exists()) file.delete();
        }

        adminUserService.deleteByUserId(id);
        userRepository.deleteById(id);

    } catch (Exception e) {
        System.err.println("Error al eliminar usuario: " + e.getMessage());
    }
}

    public User updateProfilePicture(int id, MultipartFile file) {
        try {

            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (user.getImgUrl() != null) {
                File oldFile = new File(System.getProperty("user.dir"), user.getImgUrl());
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }

            if (file == null || file.isEmpty()) {
                user.setImgUrl(null);
                return userRepository.save(user);
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new RuntimeException("Solo se permiten imágenes");
            }

            String uploadDir = System.getProperty("user.dir") + "/uploads";
            File folder = new File(uploadDir);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            File dest = new File(folder, filename);
            file.transferTo(dest);

            user.setImgUrl("/uploads/" + filename);

            return userRepository.save(user);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar imagen: " + e.getMessage());
        }
    }

    public User update(int id, String correo, String contraseña, String nombre, MultipartFile file) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setCorreo(correo);
            existingUser.setContrasena(passwordEncoder.encode(contraseña));
            existingUser.setNombre(nombre);
            updateProfilePicture(id, file);

            return userRepository.save(existingUser);
        }
        return null;
    }
    public User obtenerPorCorreo(String correo) {
    return userRepository.findByCorreo(correo)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el correo: " + correo));
    }
}
