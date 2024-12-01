package integradora.SIGI.categoria.control;

import integradora.SIGI.categoria.model.CategoriaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CategoriaControllerTest {

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private CategoriaController categoriaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistroExitosoDeCategoria() {
        // Datos de prueba
        CategoriaDTO nuevaCategoria = new CategoriaDTO();
        nuevaCategoria.setName("Electrónica");
        nuevaCategoria.setDescription("Dispositivos electrónicos y accesorios");
        //nuevaCategoria.setStatus(true);

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Categoría registrada exitosamente");

        // Configuración del comportamiento simulado
        when(categoriaService.registrarCategoria(any(CategoriaDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = categoriaController.registrarCategoria(nuevaCategoria);

        // Validación de resultados
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Categoría registrada exitosamente", respuesta.getBody());
    }

    @Test
    void testRegistroCategoriaSinNombre() {
        // Datos de prueba
        CategoriaDTO categoriaSinNombre = new CategoriaDTO();
        categoriaSinNombre.setName(null); // Nombre vacío
        categoriaSinNombre.setDescription("Categoría sin nombre");
        //categoriaSinNombre.setStatus(true);

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.badRequest().body("Error: El campo Nombre es obligatorio");

        // Configuración del comportamiento simulado
        when(categoriaService.registrarCategoria(any(CategoriaDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = categoriaController.registrarCategoria(categoriaSinNombre);

        // Validación de resultados
        assertEquals(400, respuesta.getStatusCodeValue());
        assertEquals("Error: El campo Nombre es obligatorio", respuesta.getBody());
    }
    @Test
    void testRegistroCategoriaSinDescripcion() {
        // Datos de prueba
        CategoriaDTO categoriaSinDescripcion = new CategoriaDTO();
        categoriaSinDescripcion.setName("Hogar");
        categoriaSinDescripcion.setDescription(null); // Descripción vacía
        //categoriaSinDescripcion.setStatus(true);

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.badRequest().body("Error: El campo Descripción es obligatorio");

        // Configuración del comportamiento simulado
        when(categoriaService.registrarCategoria(any(CategoriaDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = categoriaController.registrarCategoria(categoriaSinDescripcion);

        // Validación de resultados
        assertEquals(400, respuesta.getStatusCodeValue());
        assertEquals("Error: El campo Descripción es obligatorio", respuesta.getBody());
    }
    @Test
    void testRegistroCategoriaNombreDuplicado() {
        // Datos de prueba
        CategoriaDTO categoriaDuplicada = new CategoriaDTO();
        categoriaDuplicada.setName("Electrónica"); // Nombre ya existente
        categoriaDuplicada.setDescription("Nueva descripción para una categoría duplicada");
        //categoriaDuplicada.setStatus(true);

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.badRequest().body("Error: El nombre de la categoría ya existe");

        // Configuración del comportamiento simulado
        when(categoriaService.registrarCategoria(any(CategoriaDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = categoriaController.registrarCategoria(categoriaDuplicada);

        // Validación de resultados
        assertEquals(400, respuesta.getStatusCodeValue());
        assertEquals("Error: El nombre de la categoría ya existe", respuesta.getBody());
    }
    @Test
    void testActualizacionExitosaDeCategoria() {
        // Datos de prueba
        CategoriaDTO categoriaActualizada = new CategoriaDTO();
        categoriaActualizada.setId(1L);
        categoriaActualizada.setName("Electrónica Avanzada");
        categoriaActualizada.setDescription("Dispositivos electrónicos de última generación");
        //categoriaActualizada.setStatus(true);

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Categoría actualizada correctamente");

        // Configuración del comportamiento simulado
        when(categoriaService.actualizarCategoria(any(CategoriaDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = categoriaController.actualizarCategoria(categoriaActualizada);

        // Validación de resultados
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Categoría actualizada correctamente", respuesta.getBody());
    }
    @Test
    void testActualizacionCategoriaSinNombreODescripcion() {
        // Datos de prueba
        CategoriaDTO categoriaIncompleta = new CategoriaDTO();
        categoriaIncompleta.setId(1L);
        categoriaIncompleta.setName(null); // Nombre vacío
        categoriaIncompleta.setDescription("Descripción válida");

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.badRequest().body("Error: Los campos obligatorios deben completarse");

        // Configuración del comportamiento simulado
        when(categoriaService.actualizarCategoria(any(CategoriaDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = categoriaController.actualizarCategoria(categoriaIncompleta);

        // Validación de resultados
        assertEquals(400, respuesta.getStatusCodeValue());
        assertEquals("Error: Los campos obligatorios deben completarse", respuesta.getBody());
    }
    @Test
    void testCambioEstadoADeshabilitada() {
        // Datos de prueba
        CategoriaDTO categoriaDeshabilitada = new CategoriaDTO();
        categoriaDeshabilitada.setId(1L);
        //categoriaDeshabilitada.setStatus(false); // Cambiar a "deshabilitada"

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Cambio de status exitoso");

        // Configuración del comportamiento simulado
        when(categoriaService.cambiarEstadoCategoria(any(CategoriaDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = categoriaController.cambiarEstadoCategoria(categoriaDeshabilitada);

        // Validación de resultados
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Cambio de status exitoso", respuesta.getBody());
    }
    @Test
    void testCambioEstadoAHabilitada() {
        // Datos de prueba
        CategoriaDTO categoriaHabilitada = new CategoriaDTO();
        categoriaHabilitada.setId(2L);
        //categoriaHabilitada.setStatus(true); // Cambiar a "habilitada"

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Cambio de status exitoso");

        // Configuración del comportamiento simulado
        when(categoriaService.cambiarEstadoCategoria(any(CategoriaDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = categoriaController.cambiarEstadoCategoria(categoriaHabilitada);

        // Validación de resultados
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Cambio de status exitoso", respuesta.getBody());
    }


}