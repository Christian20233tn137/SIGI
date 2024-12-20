package integradora.SIGI.proveedores.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import integradora.SIGI.producto.model.Producto;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="proveedores")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name",columnDefinition = "VARCHAR(50)")
    private String name;
    @Column(name="rfc", columnDefinition = "VARCHAR(13)")
    private String rfc;

    @Column(name="direccion",columnDefinition = "VARCHAR(100)")
    private String direccion;

    @Column(name="telefono",columnDefinition = "VARCHAR(10)")
    private String telefono;

    @Column(name="email",columnDefinition = "VARCHAR(20)")
    private String email;

    @Column(name="status",columnDefinition = "BOOL DEFAULT TRUE")
    private boolean status;

    @ManyToMany(mappedBy = "proveedores")
    @JsonIgnore
    private List<Producto> productos;

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public Proveedor(){

    }

    public Proveedor(String name,String rfc, String direccion, String telefono, String email, boolean status) {
        this.name = name;
        this.rfc = rfc;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.status = status;
    }

    public Proveedor(Long id, String name, String rfc, String direccion, String telefono, String email, boolean status) {
        this.id = id;
        this.name = name;
        this.rfc = rfc;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.status = status;
    }

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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
