package shell.security.session;

/**
 * Session is created after user is successfully authenticated.
 */
public class Session {

    private final String authToken;

    public Session(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }
}
