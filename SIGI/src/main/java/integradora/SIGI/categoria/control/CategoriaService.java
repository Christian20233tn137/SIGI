package integradora.SIGI.categoria.control;

import integradora.SIGI.categoria.model.Categoria;
import integradora.SIGI.categoria.model.CategoriaDTO;
import integradora.SIGI.categoria.model.CategoriaRepository;
import integradora.SIGI.utils.Message;
import integradora.SIGI.utils.TypesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CategoriaService {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaService.class);

    private final CategoriaRepository repository;

    @Autowired
    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    // Registrar categoría
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> registrarCategoria(CategoriaDTO dto) {
        logger.info("Iniciando registro de categoría con nombre: {}", dto.getName());

        dto.setName(dto.getName().toLowerCase().trim());

        if (dto.getName().length() < 3) {
            logger.warn("El nombre de la categoría es muy corto: {}", dto.getName());
            return new ResponseEntity<>(new Message("El nombre de la categoría no puede tener menos de 3 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (repository.findByNameIgnoreCase(dto.getName()).isPresent()) {
            logger.warn("El nombre de la categoría ya existe: {}", dto.getName());
            return new ResponseEntity<>(new Message("El nombre de la categoría ya existe", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Categoria categoria = new Categoria(dto.getName(), dto.getDescription(), true);
        repository.save(categoria);
        logger.info("Categoría registrada exitosamente: {}", categoria.getName());
        return new ResponseEntity<>(new Message(categoria, "Categoría registrada exitosamente", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    // Consultar todas las categorías
    @Transactional(readOnly = true)
    public ResponseEntity<Object> obtenerCategorias() {
        logger.info("Consultando todas las categorías.");
        List<Categoria> categorias = repository.findAll();
        logger.info("Número de categorías encontradas: {}", categorias.size());
        return new ResponseEntity<>(new Message(categorias, "Listado de categorías", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Consultar categorías activas
    @Transactional(readOnly = true)
    public ResponseEntity<Object> obtenerCategoriasActivas() {
        logger.info("Consultando categorías activas.");
        List<Categoria> categoriasActivas = repository.findByStatus(true);
        logger.info("Número de categorías activas encontradas: {}", categoriasActivas.size());
        return new ResponseEntity<>(new Message(categoriasActivas, "Listado de categorías activas", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Actualizar categoría
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> actualizarCategoria(CategoriaDTO dto) {
        logger.info("Iniciando actualización de categoría con id: {}", dto.getId());

        dto.setName(dto.getName().toLowerCase().trim());

        if (dto.getName().length() < 3) {
            logger.warn("El nombre de la categoría es muy corto para actualizar: {}", dto.getName());
            return new ResponseEntity<>(new Message("El nombre de la categoría no puede tener menos de 3 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Optional<Categoria> categoriaOpt = repository.findById(dto.getId());
        if (!categoriaOpt.isPresent()) {
            logger.warn("Categoría no encontrada con id: {}", dto.getId());
            return new ResponseEntity<>(new Message("Categoría no encontrada", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        if (repository.findByNameIgnoreCaseAndIdNot(dto.getName(), dto.getId()).isPresent()) {
            logger.warn("El nombre de la categoría ya existe para otro id: {}", dto.getName());
            return new ResponseEntity<>(new Message("El nombre de la categoría ya existe", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Categoria categoria = categoriaOpt.get();
        categoria.setName(dto.getName());
        categoria.setDescription(dto.getDescription());
        repository.save(categoria);
        logger.info("Categoría actualizada exitosamente: {}", categoria.getName());
        return new ResponseEntity<>(new Message(categoria, "Categoría actualizada exitosamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Cambiar estado de categoría (habilitar/deshabilitar)
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> cambiarEstadoCategoria(CategoriaDTO categoriaDTO) {
        logger.info("Iniciando cambio de estado para categoría con id: {}", categoriaDTO.getId());

        Optional<Categoria> categoriaOpt = repository.findById(categoriaDTO.getId());
        if (!categoriaOpt.isPresent()) {
            logger.warn("Categoría no encontrada con id: {}", categoriaDTO.getId());
            return new ResponseEntity<>(new Message("Categoría no encontrada", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        Categoria categoria = categoriaOpt.get();
        categoria.setStatus(!categoria.isStatus());
        repository.save(categoria);

        String estado = categoria.isStatus() ? "habilitada" : "deshabilitada";
        logger.info("Categoría {} exitosamente: {}", estado, categoria.getName());
        return new ResponseEntity<>(new Message(categoria, "Categoría " + estado + " exitosamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }
}
