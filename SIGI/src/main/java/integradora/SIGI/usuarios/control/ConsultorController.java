package integradora.SIGI.usuarios.control;

import integradora.SIGI.producto.control.ProductoService;
import integradora.SIGI.usuarios.model.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultor")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class ConsultorController {

    private final UsuarioService usuarioService;
    private final ProductoService productoService;

    @Autowired
    public ConsultorController(ProductoService productoService, UsuarioService usuarioService) {
        this.productoService = productoService;
        this.usuarioService = usuarioService;
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

    @PostMapping("/send-email")
    public ResponseEntity<Object> save(@Validated({UsuarioDTO.FindByEmail.class}) @RequestBody UsuarioDTO dto){
        return usuarioService.sendEmail(dto);
    }

    @PostMapping("/verify-code")
    public ResponseEntity<Object> verifyCode(@Validated({UsuarioDTO.VerifyCode.class}) @RequestBody UsuarioDTO dto){
        return usuarioService.verifyCode(dto);
    }
}
