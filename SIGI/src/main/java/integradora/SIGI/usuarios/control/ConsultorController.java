package integradora.SIGI.usuarios.control;

import integradora.SIGI.categoria.control.CategoriaService;
import integradora.SIGI.producto.control.ProductoService;
import integradora.SIGI.proveedores.control.ProveedorService;
import integradora.SIGI.proveedores.model.ProveedorDTO;
import integradora.SIGI.usuarios.model.Usuario;
import integradora.SIGI.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/consultor")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class ConsultorController {

    private final UsuarioService usuarioService;
    private final ProveedorService proveedorService;
    private final ProductoService productoService;
    private CategoriaService categoriaService;



    @Autowired
    public ConsultorController(UsuarioService usuarioService, ProveedorService proveedorService, ProductoService productoService, CategoriaService categoriaService) {
        this.usuarioService = usuarioService;
        this.proveedorService = proveedorService;
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/all/Usuarios")
    public ResponseEntity<Message> getAllUsuarios() {
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> getUsuarioById(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

    // Obtener todos los proveedores
    @GetMapping("/all/Proveedores")
    public ResponseEntity<Object> getAllProveedores() {
        return proveedorService.obtenerProveedores();
    }

    @GetMapping("/activos/Proveedores")
    public ResponseEntity<Object> getActiveProveedores() {
        return proveedorService.obtenerProveedoresActivos();
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

    // Consultar categorias
    @GetMapping("/all/Categorias")
    public ResponseEntity<Object> obtenerCategorias() {
        return categoriaService.obtenerCategorias();
    }

    // Consultar categorias activas
    @GetMapping("/activas/Categorias")
    public ResponseEntity<Object> obtenerCategoriasActivas() {
        return categoriaService.obtenerCategoriasActivas();
    }
}
