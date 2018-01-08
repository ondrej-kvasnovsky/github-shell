package shell.features.repository.service

import com.blogspot.toomuchcoding.spock.subjcollabs.Subject
import shell.features.repository.RepositoryBuilder
import shell.features.repository.model.Repository
import spock.lang.Specification

class DefaultTopReposServiceTest extends Specification {

    @Subject
    DefaultTopReposService topReposService

    def "returns top 3 repositories sorted by pull requests"() {
        given:
        List<Repository> repositories = createRepositories()

        when:
        List<Repository> repos = topReposService.getTopReposByPullRequests(repositories, 3)

        then:
        repos.size() == 3

        then:
        repos[0].pullRequests == 4
        repos[1].pullRequests == 2
        repos[2].pullRequests == 1
    }

    def "returns top 2 repositories when only 2 are available but 3 requested"() {
        given:
        List<Repository> repositories = createRepositories().subList(0, 2)

        when:
        List<Repository> repos = topReposService.getTopReposByPullRequests(repositories, 3)

        then:
        repos.size() == 2

        then:
        repos[0].pullRequests == 4
        repos[1].pullRequests == 0
    }

    def "returns top 2 repositories when only 2 are available and 2 are requested"() {
        given:
        List<Repository> repositories = createRepositories().subList(0, 2)

        when:
        List<Repository> repos = topReposService.getTopReposByPullRequests(repositories, 2)

        then:
        repos.size() == 2

        then:
        repos[0].pullRequests == 4
        repos[1].pullRequests == 0
    }

    def "returns top 3 repositories sorted by contribution percentage"() {
        given:
        List<Repository> repositories = createRepositories()

        when:
        List<Repository> repos = topReposService.getTopReposByContribution(repositories, 3)

        then:
        repos.size() == 3

        then:
        repos[0].contributionPercentage == 2.0d
        repos[1].contributionPercentage == 2.0d
        repos[2].contributionPercentage == 0.25d
    }

    private List<Repository> createRepositories() {
        [new RepositoryBuilder().withPullRequests(0).withForks(1).build(),
         new RepositoryBuilder().withPullRequests(4).withForks(2).build(),
         new RepositoryBuilder().withPullRequests(2).withForks(1).build(),
         new RepositoryBuilder().withPullRequests(1).withForks(4).build()]
    }
}
