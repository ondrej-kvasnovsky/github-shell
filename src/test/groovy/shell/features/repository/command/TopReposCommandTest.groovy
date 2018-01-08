package shell.features.repository.command

import com.blogspot.toomuchcoding.spock.subjcollabs.Collaborator
import com.blogspot.toomuchcoding.spock.subjcollabs.Subject
import shell.features.repository.OrderBy
import shell.features.repository.command.strategy.TopReposStrategyExecutor
import shell.features.repository.model.Repository
import spock.lang.Specification

class TopReposCommandTest extends Specification {

    @Subject
    TopReposCommand topReposCommand

    @Collaborator
    TopReposStrategyExecutor topReposStrategyExecutor = Mock()

    def "uses strategy to find top repositories"() {
        given:
        topReposStrategyExecutor.execute(*_) >> [new Repository(fullName: "full/name", forks: 5, stargazersCount: 10)]

        when:
        String result = topReposCommand.topRepos(5, OrderBy.stars)

        then:
        result == """┌───────────────┬─────┬─────┐
│Repository Name│Stars│Forks│
├───────────────┼─────┼─────┤
│full/name      │10   │5    │
└───────────────┴─────┴─────┘
"""
    }
}
