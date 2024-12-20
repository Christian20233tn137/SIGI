package integradora.SIGI.usuarios.model;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name="usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column (name="name",columnDefinition="VARCHAR(30)")
    private String name;

    @Column(name="lastname",columnDefinition = "VARCHAR(30)")
    private String lastname;

    @Column(nullable = false, unique = true, name="email",columnDefinition = "VARCHAR(30)")
    private String email;

    @Column(name="telephone",columnDefinition = "VARCHAR(10)")
    private String telephone;

    @Column(nullable = false, name="password",columnDefinition = "VARCHAR(255)") // Increased password length
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", columnDefinition = "VARCHAR(50)", nullable = false)
    private Rol rol;

    @Column(name="status",columnDefinition = "BOOL DEFAULT TRUE")
    private boolean status;

    @Column(name = "code", columnDefinition = "VARCHAR(10)")
    private String code;

    public Usuario(){

    }

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public Usuario(Long id, String name, String lastname, String email, String telephone, String password, Rol rol, boolean status) {
        this.id=id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.telephone= telephone;
        this.password = password;
        this.rol=rol;
        this.status=status;
    }

    public Usuario(String name, String lastname, String email, String telephone, String password, Rol rol, boolean status) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.telephone = telephone;
        this.password = password;
        this.rol = rol;
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

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
