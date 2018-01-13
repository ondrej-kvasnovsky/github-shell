package shell.features.repository.command.strategy

import com.blogspot.toomuchcoding.spock.subjcollabs.Collaborator
import com.blogspot.toomuchcoding.spock.subjcollabs.Subject
import shell.features.pullrequest.service.PullRequestService
import shell.features.repository.OrderBy
import shell.features.repository.model.Repository
import shell.features.repository.service.RepositoryService
import shell.features.repository.service.TopReposService
import spock.lang.Specification
import spock.lang.Unroll

class PullRequestsStrategyTest extends Specification {

    @Subject
    PullRequestsStrategy pullRequestsStrategy

    @Collaborator
    TopReposService topReposService = Mock()

    @Collaborator
    RepositoryService repositoryService = Mock()

    @Collaborator
    PullRequestService pullRequestService = Mock()

    def "uses repositoryService to find top repositories"() {
        given:
        String organization = "an-organization"
        List<Repository> repositories = [new Repository()]

        when:
        pullRequestsStrategy.findTopRepositories(organization, 10)

        then:
        1 * repositoryService.findRepositories(organization) >> repositories
        1 * pullRequestService.addPullRequestCounts(organization, repositories)
        1 * topReposService.getTopReposByPullRequests(repositories, 10)
    }
}
