package integradora.SIGI.producto.model;
import jakarta.persistence.*;
import jakarta.persistence.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;


    @Column(name = "cantidad", columnDefinition = "INT")
    private Integer cantidad;

    @Column(name = "precio_unitario", columnDefinition = "DECIMAL(10,2)")
    private Double precioUnitario;


    @Column(name = "status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean status = true;

    // Constructor por defecto
    public Producto() {
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

    // Método toString para depuración
    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", status=" + status +
                '}';
    }
}

