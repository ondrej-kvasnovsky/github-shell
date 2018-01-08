package shell.features.auth.service;

public class AuthResponse {

    private final String authToken;

    public AuthResponse(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }
}
