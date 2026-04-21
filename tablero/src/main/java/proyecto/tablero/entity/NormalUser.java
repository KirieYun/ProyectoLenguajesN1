package proyecto.tablero.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "normal_users")
@Data
public class NormalUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
