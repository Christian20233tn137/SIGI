package integradora.SIGI.categoria.control;

import integradora.SIGI.categoria.model.CategoriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> obtenerCategorias() {
        return categoriaService.obtenerCategorias();
    }

    @GetMapping("/activas")
    public ResponseEntity<Object> obtenerCategoriasActivas() {
        return categoriaService.obtenerCategoriasActivas();
    }

    @PostMapping
    public ResponseEntity<Object> registrarCategoria(@Validated(CategoriaDTO.Register.class) @RequestBody CategoriaDTO categoriaDTO) {
        return categoriaService.registrarCategoria(categoriaDTO);
    }

    @PutMapping
    public ResponseEntity<Object> actualizarCategoria(@Validated(CategoriaDTO.Modify.class) @RequestBody CategoriaDTO categoriaDTO) {
        return categoriaService.actualizarCategoria(categoriaDTO);
    }

    @PutMapping("/cambiar-estado")
    public ResponseEntity<Object> cambiarEstadoCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        return categoriaService.cambiarEstadoCategoria(categoriaDTO);
    }

}
