package integradora.SIGI.producto.control;

import integradora.SIGI.producto.model.Producto;
import integradora.SIGI.producto.model.ProductoDTO;
import integradora.SIGI.producto.model.ProductoRepository;
import integradora.SIGI.utils.Message;
import integradora.SIGI.utils.TypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoService {

    private final ProductoRepository repository;

    @Autowired
    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    // Registrar producto
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> registrarProducto(ProductoDTO dto) {
        if (repository.existsByNombreIgnoreCase(dto.getNombre())) {
            return new ResponseEntity<>(new Message("El nombre del producto ya existe", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Producto producto = new Producto();
        producto.setNombre(dto.getNombre().toLowerCase().trim());
        producto.setCantidad(dto.getCantidad());
        producto.setPrecioUnitario(dto.getPrecioUnitario());
        repository.save(producto);

        return new ResponseEntity<>(new Message(producto, "Producto registrado exitosamente", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    // Consultar productos
    @Transactional(readOnly = true)
    public ResponseEntity<Object> obtenerProductos() {
        List<Producto> productos = repository.findAll();
        return new ResponseEntity<>(new Message(productos, "Listado de productos", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Consultar productos activos
    @Transactional(readOnly = true)
    public ResponseEntity<Object> obtenerProductosActivos() {
        List<Producto> productos = repository.findByStatus(true);
        return new ResponseEntity<>(new Message(productos, "Listado de productos activos", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Actualizar producto
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> actualizarProducto(ProductoDTO dto) {
        Optional<Producto> productoOpt = repository.findById(dto.getId());
        if (!productoOpt.isPresent()) {
            return new ResponseEntity<>(new Message("Producto no encontrado", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        Producto producto = productoOpt.get();
        if (repository.existsByNombreIgnoreCaseAndIdNot(dto.getNombre(), dto.getId())) {
            return new ResponseEntity<>(new Message("El nombre del producto ya existe", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        producto.setNombre(dto.getNombre().toLowerCase().trim());
        producto.setCantidad(dto.getCantidad());
        producto.setPrecioUnitario(dto.getPrecioUnitario());
        repository.save(producto);

        return new ResponseEntity<>(new Message(producto, "Producto actualizado exitosamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Cambiar estado de producto
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> cambiarEstadoProducto(ProductoDTO dto) {
        Optional<Producto> productoOpt = repository.findById(dto.getId());
        if (!productoOpt.isPresent()) {
            return new ResponseEntity<>(new Message("Producto no encontrado", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        Producto producto = productoOpt.get();
        producto.setStatus(!producto.isStatus());
        repository.save(producto);

        String estado = producto.isStatus() ? "habilitado" : "deshabilitado";
        return new ResponseEntity<>(new Message(producto, "Producto " + estado + " exitosamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Agregar cantidad a producto
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> agregarCantidad(ProductoDTO dto) {
        Optional<Producto> productoOpt = repository.findById(dto.getId());
        if (!productoOpt.isPresent()) {
            return new ResponseEntity<>(new Message("Producto no encontrado", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        Producto producto = productoOpt.get();
        producto.setCantidad(producto.getCantidad() + dto.getCantidad());
        repository.save(producto);

        return new ResponseEntity<>(new Message(producto, "Cantidad agregada exitosamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Disminuir cantidad de producto
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> disminuirCantidad(ProductoDTO dto) {
        Optional<Producto> productoOpt = repository.findById(dto.getId());
        if (!productoOpt.isPresent()) {
            return new ResponseEntity<>(new Message("Producto no encontrado", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        Producto producto = productoOpt.get();
        if (producto.getCantidad() < dto.getCantidad()) {
            return new ResponseEntity<>(new Message("La cantidad a disminuir supera la cantidad actual", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        producto.setCantidad(producto.getCantidad() - dto.getCantidad());
        repository.save(producto);

        return new ResponseEntity<>(new Message(producto, "Cantidad disminuida exitosamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }
}
