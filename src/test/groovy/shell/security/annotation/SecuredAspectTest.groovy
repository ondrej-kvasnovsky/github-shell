package shell.security.annotation

import com.blogspot.toomuchcoding.spock.subjcollabs.Collaborator
import com.blogspot.toomuchcoding.spock.subjcollabs.Subject
import org.aspectj.lang.ProceedingJoinPoint
import shell.security.NotAuthorizedException
import shell.security.session.Session
import shell.security.session.SessionHolder
import spock.lang.Specification

class SecuredAspectTest extends Specification {

    @Subject
    SecuredAspect securedAspect

    @Collaborator
    SessionHolder sessionHolder = Mock()

    def "Allows to perform a method when session exists"() {
        given:
        1 * sessionHolder.currentClientSession >> Optional.of(new Session("an-authToken"))
        ProceedingJoinPoint joinPoint = Mock()
        joinPoint.proceed() >> "Mocked Result"

        when:
        def result = securedAspect.secured(joinPoint)

        then:
        result == "Mocked Result"
    }

    def "Throws exception when session does not exist"() {
        given:
        1 * sessionHolder.currentClientSession >> Optional.empty()

        when:
        securedAspect.secured(null)

        then:
        thrown(NotAuthorizedException)
    }
}
