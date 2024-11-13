package integradora.SIGI.usuarios.control;

import integradora.SIGI.usuarios.model.Usuario;
import integradora.SIGI.usuarios.model.UsuarioDTO;
import integradora.SIGI.usuarios.model.UsuarioRepository;
import integradora.SIGI.utils.Message;
import integradora.SIGI.utils.TypesResponse;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        logger.info("La búsqueda ha sido realizada correctamente");
        return new ResponseEntity<>(new Message(usuarios,"Listado de etiquetas", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> saveUsuarios(UsuarioDTO dto) {
        dto.setName(dto.getName().toLowerCase().trim());

        if (dto.getName().length() > 30) {
            return new ResponseEntity<>(new Message("El nombre del Usuario no puede tener más de 30 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (dto.getLastname().length() > 30) {
            return new ResponseEntity<>(new Message("El apellido del Usuario no puede tener más de 30 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (usuarioRepository.findByEmailIgnoreCase(dto.getEmail()).isPresent()) {
            return new ResponseEntity<>(new Message("El email del usuario ya existe", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (dto.getTelephone().length() > 10) {
            return new ResponseEntity<>(new Message("El numero de telefono del Usuario no puede tener más de 10 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (dto.getPassword().length() < 8) {
            return new ResponseEntity<>(new Message("La contraseña del Usuario no puede tener menos de 8 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = new Usuario(dto.getName(), dto.getLastname(), dto.getEmail(), dto.getTelephone(), dto.getPassword(), dto.getRol(), true);
        usuarioRepository.save(usuario);
        return new ResponseEntity<>(new Message(usuario, "Usuario registrado exitosamente", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> updateUsuarios(UsuarioDTO dto) {
        dto.setName(dto.getName().toLowerCase().trim());

        if (dto.getName().length() > 30) {
            return new ResponseEntity<>(new Message("El nombre del Usuario no puede tener más de 30 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (dto.getLastname().length() > 30) {
            return new ResponseEntity<>(new Message("El apellido del Usuario no puede tener más de 30 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(dto.getId());
        if (!usuarioOpt.isPresent()) {
            return new ResponseEntity<>(new Message("Usuario no encontrado", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        if (usuarioRepository.findByEmailIgnoreCaseAndIdNot(dto.getEmail(), dto.getId()).isPresent()) {
            return new ResponseEntity<>(new Message("El email del usuario ya existe", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (dto.getTelephone().length() > 10) {
            return new ResponseEntity<>(new Message("El número de teléfono del Usuario no puede tener más de 10 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (dto.getPassword().length() < 8) {
            return new ResponseEntity<>(new Message("La contraseña del Usuario no puede tener menos de 8 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setName(dto.getName());
        usuario.setLastname(dto.getLastname());
        usuario.setEmail(dto.getEmail());
        usuario.setTelephone(dto.getTelephone());
        usuario.setPassword(dto.getPassword());
        usuario.setRol(dto.getRol());
        usuarioRepository.save(usuario);

        return new ResponseEntity<>(new Message(usuario, "Usuario actualizado exitosamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> changeStatusUsuario(UsuarioDTO dto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(dto.getId());
        if (!usuarioOpt.isPresent()) {
            return new ResponseEntity<>(new Message("Usuario no encontrado", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setStatus(!usuario.isStatus());
        usuarioRepository.save(usuario);

        String estado = usuario.isStatus() ? "habilitado" : "deshabilitado";
        return new ResponseEntity<>(new Message(usuario, "Usuario " + estado + " exitosamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

}
