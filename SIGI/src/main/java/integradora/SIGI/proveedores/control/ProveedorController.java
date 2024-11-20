package integradora.SIGI.proveedores.control;

import integradora.SIGI.proveedores.model.ProveedorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proveedor")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT})
public class ProveedorController {

    private final ProveedorService proveedorService;

    @Autowired
    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    // Obtener todos los proveedores
    @GetMapping
    public ResponseEntity<Object> getAllProveedores() {
        return proveedorService.obtenerProveedores();
    }

    // Registrar proveedor
    @PostMapping
    public ResponseEntity<Object> saveProveedor(@Validated(ProveedorDTO.Register.class) @RequestBody ProveedorDTO dto) {
        return proveedorService.registrarProveedor(dto);
    }

    // Actualizar proveedor
    @PutMapping
    public ResponseEntity<Object> updateProveedor(@Validated(ProveedorDTO.Modify.class) @RequestBody ProveedorDTO dto) {
        return proveedorService.actualizarProveedor(dto);
    }

    // Obtener proveedores activos
    @GetMapping("/activos")
    public ResponseEntity<Object> getActiveProveedores() {
        return proveedorService.obtenerProveedoresActivos();
    }

    // Cambiar estado de proveedor (habilitar/deshabilitar)
    @PutMapping("/cambiar-estado")
    public ResponseEntity<Object> changeStatusProveedor(@RequestBody ProveedorDTO dto) {
        return proveedorService.cambiarEstadoProveedor(dto);
    }
}
