package shell.features.auth.command

import com.blogspot.toomuchcoding.spock.subjcollabs.Collaborator
import com.blogspot.toomuchcoding.spock.subjcollabs.Subject
import shell.features.auth.service.AuthResponse
import shell.features.auth.service.AuthService
import shell.security.session.DefaultSessionHolder
import shell.security.session.Session
import shell.security.session.SessionHolder
import spock.lang.Specification

class AuthCommandTest extends Specification {

    @Subject
    AuthCommand authCommand

    @Collaborator
    AuthService authService = Mock()

    @Collaborator
    SessionHolder sessionHolder = new DefaultSessionHolder()

    def "successfully authenticates a user"() {
        given:
        1 * authService.authenticate("dummy-login", "dummy-password") >> Optional.of(new AuthResponse("dummy-authToken"))

        when:
        String result = authCommand.authenticate("dummy-login", "dummy-password")

        then:
        result == "Success."

        then:
        sessionHolder.currentClientSession.isPresent()
    }

    def "fails to authenticate a user"() {
        given:
        1 * authService.authenticate("dummy-login", "dummy-password") >> Optional.empty()

        when:
        String result = authCommand.authenticate("dummy-login", "dummy-password")

        then:
        result == "Failed to authenticate."

        then:
        !sessionHolder.currentClientSession.isPresent()
    }

    def "user is loged out"() {
        given:
        sessionHolder.setCurrentSession(new Session("dummy-authToken"))

        when:
        authCommand.logout()

        then:
        !sessionHolder.currentClientSession.isPresent()
    }
}
