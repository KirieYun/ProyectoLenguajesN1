package proyecto.tablero.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import proyecto.tablero.entity.NormalUser;
import proyecto.tablero.entity.User;
import proyecto.tablero.repository.NormalUserRepository;

@Service
@AllArgsConstructor
public class NormalUserService {

    private final NormalUserRepository normalUserRepository;

    public NormalUser add(User user) {
        NormalUser normalUser = new NormalUser();
        normalUser.setUser(user);
        return normalUserRepository.save(normalUser);

    }

    public List<NormalUser> getAll() {
        return normalUserRepository.findAll();
    }

    public NormalUser getById(int id) {
        return normalUserRepository.findById(id).orElse(null);
    }

    public NormalUser update(int id, User user) {
        NormalUser existingNormalUser = normalUserRepository.findById(id).orElse(null);
        if (existingNormalUser == null) {
            return null;
        }
        existingNormalUser.setUser(user);
        return normalUserRepository.save(existingNormalUser);
    }

    public void deleteByUserId(int userId) {
        NormalUser normal = normalUserRepository.findAll()
                .stream()
                .filter(n -> n.getUser().getId() == userId)
                .findFirst()
                .orElse(null);

        if (normal != null) {
            normalUserRepository.delete(normal);
        }
    }

}
