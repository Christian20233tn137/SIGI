package integradora.SIGI.usuarios.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioDTO {

    private Long id;
    @NotBlank(groups = {UsuarioDTO.Register.class, UsuarioDTO.Modify.class})
    private String name;

    @NotBlank(groups = {UsuarioDTO.Register.class, UsuarioDTO.Modify.class})
    private String lastname;

    @NotBlank(groups = {UsuarioDTO.Register.class, UsuarioDTO.Modify.class, FindByEmail.class,VerifyCode.class})
    private String email;

    @NotBlank(groups = {UsuarioDTO.Register.class, UsuarioDTO.Modify.class})
    private String telephone;

    @NotBlank(groups = {UsuarioDTO.Register.class, UsuarioDTO.Modify.class})
    private String password;

    @NotBlank(groups = {VerifyCode.class})
    private String code;

    @NotNull(groups = ChangeStatus.class, message = "Es necesario especificar el estado")
    private Boolean status; // Atributo para habilitar/deshabilitar categorías

    public UsuarioDTO() {
    }

    public UsuarioDTO(String name, String lastname, String email, String telephone, String password, String code, Boolean status) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.telephone = telephone;
        this.password = password;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public interface Register{}
    public interface Modify{}
    public interface ChangeStatus{}
    public interface FindByEmail {}
    public interface VerifyCode {}
}
