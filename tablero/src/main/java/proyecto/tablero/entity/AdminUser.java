package proyecto.tablero.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "admin_users")
@Data
public class AdminUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
