package proyecto.tablero.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JsonIgnore
    private List<NormalUser> normalUsers;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<AdminUser> adminUsers;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Publicacion> publicaciones;


}