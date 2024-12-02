package integradora.SIGI.utils;

import integradora.SIGI.Role.model.Role;
import integradora.SIGI.Role.model.RoleRepository;
import integradora.SIGI.usuarios.model.Usuario;
import integradora.SIGI.usuarios.model.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {


            Optional<Role> optionalRole = roleRepository.findByName("ROLE_TOWN_ACCESS");
            if (!optionalRole.isPresent()) {
                Role roleTown = new Role("ROLE_TOWN_ACCESS");
                roleRepository.saveAndFlush(roleTown);

                Optional<Usuario> optionalUser = usuarioRepository.findByEmail("townUser");
                if (!optionalUser.isPresent()) {
                    Usuario userTown = new Usuario("townUser", passwordEncoder.encode("password123"));
                    userTown.getRoles().add(roleTown);
                    usuarioRepository.saveAndFlush(userTown);
                }
            }

            optionalRole = roleRepository.findByName("ROLE_STATE_ACCESS");
            if (!optionalRole.isPresent()) {
                Role roleState = new Role("ROLE_STATE_ACCESS");
                roleRepository.saveAndFlush(roleState);

                Optional<Usuario> optionalUser = usuarioRepository.findByEmail("stateUser");
                if (!optionalUser.isPresent()) {
                    Usuario userState = new Usuario("stateUser", passwordEncoder.encode("password123"));
                    userState.getRoles().add(roleState);
                    usuarioRepository.saveAndFlush(userState);
                }
            }

        };
    }
}
