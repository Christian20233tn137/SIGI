package integradora.SIGI.model;

import jakarta.persistence.*;
import org.hibernate.tool.schema.spi.SchemaTruncator;

@Entity
@Table(name="productos")
public class Usuarios {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column (name="name",columnDefinition="VARCHAR(30)")
    private String name;

    @Column(name="lastname",columnDefinition = "VARCHAR(30)")
    private String lastname;
    @Column(name="email",columnDefinition = "VARCHAR(30)")
    private String email;

    @Column(name="telephone",columnDefinition = "VARCHAR(10)")
    private String telephone;
    @Column(name="password",columnDefinition = "VARCHAR(16)")
    private String password;

    @Column(name="rol", columnDefinition = "VARCHAR(50)")
    private String rol;

    @Column(name="status",columnDefinition = "BOOL DEFAULT TRUE")
    private boolean status;

    public Usuarios(){

    }

    public Usuarios(String name, String lastname, String email, String telephone, String password, String rol, boolean status) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.telephone= telephone;
        this.password = password;
        this.rol=rol;
        this.status=status;
    }

    public Usuarios(Long id,String name, String lastname, String email, String telephone, String password, String rol, boolean status) {
        this.id=id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.telephone= telephone;
        this.password = password;
        this.rol=rol;
        this.status=status;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
