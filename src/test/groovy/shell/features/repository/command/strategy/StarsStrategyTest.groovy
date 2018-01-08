package shell.features.repository.command.strategy

import com.blogspot.toomuchcoding.spock.subjcollabs.Collaborator
import com.blogspot.toomuchcoding.spock.subjcollabs.Subject
import shell.features.repository.OrderBy
import shell.features.repository.service.RepositoryService
import spock.lang.Specification
import spock.lang.Unroll

class StarsStrategyTest extends Specification {

    @Subject
    StarsStrategy starsStrategy

    @Collaborator
    RepositoryService repositoryService = Mock()

    def "accepts when orderBy is 'stars'"() {
        when:
        boolean accepts = starsStrategy.accept(OrderBy.stars)

        then:
        accepts
    }

    @Unroll
    def "refuses when orderBy is '#orderBy'"() {
        when:
        boolean accepts = starsStrategy.accept(orderBy)

        then:
        !accepts

        where:
        orderBy << [OrderBy.contribution, OrderBy.forks, OrderBy.pullRequests]
    }

    def "uses repositoryService to find top repositories"() {
        when:
        starsStrategy.findTopRepositories("an-organization", 10)

        then:
        1 * repositoryService.findTopRepos("an-organization", 10, OrderBy.stars.name())
    }
}
