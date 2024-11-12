package integradora.SIGI.usuarios.control;

import integradora.SIGI.usuarios.model.Usuario;
import integradora.SIGI.usuarios.model.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/all")
    public ResponseEntity<Message> getAllUsuarios() {
        return usuarioService.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<Message> saveUsuarios(@Validated(UsuarioDTO.Register.class) @RequestBody UsuarioDTO dto) {
        return usuarioService.save(dto);
    }

    @PutMapping("/update")
    public ResponseEntity<Message> updateUsuarios(@Validated(UsuarioDTO.Modify.class) @RequestBody UsuarioDTO dto) {
        return usuarioService.update(dto);
    }

    @GetMapping("/{id}")
    public Optional<Usuario> getUsuarioById(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

    @DeleteMapping("/{id}")
    public Usuario deleteUsuario(@PathVariable Long id) {
        return usuarioService.changeStatus(id);
    }

}
