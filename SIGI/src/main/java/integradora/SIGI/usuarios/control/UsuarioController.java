package integradora.SIGI.usuarios.control;

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

    @GetMapping("/all")
    public ResponseEntity<Message> getAllUsuarios() {
        return usuarioService.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveUsuarios(@Validated(UsuarioDTO.Register.class) @RequestBody UsuarioDTO dto) {
        return usuarioService.saveUsuarios(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUsuario(@PathVariable("id") Long id, @Validated(UsuarioDTO.Modify.class) @RequestBody UsuarioDTO dto) {
        // Asignar el id de la URL al DTO
        dto.setId(id);
        return usuarioService.updateUsuarios(id, dto); // Llamada pasando el id y el dto
    }

    @GetMapping("/{id}")
    public Optional<Usuario> getUsuarioById(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

    @PutMapping("/cambiar-estado")
    public ResponseEntity<Object> changeStatusUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.changeStatusUsuario(usuarioDTO);
    }


}
 