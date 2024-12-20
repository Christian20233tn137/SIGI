package integradora.SIGI.security;

import integradora.SIGI.usuarios.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsuarioDetails implements UserDetails {

    private final Usuario usuario;

    public UsuarioDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(usuario.getRol().name()));
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Puedes adaptar este valor a tu lógica de negocio
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Puedes adaptar este valor a tu lógica de negocio
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Puedes adaptar este valor a tu lógica de negocio
    }

    @Override
    public boolean isEnabled() {
        return usuario.isStatus(); // Usa el estado del usuario
    }
}
