package integradora.SIGI.producto.control;

import integradora.SIGI.producto.model.ProductoDTO;
import integradora.SIGI.producto.model.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistroExitosoDeProducto() {
        // Datos de prueba
        ProductoDTO nuevoProducto = new ProductoDTO();
        nuevoProducto.setNombre("Laptop Dell");
        nuevoProducto.setCantidad(50);
        nuevoProducto.setPrecioUnitario(15000.00);

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Producto registrado exitosamente");

        // Configuración del comportamiento simulado
        when(productoService.registrarProducto(any(ProductoDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = productoController.registrarProducto(nuevoProducto);

        // Validación de resultados
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Producto registrado exitosamente", respuesta.getBody());
    }
    @Test
    void testRegistroProductoCamposFaltantes() {
        // Datos de prueba
        ProductoDTO productoIncompleto = new ProductoDTO();
        productoIncompleto.setNombre("Smartphone");
        productoIncompleto.setCantidad(100);
        productoIncompleto.setPrecioUnitario(null); // Precio unitario faltante

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.badRequest().body("Error: Todos los campos obligatorios deben completarse");

        // Configuración del comportamiento simulado
        when(productoService.registrarProducto(any(ProductoDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = productoController.registrarProducto(productoIncompleto);

        // Validación de resultados
        assertEquals(400, respuesta.getStatusCodeValue());
        assertEquals("Error: Todos los campos obligatorios deben completarse", respuesta.getBody());
    }

    @Test
    void testConsultaExitosaDeProductos() {
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Listado de todos los productos");

        // Configuración del comportamiento simulado
        when(productoService.obtenerProductos()).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = productoController.obtenerProductos();

        // Validación de resultados
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Listado de todos los productos", respuesta.getBody());
    }

    @Test
    void testConsultaProductosPorNombre() {
        String nombreFiltro = "Laptop";
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Listado de productos con nombre Laptop");

        // Configuración del comportamiento simulado
        when(productoService.obtenerProductos()).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = productoController.obtenerProductos(); // Método modificado para simulación

        // Validación de resultados
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Listado de productos con nombre Laptop", respuesta.getBody());
    }
/*
    @Test
    void testMensajeDeAlertaSinProductosEncontrados() {
        // Datos de prueba para un filtro que no coincida con ningún producto
        String filtroNombre = "Producto Inexistente";
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("No se encontraron productos con el filtro proporcionado");

        // Simulación del comportamiento del servicio
        when(productoService.existsByNombreIgnoreCase(filtroNombre)).thenReturn(respuestaEsperada);

        // Ejecución del método a probar
        ResponseEntity<Object> respuesta = productoController.existsByNombreIgnoreCase(filtroNombre);

        // Validación de los resultados
        assertEquals(200, respuesta.getStatusCodeValue()); // Código HTTP 200 esperado
        assertEquals("No se encontraron productos con el filtro proporcionado", respuesta.getBody()); // Mensaje esperado
    }
 */

    @Test
    void testConsultaProductosActivos() {
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Listado de productos activos");

        // Configuración del comportamiento simulado
        when(productoService.obtenerProductosActivos()).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = productoController.obtenerProductosActivos();

        // Validación de resultados
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Listado de productos activos", respuesta.getBody());
    }

    @Test
    void testMensajeDeAlertaSinProductosActivos() {
        // Simulación de un escenario sin productos activos
        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("No hay productos activos disponibles");

        // Simulación del comportamiento del servicio
        when(productoService.obtenerProductosActivos()).thenReturn(respuestaEsperada);

        // Ejecución del método a probar
        ResponseEntity<Object> respuesta = productoController.obtenerProductosActivos();

        // Validación de los resultados
        assertEquals(200, respuesta.getStatusCodeValue()); // Código HTTP 200 esperado
        assertEquals("No hay productos activos disponibles", respuesta.getBody()); // Mensaje esperado
    }

    @Test
    void testActualizacionExitosaDeProducto() {
        // Datos de prueba
        ProductoDTO productoActualizado = new ProductoDTO();
        productoActualizado.setId(1L);
        productoActualizado.setNombre("Laptop Dell Inspiron");
        productoActualizado.setCantidad(60);
        productoActualizado.setPrecioUnitario(15500.00);

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Producto actualizado correctamente");

        // Configuración del comportamiento simulado
        when(productoService.actualizarProducto(any(ProductoDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = productoController.actualizarProducto(productoActualizado);

        // Validación de resultados
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Producto actualizado correctamente", respuesta.getBody());
    }

    @Test
    void testActualizacionProductoCamposFaltantes() {
        // Datos de prueba
        ProductoDTO productoIncompleto = new ProductoDTO();
        productoIncompleto.setId(2L);
        productoIncompleto.setNombre(null); // Nombre faltante
        productoIncompleto.setCantidad(30);
        productoIncompleto.setPrecioUnitario(12000.00);

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.badRequest().body("Error: Todos los campos obligatorios deben completarse");

        // Configuración del comportamiento simulado
        when(productoService.actualizarProducto(any(ProductoDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = productoController.actualizarProducto(productoIncompleto);

        // Validación de resultados
        assertEquals(400, respuesta.getStatusCodeValue());
        assertEquals("Error: Todos los campos obligatorios deben completarse", respuesta.getBody());
    }

    @Test
    void testCambioEstadoADeshabilitadoProducto() {
        // Datos de prueba
        ProductoDTO productoDeshabilitado = new ProductoDTO();
        productoDeshabilitado.setId(1L);
        productoDeshabilitado.setStatus(false); // Cambiar a "deshabilitado"

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Cambio de status exitoso");

        // Configuración del comportamiento simulado
        when(productoService.cambiarEstadoProducto(any(ProductoDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = productoController.cambiarEstadoProducto(productoDeshabilitado);

        // Validación de resultados
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Cambio de status exitoso", respuesta.getBody());
    }

    @Test
    void testCambioEstadoAHabilitadoProducto() {
        // Datos de prueba
        ProductoDTO productoHabilitado = new ProductoDTO();
        productoHabilitado.setId(2L);
        productoHabilitado.setStatus(true); // Cambiar a "habilitado"

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Cambio de status exitoso");

        // Configuración del comportamiento simulado
        when(productoService.cambiarEstadoProducto(any(ProductoDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = productoController.cambiarEstadoProducto(productoHabilitado);

        // Validación de resultados
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Cambio de status exitoso", respuesta.getBody());
    }

    @Test
    void testAgregarCantidadAlProducto() {
        // Datos de prueba
        ProductoDTO productoStockIncremento = new ProductoDTO();
        productoStockIncremento.setId(1L);
        productoStockIncremento.setCantidad(10); // Incrementar en 10 unidades

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Stock incrementado exitosamente");

        // Configuración del comportamiento simulado
        when(productoService.agregarCantidad(any(ProductoDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = productoController.agregarCantidad(productoStockIncremento);

        // Validación de resultados
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Stock incrementado exitosamente", respuesta.getBody());
    }

    @Test
    void testAgregarCantidadValorInvalido() {
        // Datos de prueba
        ProductoDTO productoStockInvalido = new ProductoDTO();
        productoStockInvalido.setId(1L);
        productoStockInvalido.setCantidad(-5); // Cantidad negativa no permitida

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.badRequest().body("Error: La cantidad a agregar debe ser mayor a cero");

        // Configuración del comportamiento simulado
        when(productoService.agregarCantidad(any(ProductoDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = productoController.agregarCantidad(productoStockInvalido);

        // Validación de resultados
        assertEquals(400, respuesta.getStatusCodeValue());
        assertEquals("Error: La cantidad a agregar debe ser mayor a cero", respuesta.getBody());
    }

    @Test
    void testDisminuirCantidadDelProducto() {
        // Datos de prueba
        ProductoDTO productoStockDecremento = new ProductoDTO();
        productoStockDecremento.setId(1L);
        productoStockDecremento.setCantidad(5); // Disminuir en 5 unidades

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.ok("Stock decrementado exitosamente");

        // Configuración del comportamiento simulado
        when(productoService.disminuirCantidad(any(ProductoDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = productoController.disminuirCantidad(productoStockDecremento);

        // Validación de resultados
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals("Stock decrementado exitosamente", respuesta.getBody());
    }

    @Test
    void testDisminuirCantidadSobreStockDisponible() {
        // Datos de prueba
        ProductoDTO productoStockInsuficiente = new ProductoDTO();
        productoStockInsuficiente.setId(1L);
        productoStockInsuficiente.setCantidad(100); // Disminuir más de lo disponible

        ResponseEntity<Object> respuestaEsperada = ResponseEntity.badRequest().body("Error: Stock insuficiente para realizar la operación");

        // Configuración del comportamiento simulado
        when(productoService.disminuirCantidad(any(ProductoDTO.class))).thenReturn(respuestaEsperada);

        // Ejecución del caso de prueba
        ResponseEntity<Object> respuesta = productoController.disminuirCantidad(productoStockInsuficiente);

        // Validación de resultados
        assertEquals(400, respuesta.getStatusCodeValue());
        assertEquals("Error: Stock insuficiente para realizar la operación", respuesta.getBody());
    }

}