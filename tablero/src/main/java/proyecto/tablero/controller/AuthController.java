package proyecto.tablero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import proyecto.tablero.security.JwtUtil;
import proyecto.tablero.service.UserService;
import proyecto.tablero.dto.AuthResponse;
import proyecto.tablero.dto.LoginRequest;
import proyecto.tablero.entity.User;
import proyecto.tablero.repository.AdminUserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminUserRepository adminUserRepository;

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getCorreo(),
                            request.getPassword()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getCorreo());
            String token = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new AuthResponse(token));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales inválidas");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        user.setContrasena(passwordEncoder.encode(user.getContrasena()));
        return ResponseEntity.ok(userService.add(user));
    }

    @GetMapping("/me")
    public ResponseEntity<?> obtenerUsuarioActual(Authentication authentication) {
        String correo = authentication.getName();
        User usuario = userService.obtenerPorCorreo(correo);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/is-admin")
    public ResponseEntity<Boolean> checkIfAdmin(Authentication authentication) {
        try {
            String correo = authentication.getName();
            User user = userService.obtenerPorCorreo(correo);

            boolean isAdmin = adminUserRepository.existsByUserId(user.getId());

            return ResponseEntity.ok(isAdmin);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
}