package proyecto.tablero.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import proyecto.tablero.entity.Publicacion;
import proyecto.tablero.entity.PublicacionesGuardadas;
import proyecto.tablero.entity.User;
import proyecto.tablero.repository.PublicacionesGuardadasRepository;

@Service
@AllArgsConstructor
public class PublicacionesGuardadasService {

    private PublicacionesGuardadasRepository publicacionesGuardadasRepository;
    
    public PublicacionesGuardadas add(PublicacionesGuardadas publicacionesGuardadas) {
        return publicacionesGuardadasRepository.save(publicacionesGuardadas);
    }

    public List<PublicacionesGuardadas> getAll() {
        return publicacionesGuardadasRepository.findAll();
    }

    public PublicacionesGuardadas getById(int id) {
        return publicacionesGuardadasRepository.findById(id).orElse(null);
    }

     public PublicacionesGuardadas update(User user, Publicacion publicacion, int id) {
        PublicacionesGuardadas existingPublicacionesGuardadas = publicacionesGuardadasRepository.findById(id).orElse(null);
        if (existingPublicacionesGuardadas == null) {
            return null;
        }
        existingPublicacionesGuardadas.setPublicacion(null);
        existingPublicacionesGuardadas.setUser(null);

        return publicacionesGuardadasRepository.save(existingPublicacionesGuardadas);
    }

    
     public void delete(int id) {
         publicacionesGuardadasRepository.deleteById(id);
     }




}
