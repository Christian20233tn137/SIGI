package integradora.SIGI.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
<<<<<<< HEAD
=======
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
<<<<<<< HEAD
                        .requestMatchers("/login").permitAll() // Permitir acceso a login y registro
                        .requestMatchers("/categorias").hasAuthority("ROLE_TOWN_ACCESS") // Roles específicos
                        .requestMatchers("/usuario").hasAuthority("ROLE_STATE_ACCESS")
                        .anyRequest().authenticated() // Requiere autenticación para el resto
=======
                        .requestMatchers("/auth" , "/usuario").permitAll() // Endpoints públicos
                        .anyRequest().permitAll()
>>>>>>> chris
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sin sesiones
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Agregar el filtro JWT

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
>>>>>>> 23fbd5fcaebf3dc974710f04662f0bfdc129be81
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Deshabilitar CSRF
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Habilitar CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll()

                        // USUARIOS
                        .requestMatchers("/usuario").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/usuario/all").hasAuthority("ROLE_ADMIN")

                        // USUARIOS/CONSULTOR
                        .requestMatchers("/usuario/all").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/usuario/{id}").hasAuthority("ROLE_CONSULTOR")

                        // CATEGORIAS
                        .requestMatchers("/categorias/**").hasAuthority("ROLE_ADMIN")

                        // CATEGORIAS/CONSULTOR
                        .requestMatchers("/categorias/all").hasAuthority("ROLE_CONSULTOR")
                        .requestMatchers("/categorias/activas").hasAuthority("ROLE_CONSULTOR")

                        // PRODUCTOS
                        .requestMatchers("/producto/**").hasAuthority("ROLE_ADMIN")

                        // PRODUCTOS/CONSULTOR
                        .requestMatchers("/producto/all").hasAuthority("ROLE_CONSULTOR")
                        .requestMatchers("/producto/activos").hasAuthority("ROLE_CONSULTOR")

                        // PROVEEDORES
                        .requestMatchers("/proveedor/**").hasAuthority("ROLE_ADMIN")

                        // PROVEEDORES/CONSULTOR
                        .requestMatchers("/proveedor/all").hasAuthority("ROLE_CONSULTOR")
                        .requestMatchers("/proveedor/activos").hasAuthority("ROLE_CONSULTOR")

                        .requestMatchers("/usuario/send-email/**").permitAll()
                        .requestMatchers("/usuario/verify-code/**").permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Agregar filtro JWT
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://127.0.0.1:5501", "http://localhost:5501")); // Orígenes permitidos
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")); // Métodos permitidos
        configuration.setAllowedHeaders(List.of("*")); // Permitir todos los encabezados
        configuration.setAllowCredentials(true); // Permitir credenciales como cookies o tokens

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
