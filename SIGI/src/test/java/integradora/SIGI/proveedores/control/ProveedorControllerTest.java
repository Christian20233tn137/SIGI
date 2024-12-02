package integradora.SIGI.proveedores.control;

import integradora.SIGI.proveedores.model.ProveedorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ProveedorControllerTest {

    @Mock
    private ProveedorService proveedorService;

    @InjectMocks
    private ProveedorController proveedorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testRegistroExitosoDeProveedor() {
        // Datos de prueba
        ProveedorDTO nuevoProveedor = new ProveedorDTO();
        nuevoProveedor.setRfc("XYZ123456789");
        nuevoProveedor.setDireccion("Calle Ficticia 123");
        nuevoProveedor.setTelefono("5551234567");
        nuevoProveedor.setEmail("contacto@xyz.com");
        nuevoProveedor.setStatus(true);

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Proveedor registrado exitosamente");

        // Configuración del comportamiento simulado
        when(proveedorService.registrarProveedor(any(ProveedorDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = proveedorController.saveProveedor(nuevoProveedor);

        // Validación de resultados
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Proveedor registrado exitosamente", respuesta.getBody());
    }
    @Test
    void testRegistroProveedorCamposFaltantes() {
        // Datos de prueba
        ProveedorDTO proveedorIncompleto = new ProveedorDTO();
        proveedorIncompleto.setRfc("ABC987654321");
        proveedorIncompleto.setDireccion("Avenida Principal 45");
        proveedorIncompleto.setTelefono("5559876543");
        proveedorIncompleto.setEmail(null); // Falta el campo email

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.badRequest().body("Error: Todos los campos obligatorios deben completarse");

        // Configuración del comportamiento simulado
        when(proveedorService.registrarProveedor(any(ProveedorDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = proveedorController.saveProveedor(proveedorIncompleto);

        // Validación de resultados
        assertEquals(400, respuesta.getStatusCodeValue());
        assertEquals("Error: Todos los campos obligatorios deben completarse", respuesta.getBody());
    }
    @Test
    void testConsultaExitosaDeProveedores() {
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Listado de todos los proveedores");

        // Configuración del comportamiento simulado
        when(proveedorService.obtenerProveedores()).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = proveedorController.getAllProveedores();

        // Validación de resultados
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Listado de todos los proveedores", respuesta.getBody());
    }
    @Test
    void testConsultaProveedoresActivos() {
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Listado de proveedores activos");

        // Configuración del comportamiento simulado
        when(proveedorService.obtenerProveedoresActivos()).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = proveedorController.getActiveProveedores();

        // Validación de resultados
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Listado de proveedores activos", respuesta.getBody());
    }
    @Test
    void testMensajeDeAlertaSinProveedoresActivos() {
        // Simulación de un escenario sin proveedores activos
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("No hay proveedores activos disponibles");

        // Simulación del comportamiento del servicio
        when(proveedorService.obtenerProveedoresActivos()).thenReturn(respuestaEsperada);

        // Ejecución del método a probar
        ResponseEntity<Object> respuesta = proveedorController.getActiveProveedores();

        // Validación de los resultados
        assertEquals(200, respuesta.getStatusCodeValue()); // Código HTTP 200 esperado
        assertEquals("No hay proveedores activos disponibles", respuesta.getBody()); // Mensaje esperado
    }

    @Test
    void testActualizacionExitosaDeProveedor() {
        // Datos de prueba
        ProveedorDTO proveedorActualizado = new ProveedorDTO();
        proveedorActualizado.setId(1L);
        proveedorActualizado.setRfc("A123456789");
        proveedorActualizado.setDireccion("Calle Ficticia 456");
        proveedorActualizado.setTelefono("5559876543");
        proveedorActualizado.setEmail("nuevo@proveedora.com");

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Proveedor actualizado correctamente");

        // Configuración del comportamiento simulado
        when(proveedorService.actualizarProveedor(any(ProveedorDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = proveedorController.updateProveedor(proveedorActualizado);

        // Validación de resultados
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Proveedor actualizado correctamente", respuesta.getBody());
    }
    @Test
    void testActualizacionProveedorCamposFaltantes() {
        // Datos de prueba
        ProveedorDTO proveedorIncompleto = new ProveedorDTO();
        proveedorIncompleto.setId(2L);
        proveedorIncompleto.setRfc("B987654321");
        proveedorIncompleto.setDireccion("Avenida Principal 789");
        proveedorIncompleto.setTelefono("5551234567");
        proveedorIncompleto.setEmail(null); // Falta el campo email

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.badRequest().body("Error: Todos los campos obligatorios deben completarse");

        // Configuración del comportamiento simulado
        when(proveedorService.actualizarProveedor(any(ProveedorDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = proveedorController.updateProveedor(proveedorIncompleto);

        // Validación de resultados
        assertEquals(400, respuesta.getStatusCodeValue());
        assertEquals("Error: Todos los campos obligatorios deben completarse", respuesta.getBody());
    }
    @Test
    void testCambioEstadoADeshabilitadoProveedor() {
        // Datos de prueba
        ProveedorDTO proveedorDeshabilitado = new ProveedorDTO();
        proveedorDeshabilitado.setId(1L);
        proveedorDeshabilitado.setStatus(false); // Cambiar a "deshabilitado"

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Cambio de status exitoso");

        // Configuración del comportamiento simulado
        when(proveedorService.cambiarEstadoProveedor(any(ProveedorDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = proveedorController.changeStatusProveedor(proveedorDeshabilitado);

        // Validación de resultados
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Cambio de status exitoso", respuesta.getBody());
    }
    @Test
    void testCambioEstadoAHabilitadoProveedor() {
        // Datos de prueba
        ProveedorDTO proveedorHabilitado = new ProveedorDTO();
        proveedorHabilitado.setId(2L);
        proveedorHabilitado.setStatus(true); // Cambiar a "habilitado"

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Cambio de status exitoso");

        // Configuración del comportamiento simulado
        when(proveedorService.cambiarEstadoProveedor(any(ProveedorDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = proveedorController.changeStatusProveedor(proveedorHabilitado);

        // Validación de resultados
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Cambio de status exitoso", respuesta.getBody());
    }

}