package integradora.SIGI.usuarios.control;

import integradora.SIGI.categoria.model.CategoriaDTO;
import integradora.SIGI.usuarios.model.Usuario;
import integradora.SIGI.usuarios.model.UsuarioDTO;
import integradora.SIGI.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsuarios() {
        return usuarioService.findAll();
    }

    @PostMapping
    public ResponseEntity<Object> saveUsuarios(@Validated(UsuarioDTO.Register.class) @RequestBody UsuarioDTO dto) {
        return usuarioService.saveUsuarios(dto);
    }

    @PutMapping
    public ResponseEntity<Object> updateUsuarios(@Validated(UsuarioDTO.Modify.class) @RequestBody UsuarioDTO dto) {
        return usuarioService.updateUsuarios(dto);
    }

    @GetMapping("/{id}")
    public Optional<Usuario> getUsuarioById(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

    @PutMapping("/cambiar-estado")
    public ResponseEntity<Object> changeStatusUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.changeStatusUsuario(usuarioDTO);
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
