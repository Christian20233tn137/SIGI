package integradora.SIGI.proveedores.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    // Buscar proveedor por RFC (ignorar mayúsculas/minúsculas)
    Optional<Proveedor> findByRfcIgnoreCase(String rfc);

    // Buscar proveedor por RFC, excluyendo un ID específico
    Optional<Proveedor> findByRfcIgnoreCaseAndIdNot(String rfc, Long id);

    // Obtener proveedores por estado (activos/inactivos)
    List<Proveedor> findByStatus(boolean status);
}
