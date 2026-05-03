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

    @GetMapping("/")
    public String home() {
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


}