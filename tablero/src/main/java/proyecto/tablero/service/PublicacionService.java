package proyecto.tablero.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import proyecto.tablero.entity.Publicacion;
import proyecto.tablero.repository.PublicacionRepository;

@Service
@AllArgsConstructor
public class PublicacionService {

    private final CategoryService categoryService;
    private PublicacionRepository publicacionRepository;

    PublicacionService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Publicacion add(Publicacion publicacion) {
        return publicacionRepository.save(publicacion);
    }

    public Publicacion addPublicacion(String titulo, String contenido, int userId, MultipartFile file) {

        try {
            Publicacion publicacion = new Publicacion();
            publicacion.setTitulo(titulo);
            publicacion.setContenido(contenido);

            if (file == null || file.isEmpty()) {
                publicacion.setImgUrl(null);
                return publicacionRepository.save(publicacion);
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

            publicacion.setImgUrl("/uploads/" + filename);

            return publicacionRepository.save(publicacion);

        } catch (Exception e) {
            throw new RuntimeException("Error al manejar el archivo: " + e.getMessage());
        }
    }

    public List<Publicacion> getAll() {
        return publicacionRepository.findAll();
    }

    public Publicacion getById(int id) {
        return publicacionRepository.findById(id).orElse(null);
    }

    public Publicacion updatePubliPicture(int id, MultipartFile file) {
        try {

            Publicacion publicacion = publicacionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

            if (publicacion.getImgUrl() != null) {
                File oldFile = new File(System.getProperty("user.dir"), publicacion.getImgUrl());
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }

            if (file == null || file.isEmpty()) {
                publicacion.setImgUrl(null);
                return publicacionRepository.save(publicacion);
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

            publicacion.setImgUrl("/uploads/" + filename);

            return publicacionRepository.save(publicacion);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar imagen: " + e.getMessage());
        }
    }

    public Publicacion update(int id, String titulo, String contenido, int category_id, MultipartFile file) {
        Publicacion existingPublicacion = publicacionRepository.findById(id).orElse(null);
        if (existingPublicacion != null) {
            existingPublicacion.setTitulo(titulo);
            existingPublicacion.setContenido(contenido);
            existingPublicacion.setCategory(categoryService.getById(category_id));
                if (file != null && !file.isEmpty()) {
                    updatePubliPicture(id, file);
                }
            return publicacionRepository.save(existingPublicacion);
        }
        return null;
    }

    public void delete(int id) {

        try {
            Publicacion publi = publicacionRepository.findById(id).orElse(null);
            File fileToDelete = new File(System.getProperty("user.dir"), publi.getImgUrl());
            if (fileToDelete.exists()) {
                fileToDelete.delete();
            }
            publicacionRepository.deleteById(id);
        } catch (Exception e) {
            System.err.println("Error al eliminar archivo: " + e.getMessage());
        }
    }
    public List<Publicacion> getByCategory(Integer categoryId) {
        return publicacionRepository.findByCategoryId(categoryId);
    }

}
