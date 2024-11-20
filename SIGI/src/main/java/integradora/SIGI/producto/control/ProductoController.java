package integradora.SIGI.producto.control;

import integradora.SIGI.producto.model.ProductoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producto")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Registrar producto
    @PostMapping
    public ResponseEntity<Object> registrarProducto(@Validated(ProductoDTO.Register.class) @RequestBody ProductoDTO dto) {
        return productoService.registrarProducto(dto);
    }

    // Consultar productos
    @GetMapping
    public ResponseEntity<Object> obtenerProductos() {
        return productoService.obtenerProductos();
    }

    // Consultar productos activos
    @GetMapping("/activos")
    public ResponseEntity<Object> obtenerProductosActivos() {
        return productoService.obtenerProductosActivos();
    }

    // Actualizar producto
    @PutMapping
    public ResponseEntity<Object> actualizarProducto(@Validated(ProductoDTO.Modify.class) @RequestBody ProductoDTO dto) {
        return productoService.actualizarProducto(dto);
    }

    // Cambiar estado de producto
    @PutMapping("/estado")
    public ResponseEntity<Object> cambiarEstadoProducto(@Validated(ProductoDTO.ChangeStatus.class) @RequestBody ProductoDTO dto) {
        return productoService.cambiarEstadoProducto(dto);
    }

    // Agregar cantidad a producto
    @PutMapping("/agregar-cantidad")
    public ResponseEntity<Object> agregarCantidad(@Validated(ProductoDTO.UpdateStock.class) @RequestBody ProductoDTO dto) {
        return productoService.agregarCantidad(dto);
    }

    // Disminuir cantidad de producto
    @PutMapping("/disminuir-cantidad")
    public ResponseEntity<Object> disminuirCantidad(@Validated(ProductoDTO.UpdateStock.class) @RequestBody ProductoDTO dto) {
        return productoService.disminuirCantidad(dto);
    }
}
