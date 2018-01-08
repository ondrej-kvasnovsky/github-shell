package shell.features.auth.service

import com.blogspot.toomuchcoding.spock.subjcollabs.Collaborator
import com.blogspot.toomuchcoding.spock.subjcollabs.Subject
import shell.features.auth.client.GitHubAuthClient
import shell.rest.RestClient
import spock.lang.Specification

class DefaultAuthServiceTest extends Specification {

    @Subject
    DefaultAuthService authService

    @Collaborator
    RestClient restClient = Mock()

    def "successfully authenticates user"() {
        given:
        restClient.createClient(GitHubAuthClient) >> new GitHubAuthClient() {
            void authenticate(String authToken) {
                // if  200 HTTP status is received, no exception is thrown
            }
        }

        when:
        Optional<AuthResponse> response = authService.authenticate("a-login", "a-password")

        then:
        response.isPresent()
    }

    def "fails authenticates user when other than "() {
        given:
        restClient.createClient(GitHubAuthClient) >> new GitHubAuthClient() {
            void authenticate(String authToken) {
                throw new RuntimeException("Bad credentials")
            }
        }

        when:
        Optional<AuthResponse> response = authService.authenticate("a-login", "a-password")

        then:
        !response.isPresent()
    }
}
