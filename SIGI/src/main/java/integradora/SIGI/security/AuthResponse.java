package integradora.SIGI.security;

public class AuthResponse {
    private String jwt;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }

    // Getter

    public String getJwt() {
        return jwt;
    }
}