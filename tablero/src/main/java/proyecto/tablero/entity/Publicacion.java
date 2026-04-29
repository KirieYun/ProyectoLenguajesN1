package proyecto.tablero.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "publicaciones")
@Data
public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "contenido")
    private String contenido;
    @Column(name = "imgUrl", nullable = true)
    private String imgUrl;
    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaCreacion;
    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "publicacion")
    private java.util.List<PublicacionesGuardadas> publicacionesGuardadas;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
