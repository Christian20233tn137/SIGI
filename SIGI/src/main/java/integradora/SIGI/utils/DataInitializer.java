package integradora.SIGI.utils;

<<<<<<< HEAD
import integradora.SIGI.Role.model.Role;
import integradora.SIGI.Role.model.RoleRepository;
import integradora.SIGI.usuarios.model.Usuario;
import integradora.SIGI.usuarios.model.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
=======
import integradora.SIGI.categoria.model.Categoria;
import integradora.SIGI.categoria.model.CategoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
>>>>>>> chris

@Configuration
public class DataInitializer {

<<<<<<< HEAD
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

=======
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner initDatabase(CategoriaRepository categoriaRepository) {
        return args -> {
            // Crear y guardar categorías iniciales si no existen
            if (categoriaRepository.findByNameIgnoreCase("Electrónica").isEmpty()) {
                Categoria categoriaElectronica = new Categoria("Electrónica", "Categoría de productos electrónicos", true);
                categoriaRepository.save(categoriaElectronica);
                logger.info("Categoría 'Electrónica' creada");
            }

            if (categoriaRepository.findByNameIgnoreCase("Ropa").isEmpty()) {
                Categoria categoriaRopa = new Categoria("Ropa", "Categoría de prendas de vestir", true);
                categoriaRepository.save(categoriaRopa);
                logger.info("Categoría 'Ropa' creada");
            }

            if (categoriaRepository.findByNameIgnoreCase("Hogar").isEmpty()) {
                Categoria categoriaHogar = new Categoria("Hogar", "Categoría de artículos para el hogar", true);
                categoriaRepository.save(categoriaHogar);
                logger.info("Categoría 'Hogar' creada");
            }
>>>>>>> chris
        };
    }
}
