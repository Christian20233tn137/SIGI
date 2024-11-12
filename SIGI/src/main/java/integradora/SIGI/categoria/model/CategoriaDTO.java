package integradora.SIGI.categoria.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CategoriaDTO {

    @NotNull(groups = {Modify.class, ChangeStatus.class}, message = "Es necesario el id")
    private Long id;

    @NotBlank(groups = {Modify.class, Register.class}, message = "Es necesario el nombre")
    @Size(min = 3, max = 30, message = "El nombre debe tener entre 3 y 30 caracteres")
    private String name;

    @Size(max = 50, message = "La descripción no puede tener más de 50 caracteres")
    private String description;

    // Interfaz para validaciones en el registro
    public interface Register {}

    // Interfaz para validaciones en la modificación
    public interface Modify {}

    // Interfaz para validaciones al cambiar el estado
    public interface ChangeStatus {}

    // Constructores
    public CategoriaDTO() {}

    public CategoriaDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
