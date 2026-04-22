package proyecto.tablero.service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import proyecto.tablero.entity.User;
import proyecto.tablero.repository.UserRepository;

@Service
@AllArgsConstructor

public class UserService {

    private UserRepository userRepository;

    public User add(User user) {
        return userRepository.save(user);
    }

    public User addUser(String correo, String contraseña, String Nombre, MultipartFile file) {

        try {

            User user = new User();
            user.setCorreo(correo);
            user.setContrasena(contraseña);
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

    /* ⁡⁣⁢⁣Borrar usuario uno a la imagen de perfil⁡ */
    public void delete(int id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            try {
                File fileToDelete = new File(System.getProperty("user.dir"), user.getImgUrl());
                if (fileToDelete.exists()) {
                    fileToDelete.delete();
                }
            } catch (Exception e) {
                System.err.println("Error al eliminar archivo: " + e.getMessage());
            }
        }
        userRepository.deleteById(id);
    }

    /* ⁡⁣⁢⁣cambiar foto de perfil⁡ */
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
            existingUser.setContrasena(contraseña);
            existingUser.setNombre(nombre);

            return userRepository.save(existingUser);
        }
        return null;
    }
}
