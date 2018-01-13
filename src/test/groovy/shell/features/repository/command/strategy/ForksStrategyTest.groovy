package shell.features.repository.command.strategy

import com.blogspot.toomuchcoding.spock.subjcollabs.Collaborator
import com.blogspot.toomuchcoding.spock.subjcollabs.Subject
import shell.features.repository.OrderBy
import shell.features.repository.service.RepositoryService
import spock.lang.Specification

class ForksStrategyTest extends Specification {

    @Subject
    ForksStrategy forksStrategy

    @Collaborator
    RepositoryService repositoryService = Mock()

    def "uses repositoryService to find top repositories"() {
        when:
        forksStrategy.findTopRepositories("an-organization", 10)

        then:
        1 * repositoryService.findTopRepos("an-organization", 10, OrderBy.forks.name())
    }
}
