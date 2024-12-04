package integradora.SIGI.proveedores.control;

import integradora.SIGI.proveedores.model.Proveedor;
import integradora.SIGI.proveedores.model.ProveedorDTO;
import integradora.SIGI.proveedores.model.ProveedorRepository;
import integradora.SIGI.utils.Message;
import integradora.SIGI.utils.TypesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProveedorService {

    private static final Logger logger = LoggerFactory.getLogger(ProveedorService.class);

    private final ProveedorRepository repository;

    @Autowired
    public ProveedorService(ProveedorRepository repository) {
        this.repository = repository;
    }

    // Registrar proveedor
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> registrarProveedor(ProveedorDTO dto) {
        logger.info("Iniciando registro de proveedor con RFC: {}", dto.getRfc());

        dto.setRfc(dto.getRfc().toUpperCase().trim());
        dto.setEmail(dto.getEmail().toLowerCase().trim());

        if (repository.findByRfcIgnoreCase(dto.getRfc()).isPresent()) {
            logger.warn("El RFC del proveedor ya existe: {}", dto.getRfc());
            return new ResponseEntity<>(new Message("El RFC del proveedor ya existe", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Proveedor proveedor = new Proveedor(
                dto.getName(),
                dto.getRfc(),
                dto.getDireccion(),
                dto.getTelefono(),
                dto.getEmail(),
                true
        );
        repository.save(proveedor);
        logger.info("Proveedor registrado exitosamente: {}", proveedor.getRfc());
        return new ResponseEntity<>(new Message(proveedor, "Proveedor registrado exitosamente", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    // Consultar todos los proveedores
    @Transactional(readOnly = true)
    public ResponseEntity<Object> obtenerProveedores() {
        logger.info("Consultando todos los proveedores.");
        List<Proveedor> proveedores = repository.findAll();
        logger.info("Número de proveedores encontrados: {}", proveedores.size());
        return new ResponseEntity<>(new Message(proveedores, "Listado de proveedores", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Consultar proveedores activos
    @Transactional(readOnly = true)
    public ResponseEntity<Object> obtenerProveedoresActivos() {
        logger.info("Consultando proveedores activos.");
        List<Proveedor> proveedoresActivos = repository.findByStatus(true);
        logger.info("Número de proveedores activos encontrados: {}", proveedoresActivos.size());
        return new ResponseEntity<>(new Message(proveedoresActivos, "Listado de proveedores activos", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Actualizar proveedor
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> actualizarProveedor(ProveedorDTO dto) {
        logger.info("Iniciando actualización de proveedor con ID: {}", dto.getId());

        dto.setRfc(dto.getRfc().toUpperCase().trim());
        dto.setEmail(dto.getEmail().toLowerCase().trim());

        Optional<Proveedor> proveedorOpt = repository.findById(dto.getId());
        if (!proveedorOpt.isPresent()) {
            logger.warn("Proveedor no encontrado con ID: {}", dto.getId());
            return new ResponseEntity<>(new Message("Proveedor no encontrado", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        if (repository.findByRfcIgnoreCaseAndIdNot(dto.getRfc(), dto.getId()).isPresent()) {
            logger.warn("El RFC del proveedor ya existe para otro ID: {}", dto.getRfc());
            return new ResponseEntity<>(new Message("El RFC del proveedor ya existe", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Proveedor proveedor = proveedorOpt.get();
        proveedor.setName(dto.getName());
        proveedor.setRfc(dto.getRfc());
        proveedor.setDireccion(dto.getDireccion());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setEmail(dto.getEmail());
        repository.save(proveedor);
        logger.info("Proveedor actualizado exitosamente: {}", proveedor.getRfc());
        return new ResponseEntity<>(new Message(proveedor, "Proveedor actualizado exitosamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Cambiar estado del proveedor (habilitar/deshabilitar)
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> cambiarEstadoProveedor(ProveedorDTO dto) {
        logger.info("Iniciando cambio de estado para proveedor con ID: {}", dto.getId());

        Optional<Proveedor> proveedorOpt = repository.findById(dto.getId());
        if (!proveedorOpt.isPresent()) {
            logger.warn("Proveedor no encontrado con ID: {}", dto.getId());
            return new ResponseEntity<>(new Message("Proveedor no encontrado", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        Proveedor proveedor = proveedorOpt.get();
        proveedor.setStatus(!proveedor.isStatus());
        repository.save(proveedor);

        String estado = proveedor.isStatus() ? "habilitado" : "deshabilitado";
        logger.info("Proveedor {} exitosamente: {}", estado, proveedor.getRfc());
        return new ResponseEntity<>(new Message(proveedor, "Proveedor " + estado + " exitosamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }
}
