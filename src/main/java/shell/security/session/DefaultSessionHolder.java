package shell.security.session;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultSessionHolder implements SessionHolder {

    private Session session;

    @Override
    public void setCurrentSession(Session session) {
        this.session = session;
    }

    @Override
    public Optional<Session> getCurrentClientSession() {
        return Optional.ofNullable(session);
    }

    @Override
    public void removeCurrentSession() {
        session = null;
    }
}
