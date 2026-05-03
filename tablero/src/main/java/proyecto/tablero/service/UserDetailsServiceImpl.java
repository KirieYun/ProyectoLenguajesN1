package proyecto.tablero.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import proyecto.tablero.entity.User;
import proyecto.tablero.repository.UserRepository;
import proyecto.tablero.repository.AdminUserRepository;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final AdminUserRepository adminUserRepository;

    public UserDetailsServiceImpl(UserRepository userRepository, AdminUserRepository adminUserRepository) {
    this.userRepository = userRepository;
    this.adminUserRepository = adminUserRepository;
    }

    @Override
public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {

    User user = userRepository.findByCorreo(correo)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

    boolean isAdmin = adminUserRepository.existsByUser_Id(user.getId());

    String role = isAdmin ? "ROLE_ADMIN" : "ROLE_USER";

    return new org.springframework.security.core.userdetails.User(
            user.getCorreo(),
            user.getContrasena(),
            List.of(new SimpleGrantedAuthority(role))
    );
}
}