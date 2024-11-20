package integradora.SIGI.producto.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Buscar productos activos
    List<Producto> findByStatus(boolean status);

    // Buscar por nombre ignorando mayúsculas
    boolean existsByNombreIgnoreCase(String nombre);

    // Validar nombre único excluyendo un id específico
    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);
}
