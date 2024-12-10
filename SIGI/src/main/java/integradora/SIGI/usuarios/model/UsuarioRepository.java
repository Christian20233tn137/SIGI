package integradora.SIGI.usuarios.model;

import integradora.SIGI.categoria.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findAllByStatusIsTrue();
    Optional<Usuario> findByEmailIgnoreCase(String email);
    Optional<Usuario> findByEmailIgnoreCaseAndIdNot(String name, Long id);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByStatus(boolean status);
    Optional<Usuario> findTopByOrderByIdDesc();

    Optional<Usuario> findFirstByEmail(String email);
    Optional<Usuario> findFirstByEmailAndCode(String email,String code);

}
