package proyecto.tablero.controller;

import java.util.List;
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

@Controller
@AllArgsConstructor
public class ViewController {

    private final PublicacionService publicacionService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String inicio(@RequestParam(value = "categoriaId", required = false) Integer categoriaId, Model model) {
        List<Publicacion> noticias;

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

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    private User getUsuarioMock() {
        User usuarioMock = new User();
        usuarioMock.setNombre("Usuario Invitado");
        usuarioMock.setCorreo("invitado@siquiconnect.com");
        return usuarioMock;
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
            @RequestParam("categoriaId") Integer categoriaId,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        Publicacion nueva = publicacionService.addPublicacion(
                titulo,
                contenido,
                1,
                file,
                getUsuarioMock());

        if (categoriaId != null && categoriaId != 0) {
            Category cat = categoryService.getById(categoriaId);
            if (cat != null) {
                nueva.setCategory(cat);
                publicacionService.add(nueva);
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

    @GetMapping("/usuarios")
    public String verUsuarios(Model model) {
        model.addAttribute("usuario", getUsuarioMock());
        return "usuarios";
    }

    @GetMapping("/categorias")
    public String verCategorias(Model model) {
        model.addAttribute("usuario", getUsuarioMock());
        return "categorias";
    }
}