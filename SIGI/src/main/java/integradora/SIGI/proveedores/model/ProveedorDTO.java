package integradora.SIGI.proveedores.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProveedorDTO {

    @NotNull(groups = {Modify.class, ChangeStatus.class}, message = "Es necesario el id")
    private Long id;

    @NotBlank(groups = {Modify.class, Register.class}, message = "Es necesario el RFC")
    @Pattern(regexp = "^[A-ZÑ&]{3,4}\\d{6}[A-Z\\d]{3}$", message = "El RFC debe ser válido")
    private String rfc;

    @NotBlank(groups = {Modify.class, Register.class}, message = "Es necesaria la dirección")
    @Size(max = 100, message = "La dirección no puede tener más de 100 caracteres")
    private String direccion;

    @NotBlank(groups = {Modify.class, Register.class}, message = "Es necesario el teléfono")
    @Pattern(regexp = "^\\d{10}$", message = "El teléfono debe contener 10 dígitos")
    private String telefono;

    @NotBlank(groups = {Modify.class, Register.class}, message = "Es necesario el correo electrónico")
    @Email(message = "El correo electrónico debe ser válido")
    @Size(max = 20, message = "El correo no puede tener más de 20 caracteres")
    private String email;

    @NotNull(groups = {Modify.class, ChangeStatus.class}, message = "Es necesario el estado")
    private Boolean status;

    // Interfaz para validaciones en el registro
    public interface Register {}

    // Interfaz para validaciones en la modificación
    public interface Modify {}

    // Interfaz para validaciones al cambiar el estado
    public interface ChangeStatus {}

    // Constructores
    public ProveedorDTO() {}

    public ProveedorDTO(Long id, String rfc, String direccion, String telefono, String email, Boolean status) {
        this.id = id;
        this.rfc = rfc;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.status = status;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}

