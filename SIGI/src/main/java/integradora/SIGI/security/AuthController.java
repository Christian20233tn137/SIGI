package integradora.SIGI.security;

import integradora.SIGI.usuarios.model.Usuario;
import integradora.SIGI.usuarios.model.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(loginRequest.getEmail());

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            // Verifica la contraseña
            if (passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
                // Generar el token incluyendo el id y el status
                String token = jwtUtil.generateToken(
                        usuario.getEmail(),
                        usuario.getRol().name(),
                        usuario.getId(),  // El ID del usuario
                        usuario.isStatus()  // El estado del usuario
                );
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(401).body("Credenciales inválidas: Contraseña incorrecta");
            }
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas: Usuario no encontrado");
        }
    }



}
