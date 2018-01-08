package shell.features.auth.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import shell.features.auth.service.AuthResponse;
import shell.features.auth.service.AuthService;
import shell.security.annotation.Secured;
import shell.security.session.Session;
import shell.security.session.SessionHolder;

import java.util.Optional;

@ShellComponent
public class AuthCommand {

    private final AuthService authService;
    private final SessionHolder sessionHolder;

    public AuthCommand(AuthService authService, SessionHolder sessionHolder) {
        this.authService = authService;
        this.sessionHolder = sessionHolder;
    }

    @ShellMethod(key = "login", value = "Login into GitHub. Usage: login <your login> <your password>")
    public String authenticate(@ShellOption String login,
                               @ShellOption String password) {
        Optional<AuthResponse> response = authService.authenticate(login, password);
        if (response.isPresent()) {
            AuthResponse authResponse = response.get();
            sessionHolder.setCurrentSession(new Session(authResponse.getAuthToken()));
            return "Success.";
        } else {
            return "Failed to authenticate.";
        }
    }

    @Secured
    @ShellMethod(key = "logout", value = "Logout from GitHub. Usage: logout")
    public void logout() {
        sessionHolder.removeCurrentSession();
    }
}
