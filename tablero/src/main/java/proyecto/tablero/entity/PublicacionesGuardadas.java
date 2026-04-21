package proyecto.tablero.entity;

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
    @ManyToOne
    @JoinColumn(name = "publicacion_id")
    private Publicacion publicacion;
}
