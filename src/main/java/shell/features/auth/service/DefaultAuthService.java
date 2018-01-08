package shell.features.auth.service;

import org.springframework.stereotype.Service;
import shell.features.auth.client.GitHubAuthClient;
import shell.rest.RestClient;

import java.util.Base64;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class DefaultAuthService implements AuthService {

    private final RestClient restClient;

    public DefaultAuthService(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public Optional<AuthResponse> authenticate(String login, String password) {
        String authToken = encodeAuthToken(login, password);
        GitHubAuthClient client = restClient.createClient(GitHubAuthClient.class);
        try {
            client.authenticate(authToken);
            return Optional.of(new AuthResponse(authToken));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private String encodeAuthToken(String login, String password) {
        byte[] toEncode = (login + ":" + password).getBytes(UTF_8);
        return new String(Base64.getEncoder().encode(toEncode), UTF_8);
    }
}
