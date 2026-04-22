package proyecto.tablero.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import proyecto.tablero.entity.NormalUser;

@Service
@AllArgsConstructor
public class NormalUserService {
    
    private NormalUserService normalUserService;

    public NormalUser add(NormalUser normalUser) {
        return normalUserService.add(normalUser);
    }

    public List<NormalUser> getAll() {
        return normalUserService.getAll();
    }

    public NormalUser getById(int id) {
        return normalUserService.getById(id);
     }

     public NormalUser update(int id, String correo, String contraseña, String nombre) {
         return normalUserService.update(id, correo, contraseña, nombre);
     }

     public void delete(int id) {
         normalUserService.delete(id);
     }

}
