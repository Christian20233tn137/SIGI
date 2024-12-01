package integradora.SIGI.usuarios.control;

import integradora.SIGI.usuarios.model.Usuario;
import integradora.SIGI.usuarios.model.UsuarioDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistroExitosoDeUsuario() {
        // Datos de prueba
        UsuarioDTO nuevoUsuario = new UsuarioDTO();
        nuevoUsuario.setName("Layla");
        nuevoUsuario.setLastname("Gonzales Leyva");
        nuevoUsuario.setEmail("LaylaLeyva@gmail.com");
        nuevoUsuario.setTelephone("9192837465");
        nuevoUsuario.setPassword("SecurityPass123");

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Registro exitoso");

        // Configuración del comportamiento simulado
        when(usuarioService.saveUsuarios(any(UsuarioDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = usuarioController.saveUsuarios(nuevoUsuario);

        // Validación de resultados
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Registro exitoso", respuesta.getBody());
    }
    @Test
    void testRegistroUsuarioConCorreoYaRegistrado() {
        // Datos de prueba
        UsuarioDTO usuarioExistente = new UsuarioDTO();
        usuarioExistente.setName("Maria");
        usuarioExistente.setLastname("González");
        usuarioExistente.setEmail("LaylaLeyva@gmail.com"); // Correo ya registrado
        usuarioExistente.setTelephone("0987654321");
        usuarioExistente.setPassword("nosex.245");

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.badRequest().body("Error: el correo electrónico ya está registrado");

        // Configuración del comportamiento simulado
        when(usuarioService.saveUsuarios(any(UsuarioDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = usuarioController.saveUsuarios(usuarioExistente);

        // Validación de resultados
        assertEquals(400, respuesta.getStatusCodeValue());
        assertEquals("Error: el correo electrónico ya está registrado", respuesta.getBody());
    }
    @Test
    void testRegistroUsuarioSinCamposObligatorios() {
        // Datos de prueba
        UsuarioDTO usuarioIncompleto = new UsuarioDTO();
        usuarioIncompleto.setName("Pedro");
        usuarioIncompleto.setLastname(null); // Falta el apellido
        usuarioIncompleto.setEmail("pedro@gmail.com");
        usuarioIncompleto.setTelephone("1231231234");
        usuarioIncompleto.setPassword("solin.338");

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.badRequest().body("Error: Todos los campos obligatorios deben completarse");

        // Configuración del comportamiento simulado
        when(usuarioService.saveUsuarios(any(UsuarioDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = usuarioController.saveUsuarios(usuarioIncompleto);

        // Validación de resultados
        assertEquals(400, respuesta.getStatusCodeValue());
        assertEquals("Error: Todos los campos obligatorios deben completarse", respuesta.getBody());
    }
    @Test
    void testConsultaUsuariosPorNombre() {
        // Datos de prueba
        String nombreFiltro = "Angel";
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Listado de usuarios con nombre Angel");

        // Configuración del comportamiento simulado
        //when(usuarioService.findByName(nombreFiltro)).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        //ResponseEntity<Object> respuesta = usuarioController.getUsuariosByName(nombreFiltro);

        // Validación de resultados
        //assertEquals(200, Response.getStatusCodeValue());
        //assertEquals("Listado de usuarios con nombre Angel", Response.getBody());
    }
    @Test
    void testConsultaUsuarioPorId() {
        // Datos de prueba
        Long idUsuario = 1L;
        Usuario usuario = new Usuario(1L, "Layla", "Gonzales Leyva", "LaylaLeyva@gmail.com", "9192837465", "SecurityPass123", "Admin", true);

        // Configuración del comportamiento simulado
        when(usuarioService.findById(idUsuario)).thenReturn(Optional.of(usuario));

        // Ejecución del caso de prueba
        Optional<Usuario> respuesta = usuarioController.getUsuarioById(idUsuario);

        // Validación de resultados
        assertEquals("Layla", respuesta.get().getName());
        assertEquals("Gonzales Leyva", respuesta.get().getLastname());
    }
    @Test
    void testRestriccionDeAccesoUsuariosSinRolAdmin() {
        // Datos de prueba
        UsuarioDTO usuarioNoAdmin = new UsuarioDTO();
        usuarioNoAdmin.setRol("User");

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.status(403).body("Error: Acceso restringido");

        // Configuración del comportamiento simulado
        when(usuarioService.findAll()).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = usuarioController.getAllUsuarios();

        // Validación de resultados
        assertEquals(403, respuesta.getStatusCodeValue());
        assertEquals("Error: Acceso restringido", respuesta.getBody());
    }
    @Test
    void testCambioEstadoAHabilitado() {
        // Datos de prueba
        UsuarioDTO usuarioDto = new UsuarioDTO();
        usuarioDto.setId(1L);
        //usuarioDto.setStatus(true);

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Cambio de status exitoso");

        // Configuración del comportamiento simulado
        when(usuarioService.changeStatusUsuario(any(UsuarioDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = usuarioController.changeStatusUsuario(usuarioDto);

        // Validación de resultados
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Cambio de status exitoso", respuesta.getBody());
    }
    @Test
    void testCambioEstadoADeshabilitado() {
        // Datos de prueba
        UsuarioDTO usuarioDto = new UsuarioDTO();
        usuarioDto.setId(1L);
        //usuarioDto.setStatus(false);

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Cambio de status exitoso");

        // Configuración del comportamiento simulado
        when(usuarioService.changeStatusUsuario(any(UsuarioDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = usuarioController.changeStatusUsuario(usuarioDto);

        // Validación de resultados
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Cambio de status exitoso", respuesta.getBody());
    }
}
