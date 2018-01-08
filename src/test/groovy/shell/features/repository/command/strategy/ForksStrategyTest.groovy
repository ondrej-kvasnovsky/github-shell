package shell.features.repository.command.strategy

import com.blogspot.toomuchcoding.spock.subjcollabs.Collaborator
import com.blogspot.toomuchcoding.spock.subjcollabs.Subject
import shell.features.repository.OrderBy
import shell.features.repository.service.RepositoryService
import spock.lang.Specification
import spock.lang.Unroll

class ForksStrategyTest extends Specification {

    @Subject
    ForksStrategy forksStrategy

    @Collaborator
    RepositoryService repositoryService = Mock()

    def "accepts when orderBy is 'forks'"() {
        when:
        boolean accepts = forksStrategy.accept(OrderBy.forks)

        then:
        accepts
    }

    @Unroll
    def "refuses when orderBy is '#orderBy'"() {
        when:
        boolean accepts = forksStrategy.accept(orderBy)

        then:
        !accepts

        where:
        orderBy << [OrderBy.stars, OrderBy.contribution, OrderBy.pullRequests]
    }

    def "uses repositoryService to find top repositories"() {
        when:
        forksStrategy.findTopRepositories("an-organization", 10)

        then:
        1 * repositoryService.findTopRepos("an-organization", 10, OrderBy.forks.name())
    }
}
