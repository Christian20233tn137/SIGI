package integradora.SIGI.categoria.control;

import integradora.SIGI.categoria.model.CategoriaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CategoriaControllerTest {

    // Simulación del servicio de categorías
    @Mock
    private CategoriaService categoriaService;

    // Controlador a probar, con el servicio simulado inyectado
    @InjectMocks
    private CategoriaController categoriaController;

    // Configuración inicial antes de cada prueba
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    void testRegistroExitosoDeCategoria() {
        // Preparación de datos de prueba
        CategoriaDTO nuevaCategoria = new CategoriaDTO();
        nuevaCategoria.setName("Electrónica");
        nuevaCategoria.setDescription("Dispositivos electrónicos y accesorios");
        nuevaCategoria.setStatus(true); // Configuramos la categoría como activa

        // Configuración de la respuesta esperada del servicio
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Categoría registrada exitosamente");

        // Simulación del comportamiento del servicio
        when(categoriaService.registrarCategoria(any(CategoriaDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del método a probar
        ResponseEntity<Object> respuesta = categoriaController.registrarCategoria(nuevaCategoria);

        // Validación de los resultados
        assertEquals(200, respuesta.getStatusCodeValue()); // Código HTTP 200 esperado
        assertEquals("Categoría registrada exitosamente", respuesta.getBody()); // Mensaje esperado
    }

    @Test
    void testRegistroCategoriaSinNombre() {
        // Preparación de datos con un campo obligatorio vacío (nombre)
        CategoriaDTO categoriaSinNombre = new CategoriaDTO();
        categoriaSinNombre.setName(null); // Nombre vacío
        categoriaSinNombre.setDescription("Categoría sin nombre");
        categoriaSinNombre.setStatus(true);

        // Configuración de la respuesta esperada
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.badRequest().body("Error: El campo Nombre es obligatorio");

        // Simulación del comportamiento del servicio
        when(categoriaService.registrarCategoria(any(CategoriaDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del método a probar
        ResponseEntity<Object> respuesta = categoriaController.registrarCategoria(categoriaSinNombre);

        // Validación de los resultados
        assertEquals(400, respuesta.getStatusCodeValue()); // Código HTTP 400 esperado
        assertEquals("Error: El campo Nombre es obligatorio", respuesta.getBody()); // Mensaje esperado
    }

    @Test
    void testRegistroCategoriaSinDescripcion() {
        // Preparación de datos con descripción vacía
        CategoriaDTO categoriaSinDescripcion = new CategoriaDTO();
        categoriaSinDescripcion.setName("Hogar");
        categoriaSinDescripcion.setDescription(null); // Descripción vacía
        categoriaSinDescripcion.setStatus(true);

        // Configuración de la respuesta esperada
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.badRequest().body("Error: El campo Descripción es obligatorio");

        // Simulación del comportamiento del servicio
        when(categoriaService.registrarCategoria(any(CategoriaDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del método a probar
        ResponseEntity<Object> respuesta = categoriaController.registrarCategoria(categoriaSinDescripcion);

        // Validación de los resultados
        assertEquals(400, respuesta.getStatusCodeValue()); // Código HTTP 400 esperado
        assertEquals("Error: El campo Descripción es obligatorio", respuesta.getBody()); // Mensaje esperado
    }

    @Test
    void testRegistroCategoriaNombreDuplicado() {
        // Preparación de datos con un nombre ya existente
        CategoriaDTO categoriaDuplicada = new CategoriaDTO();
        categoriaDuplicada.setName("Electrónica"); // Nombre ya existente
        categoriaDuplicada.setDescription("Nueva descripción para una categoría duplicada");
        categoriaDuplicada.setStatus(true);

        // Configuración de la respuesta esperada
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.badRequest().body("Error: El nombre de la categoría ya existe");

        // Simulación del comportamiento del servicio
        when(categoriaService.registrarCategoria(any(CategoriaDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del método a probar
        ResponseEntity<Object> respuesta = categoriaController.registrarCategoria(categoriaDuplicada);

        // Validación de los resultados
        assertEquals(400, respuesta.getStatusCodeValue()); // Código HTTP 400 esperado
        assertEquals("Error: El nombre de la categoría ya existe", respuesta.getBody()); // Mensaje esperado
    }

    @Test
    void testConsultaExitosaDeTodasLasCategorias() {
        // Datos de prueba
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Listado completo de todas las categorías");

        // Simulación del comportamiento del servicio
        when(categoriaService.obtenerCategorias()).thenReturn(respuestaEsperada);

        // Ejecución del método a probar
        ResponseEntity<Object> respuesta = categoriaController.obtenerCategorias();

        // Validación de los resultados
        assertEquals(200, respuesta.getStatusCodeValue()); // Código HTTP 200 esperado
        assertEquals("Listado completo de todas las categorías", respuesta.getBody()); // Mensaje esperado
    }

    @Test
    void testConsultaExitosaDeCategoriasActivas() {
        // Datos de prueba
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Listado de categorías activas");

        // Simulación del comportamiento del servicio
        when(categoriaService.obtenerCategoriasActivas()).thenReturn(respuestaEsperada);

        // Ejecución del método a probar
        ResponseEntity<Object> respuesta = categoriaController.obtenerCategoriasActivas();

        // Validación de los resultados
        assertEquals(200, respuesta.getStatusCodeValue()); // Código HTTP 200 esperado
        assertEquals("Listado de categorías activas", respuesta.getBody()); // Mensaje esperado
    }

    @Test
    void testMensajeDeAlertaSinCategoriasActivas() {
        // Simulación de un escenario sin categorías activas
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("No hay categorías activas disponibles");

        // Simulación del comportamiento del servicio
        when(categoriaService.obtenerCategoriasActivas()).thenReturn(respuestaEsperada);

        // Ejecución del método a probar
        ResponseEntity<Object> respuesta = categoriaController.obtenerCategoriasActivas();

        // Validación de los resultados
        assertEquals(200, respuesta.getStatusCodeValue()); // Código HTTP 200 esperado
        assertEquals("No hay categorías activas disponibles", respuesta.getBody()); // Mensaje esperado
    }


    @Test
    void testActualizacionExitosaDeCategoria() {
        // Preparación de datos para la actualización
        CategoriaDTO categoriaActualizada = new CategoriaDTO();
        categoriaActualizada.setId(1L);
        categoriaActualizada.setName("Electrónica Avanzada");
        categoriaActualizada.setDescription("Dispositivos electrónicos de última generación");
        categoriaActualizada.setStatus(true);

        // Configuración de la respuesta esperada
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Categoría actualizada correctamente");

        // Simulación del comportamiento del servicio
        when(categoriaService.actualizarCategoria(any(CategoriaDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del método a probar
        ResponseEntity<Object> respuesta = categoriaController.actualizarCategoria(categoriaActualizada);

        // Validación de los resultados
        assertEquals(200, respuesta.getStatusCodeValue()); // Código HTTP 200 esperado
        assertEquals("Categoría actualizada correctamente", respuesta.getBody()); // Mensaje esperado
    }

    @Test
    void testCambioEstadoADeshabilitada() {
        // Preparación de datos para cambiar estado a "deshabilitado"
        CategoriaDTO categoriaDeshabilitada = new CategoriaDTO();
        categoriaDeshabilitada.setId(1L);
        categoriaDeshabilitada.setStatus(false); // Cambiar a "deshabilitada"

        // Configuración de la respuesta esperada
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Cambio de status exitoso");

        // Simulación del comportamiento del servicio
        when(categoriaService.cambiarEstadoCategoria(any(CategoriaDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del método a probar
        ResponseEntity<Object> respuesta = categoriaController.cambiarEstadoCategoria(categoriaDeshabilitada);

        // Validación de los resultados
        assertEquals(200, respuesta.getStatusCodeValue()); // Código HTTP 200 esperado
        assertEquals("Cambio de status exitoso", respuesta.getBody()); // Mensaje esperado
    }

    @Test
    void testCambioEstadoAHabilitada() {
        // Preparación de datos para cambiar estado a "habilitado"
        CategoriaDTO categoriaHabilitada = new CategoriaDTO();
        categoriaHabilitada.setId(2L);
        categoriaHabilitada.setStatus(true); // Cambiar a "habilitada"

        // Configuración de la respuesta esperada
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Cambio de status exitoso");

        // Simulación del comportamiento del servicio
        when(categoriaService.cambiarEstadoCategoria(any(CategoriaDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del método a probar
        ResponseEntity<Object> respuesta = categoriaController.cambiarEstadoCategoria(categoriaHabilitada);

        // Validación de los resultados
        assertEquals(200, respuesta.getStatusCodeValue()); // Código HTTP 200 esperado
        assertEquals("Cambio de status exitoso", respuesta.getBody()); // Mensaje esperado
    }
}
