package integradora.SIGI.usuarios.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioDTO {
    @NotNull(groups = {UsuarioDTO.Modify.class, UsuarioDTO.ChangeStatus.class})
    private Long id;

    @NotBlank(groups = {UsuarioDTO.Register.class, UsuarioDTO.Modify.class})
    private String name;

    @NotBlank(groups = {UsuarioDTO.Register.class, UsuarioDTO.Modify.class})
    private String lastname;

    @NotBlank(groups = {UsuarioDTO.Register.class, UsuarioDTO.Modify.class})
    private String email;

    @NotBlank(groups = {UsuarioDTO.Register.class, UsuarioDTO.Modify.class})
    private String telephone;

    @NotBlank(groups = {UsuarioDTO.Register.class, UsuarioDTO.Modify.class})
    private String password;

    @NotBlank(groups = {UsuarioDTO.Register.class, UsuarioDTO.Modify.class})
    private String rol;

    @NotBlank(groups = {VerifyCode.class})
    private String code;

    public UsuarioDTO() {
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public interface Register{}
    public interface Modify{}
    public interface ChangeStatus{}
    public interface FindByEmail {}
    public interface VerifyCode {}
}
