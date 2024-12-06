package integradora.SIGI.usuarios.control;

import integradora.SIGI.producto.control.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultor")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class ConsultorController {

    private final ProductoService productoService;

    @Autowired
    public ConsultorController(ProductoService productoService) {
        this.productoService = productoService;
    }
    // Consultar productos
    @GetMapping("/all/Productos")
    public ResponseEntity<Object> obtenerProductos() {
        return productoService.obtenerProductos();
    }

    // Consultar productos activos
    @GetMapping("/activos/Productos")
    public ResponseEntity<Object> obtenerProductosActivos() {
        return productoService.obtenerProductosActivos();
    }

}
