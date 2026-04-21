package proyecto.tablero.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "correo")
    private String correo;
    @Column(name = "contrasena")
    private String contrasena;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "imgUrl", nullable = true)
    private String imgUrl;

    @OneToMany(mappedBy = "user")
    private List<NormalUser> normalUsers;
    @OneToMany(mappedBy = "user")
    private List<AdminUser> adminUsers;

    @OneToMany(mappedBy = "user")
    private List<Publicacion> publicaciones;


}