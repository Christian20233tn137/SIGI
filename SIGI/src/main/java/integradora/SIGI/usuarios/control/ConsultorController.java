package integradora.SIGI.usuarios.control;

import integradora.SIGI.producto.control.ProductoService;
import integradora.SIGI.usuarios.model.UsuarioDTO;
import integradora.SIGI.utils.Message;
import integradora.SIGI.utils.TypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultor")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
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

    // Enviar correo con código de verificación
    @PostMapping("/send-email")
    public ResponseEntity<Object> save(@Validated({UsuarioDTO.FindByEmail.class}) @RequestBody UsuarioDTO dto) {
        return usuarioService.sendEmail(dto);
    }

    // Verificar código de recuperación
    @PostMapping("/verify-code")
    public ResponseEntity<Object> verifyCode(@Validated({UsuarioDTO.VerifyCode.class}) @RequestBody UsuarioDTO dto) {
        return usuarioService.verifyCode(dto);
    }

    // Cambiar contraseña
    @PostMapping("/change-password")
    public ResponseEntity<Message> changePassword(@RequestBody UsuarioDTO dto) {
        // Validar datos básicos
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body(new Message("El correo es obligatorio", TypesResponse.WARNING));
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(new Message("La nueva contraseña es obligatoria", TypesResponse.WARNING));
        }

        // Delegar la lógica al servicio
        return usuarioService.changePassword(dto);
    }
}
