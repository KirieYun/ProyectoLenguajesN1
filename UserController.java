package soda.api.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @GetMapping("/all")
    public String publicData() {
        return "Ruta pública";
    }

    @GetMapping("/user/data")
    public String userData() {
        return "Datos para USER o ADMIN";
    }

    @GetMapping("/admin/data")
    public String adminData() {
        return "Datos solo para ADMIN";
    }
}