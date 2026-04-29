package proyecto.tablero.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import proyecto.tablero.service.PublicacionService;
import proyecto.tablero.service.CategoryService;
import proyecto.tablero.entity.Publicacion;
import proyecto.tablero.entity.User;
import proyecto.tablero.entity.Category;
import java.util.List;

@Controller
@AllArgsConstructor
public class ViewController {

    private final PublicacionService publicacionService;
    private final CategoryService categoryService;

    private User getUsuarioMock() {
        User usuarioMock = new User();
        usuarioMock.setNombre("Usuario Invitado");
        usuarioMock.setCorreo("invitado@siquiconnect.com");
        return usuarioMock;
    }

    @GetMapping("/")
    public String inicio(@RequestParam(value = "categoriaId", required = false) Integer categoriaId, Model model) {
        List<Publicacion> noticias;
        // Filtro por categoría funcional gracias a tu Repository/Service
        if (categoriaId != null && categoriaId != 0) {
            noticias = publicacionService.getByCategory(categoriaId);
        } else {
            noticias = publicacionService.getAll();
        }

        model.addAttribute("listaNoticias", noticias);
        model.addAttribute("categorias", categoryService.getAll());
        model.addAttribute("usuario", getUsuarioMock());
        return "noticias";
    }

    @GetMapping("/noticias/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("categorias", categoryService.getAll());
        model.addAttribute("usuario", getUsuarioMock());
        return "publicacion";
    }

    @PostMapping("/noticias/guardar")
    public String guardarNoticia(
            @RequestParam("titulo") String titulo,
            @RequestParam("contenido") String contenido,
            @RequestParam("categoriaId") Integer categoriaId, // ID proveniente del <select>
            @RequestParam(value = "file", required = false) MultipartFile file) {
        
        // 1. Creamos la publicación (maneja la imagen internamente)
        Publicacion nueva = publicacionService.addPublicacion(titulo, contenido, 1, file);
        
        // 2. Usamos tu método getById de CategoryService para vincular la categoría
        if (categoriaId != null && categoriaId != 0) {
            Category cat = categoryService.getById(categoriaId);
            if (cat != null) {
                nueva.setCategory(cat);
                publicacionService.add(nueva); // Guardamos la relación
            }
        }
        
        return "redirect:/";
    }

    @GetMapping("/perfil")
    public String verPerfil(Model model) {
        model.addAttribute("listaNoticias", publicacionService.getAll());
        model.addAttribute("categorias", categoryService.getAll());
        model.addAttribute("usuario", getUsuarioMock());
        return "perfil";
    }

    @GetMapping("/publicaciones/eliminar/{id}")
    public String eliminarNoticia(@PathVariable int id) {
        publicacionService.delete(id);
        return "redirect:/perfil";
    }

    @GetMapping("/publicaciones/editar/{id}")
    public String editarNoticia(@PathVariable int id, Model model) {
        Publicacion pub = publicacionService.getById(id);
        model.addAttribute("publicacion", pub);
        model.addAttribute("categorias", categoryService.getAll());
        model.addAttribute("usuario", getUsuarioMock());
        return "publicacion";
    }
}