package proyecto.tablero.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "publicaciones_guardadas")
@Data
public class PublicacionesGuardadas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "fecha_guardado")
    private LocalDateTime fechaGuardado;

    @PrePersist
    public void prePersist() {
        this.fechaGuardado = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "publicacion_id")
    private Publicacion publicacion;
}
