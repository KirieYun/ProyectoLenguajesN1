package proyecto.tablero.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @Lob
    @Column(name = "contenido", columnDefinition = "TEXT")
    private String contenido;
    @Column(name = "imgUrl", nullable = true)
    private String imgUrl;
    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion;
    @PrePersist
    public void prePersist() {
        this.fechaPublicacion = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "publicacion")
    private List<PublicacionesGuardadas> publicacionesGuardadas;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
}
