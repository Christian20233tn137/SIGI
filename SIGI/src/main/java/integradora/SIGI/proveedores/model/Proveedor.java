package integradora.SIGI.proveedores.model;

import jakarta.persistence.*;

@Entity
@Table(name="proveedores")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="rfc", columnDefinition = "VARCHAR(13)")
    private String rfc;

    @Column(name="direccion",columnDefinition = "VARVCHAR(100)")
    private String direccion;

    @Column(name="telefono",columnDefinition = "VARCHAR(10)")
    private String telefono;

    @Column(name="email",columnDefinition = "VARCHAR(20)")
    private String email;

    @Column(name="status",columnDefinition = "BOOL DEFAULT TRUE")
    private boolean status;

    public Proveedor(){

    }

    public Proveedor(String rfc, String direccion, String telefono, String email, boolean status) {
        this.rfc = rfc;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.status = status;
    }

    public Proveedor(Long id, String rfc, String direccion, String telefono, String email, boolean status) {
        this.id = id;
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
