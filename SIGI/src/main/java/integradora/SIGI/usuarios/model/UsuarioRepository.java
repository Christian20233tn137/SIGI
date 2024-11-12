package integradora.SIGI.usuarios.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findAllByStatusIsTrue();
    boolean existsByPlaca(String placa);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByStatus(boolean status);
    Optional<Usuario> findByAno(int ano);
    Optional<Usuario> findTopByOrderByIdDesc();
}
