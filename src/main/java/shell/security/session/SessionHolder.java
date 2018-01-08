package shell.security.session;

import java.util.Optional;

/**
 * Manages current session. When user is authenticated, he needs to have a session in {@link SessionHolder}.
 */
public interface SessionHolder {

    void setCurrentSession(Session session);

    Optional<Session> getCurrentClientSession();

    void removeCurrentSession();
}
