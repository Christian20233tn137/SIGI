package integradora.SIGI.producto.model;

import jakarta.validation.constraints.*;

public class ProductoDTO {

    private Long id;
    @NotBlank(groups = {Register.class, Modify.class}, message = "El nombre del producto es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;

    @NotNull(groups = {Register.class, Modify.class}, message = "La cantidad es obligatoria")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private Integer cantidad;

    @NotNull(groups = {Register.class, Modify.class}, message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio unitario debe ser mayor a 0")
    private Double precioUnitario;

    private boolean status;

    // Interfaces para validaciones
    public interface Register {}
    public interface Modify {}
    public interface ChangeStatus {}
    public interface UpdateStock {}

    // Constructores
    public ProductoDTO() {}

    public ProductoDTO(String nombre, Integer cantidad, Double precioUnitario, boolean status) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.status = status;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
