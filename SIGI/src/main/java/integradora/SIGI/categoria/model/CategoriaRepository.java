package integradora.SIGI.categoria.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Buscar categoría por nombre (case insensitive)
    Optional<Categoria> findByNameIgnoreCase(String name);

    // Buscar categoría por nombre excluyendo un ID específico
    Optional<Categoria> findByNameIgnoreCaseAndIdNot(String name, Long id);

    // Listar todas las categorías activas
    List<Categoria> findByStatus(boolean status);
}
