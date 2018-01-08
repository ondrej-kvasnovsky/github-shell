package shell.features.pullrequest.service

import com.blogspot.toomuchcoding.spock.subjcollabs.Collaborator
import com.blogspot.toomuchcoding.spock.subjcollabs.Subject
import shell.features.pullrequest.client.PullRequestClient
import shell.features.repository.model.Repository
import shell.rest.RestClient
import shell.security.session.Session
import shell.security.session.SessionHolder
import spock.lang.Specification

class DefaultPullRequestServiceTest extends Specification {

    @Subject
    DefaultPullRequestService pullRequestService

    @Collaborator
    RestClient restClient = Mock()

    @Collaborator
    SessionHolder sessionHolder = Mock()

    def setup() {
        sessionHolder.currentClientSession >> Optional.of(new Session("an-authToken"))
    }

    def "finds number of pull requests for a organization and its repository from headers"() {
        given:
        PullRequestClient client = Mock()
        1 * restClient.createClient(PullRequestClient, _) >> client

        when:
        pullRequestService.findNumberOfPullRequest('an-organization', 'a-repository')

        then:
        1 * client.findPullRequest("an-organization", "a-repository", "an-authToken")
    }

    def "finds and adds number of pull requests to all repositories from headers"() {
        given:
        PullRequestClient client = Mock()
        1 * restClient.createClient(PullRequestClient, _) >> client

        List<Repository> repositories = [new Repository(name: 'a-repository', forks: 10)]

        when:
        pullRequestService.addPullRequestCounts('an-organization', repositories)

        then:
        1 * client.findPullRequest("an-organization", "a-repository", "an-authToken")
    }
}
