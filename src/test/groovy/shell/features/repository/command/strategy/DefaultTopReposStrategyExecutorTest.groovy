package shell.features.repository.command.strategy

import com.blogspot.toomuchcoding.spock.subjcollabs.Collaborator
import com.blogspot.toomuchcoding.spock.subjcollabs.Subject
import shell.features.repository.OrderBy
import shell.features.repository.model.Repository
import spock.lang.Specification

class DefaultTopReposStrategyExecutorTest extends Specification {

    @Subject
    DefaultTopReposStrategyExecutor topReposStrategyExecutor

    @Collaborator
    TopRepositoryStrategy starsStrategy = Mock()

    @Collaborator
    TopRepositoryStrategy forksStrategy = Mock()

    @Collaborator
    TopRepositoryStrategy pullRequestsStrategy = Mock()

    @Collaborator
    TopRepositoryStrategy contributionStrategy = Mock()

    def "uses a strategy if the strategy accepts"() {
        given:
        starsStrategy.accept(OrderBy.stars) >> true
        List<Repository> expectedRepositories = [new Repository()]
        starsStrategy.findTopRepositories("an-organization", 5) >> expectedRepositories

        when:
        List<Repository> repositories = topReposStrategyExecutor.execute("an-organization", 5, OrderBy.stars)

        then:
        repositories == expectedRepositories
    }
}
