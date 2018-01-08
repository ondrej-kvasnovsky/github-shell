package shell.features.auth.service;

import java.util.Optional;

public interface AuthService {

    Optional<AuthResponse> authenticate(String login, String password);
}
