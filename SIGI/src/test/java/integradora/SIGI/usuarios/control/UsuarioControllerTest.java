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

    // Simulación del servicio de usuarios
    @Mock
    private UsuarioService usuarioService;

    // Controlador a probar, con el servicio simulado inyectado
    @InjectMocks
    private UsuarioController usuarioController;

    // Configuración inicial antes de cada prueba
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    void testRegistroExitosoDeUsuario() {
        // Preparación de datos de prueba para un registro exitoso
        UsuarioDTO nuevoUsuario = new UsuarioDTO();
        nuevoUsuario.setName("Layla");
        nuevoUsuario.setLastname("Gonzales Leyva");
        nuevoUsuario.setEmail("LaylaLeyva@gmail.com");
        nuevoUsuario.setTelephone("9192837465");
        nuevoUsuario.setPassword("SecurityPass123");

        // Configuración de la respuesta esperada
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Registro exitoso");

        // Simulación del comportamiento del servicio
        when(usuarioService.saveUsuarios(any(UsuarioDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del método a probar
        ResponseEntity<Object> respuesta = usuarioController.saveUsuarios(nuevoUsuario);

        // Validación de los resultados
        assertEquals(200, respuesta.getStatusCodeValue()); // Código HTTP 200 esperado
        assertEquals("Registro exitoso", respuesta.getBody()); // Mensaje esperado
    }

    @Test
    void testRegistroUsuarioConCorreoYaRegistrado() {
        // Preparación de datos con un correo ya registrado
        UsuarioDTO usuarioExistente = new UsuarioDTO();
        usuarioExistente.setName("Maria");
        usuarioExistente.setLastname("González");
        usuarioExistente.setEmail("LaylaLeyva@gmail.com"); // Correo ya registrado
        usuarioExistente.setTelephone("0987654321");
        usuarioExistente.setPassword("nosex.245");

        // Configuración de la respuesta esperada
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.badRequest().body("Error: el correo electrónico ya está registrado");

        // Simulación del comportamiento del servicio
        when(usuarioService.saveUsuarios(any(UsuarioDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del método a probar
        ResponseEntity<Object> respuesta = usuarioController.saveUsuarios(usuarioExistente);

        // Validación de los resultados
        assertEquals(400, respuesta.getStatusCodeValue()); // Código HTTP 400 esperado
        assertEquals("Error: el correo electrónico ya está registrado", respuesta.getBody()); // Mensaje esperado
    }

    @Test
    void testRegistroUsuarioSinCamposObligatorios() {
        // Preparación de datos incompletos (apellido faltante)
        UsuarioDTO usuarioIncompleto = new UsuarioDTO();
        usuarioIncompleto.setName("Pedro");
        usuarioIncompleto.setLastname(null); // Falta el apellido
        usuarioIncompleto.setEmail("pedro@gmail.com");
        usuarioIncompleto.setTelephone("1231231234");
        usuarioIncompleto.setPassword("solin.338");

        // Configuración de la respuesta esperada
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.badRequest().body("Error: Todos los campos obligatorios deben completarse");

        // Simulación del comportamiento del servicio
        when(usuarioService.saveUsuarios(any(UsuarioDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del método a probar
        ResponseEntity<Object> respuesta = usuarioController.saveUsuarios(usuarioIncompleto);

        // Validación de los resultados
        assertEquals(400, respuesta.getStatusCodeValue()); // Código HTTP 400 esperado
        assertEquals("Error: Todos los campos obligatorios deben completarse", respuesta.getBody()); // Mensaje esperado
    }

    @Test
    void testConsultaUsuarioPorId() {
        // Preparación de datos para la consulta por ID
        Long idUsuario = 1L;
        Usuario usuario = new Usuario(1L, "Layla", "Gonzales Leyva", "LaylaLeyva@gmail.com", "9192837465", "SecurityPass123", "Admin", true);

        // Simulación del comportamiento del servicio
        when(usuarioService.findById(idUsuario)).thenReturn(Optional.of(usuario));

        // Ejecución del método a probar
        Optional<Usuario> respuesta = usuarioController.getUsuarioById(idUsuario);

        // Validación de los resultados
        assertEquals("Layla", respuesta.get().getName()); // Verificar nombre
        assertEquals("Gonzales Leyva", respuesta.get().getLastname()); // Verificar apellido
    }
    @Test
    void testEdicionExitosaDeUsuario() {
        // Datos de prueba para edición exitosa
        UsuarioDTO usuarioAEditar = new UsuarioDTO();
        usuarioAEditar.setId(1L);
        usuarioAEditar.setName("Layla Karely");
        usuarioAEditar.setLastname("Gonzales Leyva Beltran de los Monteros");
        usuarioAEditar.setEmail("LaylaLeyva@gmail.com");
        usuarioAEditar.setTelephone("7776768909");
        usuarioAEditar.setPassword("NuevoPassword123");
        usuarioAEditar.setStatus(true);

        // Configuración de la respuesta esperada
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Edición exitosa");

        // Simulación del comportamiento del servicio
        when(usuarioService.updateUsuarios(any(UsuarioDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del método a probar
        ResponseEntity<Object> respuesta = usuarioController.updateUsuarios(usuarioAEditar);

        // Validación de los resultados
        assertEquals(200, respuesta.getStatusCodeValue()); // Código HTTP 200 esperado
        assertEquals("Edición exitosa", respuesta.getBody()); // Mensaje esperado
    }
    @Test
    void testEdicionUsuarioSinRolDeAdministrador() {
        // Datos de prueba de un usuario sin rol de administrador
        UsuarioDTO usuarioNoAdmin = new UsuarioDTO();
        usuarioNoAdmin.setId(2L);
        usuarioNoAdmin.setName("Carlos");
        usuarioNoAdmin.setLastname("Pérez");
        usuarioNoAdmin.setEmail("CarlosPerez@example.com");
        usuarioNoAdmin.setTelephone("9998887777");
        usuarioNoAdmin.setPassword("NoAdmin123");
        usuarioNoAdmin.setStatus(true);
        usuarioNoAdmin.setRol("User"); // Rol no administrador

        // Configuración de la respuesta esperada
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.status(403).body("Error: Acceso restringido");

        // Simulación del comportamiento del servicio
        when(usuarioService.updateUsuarios(any(UsuarioDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del método a probar
        ResponseEntity<Object> respuesta = usuarioController.updateUsuarios(usuarioNoAdmin);

        // Validación de los resultados
        assertEquals(403, respuesta.getStatusCodeValue()); // Código HTTP 403 esperado
        assertEquals("Error: Acceso restringido", respuesta.getBody()); // Mensaje esperado
    }
    @Test
    void testErrorAlEditarUsuarioConCamposObligatoriosIncompletos() {
        // Datos de prueba con un campo obligatorio vacío (teléfono)
        UsuarioDTO usuarioIncompleto = new UsuarioDTO();
        usuarioIncompleto.setId(3L);
        usuarioIncompleto.setName("Layla Karely");
        usuarioIncompleto.setLastname("Gómez");
        usuarioIncompleto.setEmail("leyla.gomez@example.com");
        usuarioIncompleto.setTelephone(null); // Teléfono vacío
        usuarioIncompleto.setPassword("ContraseñaSegura123");
        usuarioIncompleto.setStatus(true);

        // Configuración de la respuesta esperada
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.badRequest().body("Error: Todos los campos obligatorios deben completarse");

        // Simulación del comportamiento del servicio
        when(usuarioService.updateUsuarios(any(UsuarioDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del método a probar
        ResponseEntity<Object> respuesta = usuarioController.updateUsuarios(usuarioIncompleto);

        // Validación de los resultados
        assertEquals(400, respuesta.getStatusCodeValue()); // Código HTTP 400 esperado
        assertEquals("Error: Todos los campos obligatorios deben completarse", respuesta.getBody()); // Mensaje esperado
    }

    @Test
    void testCambioEstadoAHabilitado() {
        // Preparación de datos para cambiar estado a "habilitado"
        UsuarioDTO usuarioDto = new UsuarioDTO();
        usuarioDto.setId(1L);
        usuarioDto.setStatus(true); // Cambiar a "habilitado"

        // Configuración de la respuesta esperada
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Cambio de status exitoso");

        // Simulación del comportamiento del servicio
        when(usuarioService.changeStatusUsuario(any(UsuarioDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del método a probar
        ResponseEntity<Object> respuesta = usuarioController.changeStatusUsuario(usuarioDto);

        // Validación de los resultados
        assertEquals(200, respuesta.getStatusCodeValue()); // Código HTTP 200 esperado
        assertEquals("Cambio de status exitoso", respuesta.getBody()); // Mensaje esperado
    }

    @Test
    void testCambioEstadoADeshabilitado() {
        // Preparación de datos para cambiar estado a "deshabilitado"
        UsuarioDTO usuarioDto = new UsuarioDTO();
        usuarioDto.setId(1L);
        usuarioDto.setStatus(false); // Cambiar a "deshabilitado"

        // Configuración de la respuesta esperada
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Cambio de status exitoso");

        // Simulación del comportamiento del servicio
        when(usuarioService.changeStatusUsuario(any(UsuarioDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del método a probar
        ResponseEntity<Object> respuesta = usuarioController.changeStatusUsuario(usuarioDto);

        // Validación de los resultados
        assertEquals(200, respuesta.getStatusCodeValue()); // Código HTTP 200 esperado
        assertEquals("Cambio de status exitoso", respuesta.getBody()); // Mensaje esperado
    }
}
