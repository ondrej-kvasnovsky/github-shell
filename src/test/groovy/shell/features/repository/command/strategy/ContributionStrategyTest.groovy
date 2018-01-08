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

class ContributionStrategyTest extends Specification {

    @Subject
    ContributionStrategy contributionStrategy

    @Collaborator
    TopReposService topReposService = Mock()

    @Collaborator
    RepositoryService repositoryService = Mock()

    @Collaborator
    PullRequestService pullRequestService = Mock()

    def "accepts when orderBy is 'contribution'"() {
        when:
        boolean accepts = contributionStrategy.accept(OrderBy.contribution)

        then:
        accepts
    }

    @Unroll
    def "refuses when orderBy is '#orderBy'"() {
        when:
        boolean accepts = contributionStrategy.accept(orderBy)

        then:
        !accepts

        where:
        orderBy << [OrderBy.stars, OrderBy.forks, OrderBy.pullRequests]
    }

    def "uses repositoryService to find top repositories"() {
        given:
        String organization = "an-organization"
        List<Repository> repositories = [new Repository()]

        when:
        contributionStrategy.findTopRepositories(organization, 10)

        then:
        1 * repositoryService.findRepositories(organization) >> repositories
        1 * pullRequestService.addPullRequestCounts(organization, repositories)
        1 * topReposService.getTopReposByContribution(repositories, 10)
    }
}