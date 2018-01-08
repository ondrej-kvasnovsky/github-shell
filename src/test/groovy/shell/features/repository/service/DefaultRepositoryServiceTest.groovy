package shell.features.repository.service

import com.blogspot.toomuchcoding.spock.subjcollabs.Collaborator
import com.blogspot.toomuchcoding.spock.subjcollabs.Subject
import shell.features.repository.client.RepositoryClient
import shell.features.repository.client.TopRepositoryClient
import shell.features.repository.model.Repository
import shell.features.repository.model.SearchRepositoryResponse
import shell.rest.RestClient
import shell.security.session.Session
import shell.security.session.SessionHolder
import spock.lang.Specification

class DefaultRepositoryServiceTest extends Specification {

    @Subject
    DefaultRepositoryService repositoryService

    @Collaborator
    RestClient restClient = Mock()

    @Collaborator
    SessionHolder sessionHolder = Mock()

    def setup() {
        sessionHolder.currentClientSession >> Optional.of(new Session("an-authToken"))
    }

    def "finds all 150 repositories and an organization"() {
        given:
        SearchRepositoryResponse response1 = new SearchRepositoryResponse(totalCount: 150, items: [])
        (0..99).forEach { response1.items << new Repository() }
        SearchRepositoryResponse response2 = new SearchRepositoryResponse(totalCount: 150, items: [])
        (0..49).forEach { response2.items << new Repository() }

        RepositoryClient client = Mock()
        1 * client.paginateRepositories("org:an-organization fork:true", 1, 100, "an-authToken") >> response1
        1 * client.paginateRepositories("org:an-organization fork:true", 2, 100, "an-authToken") >> response2
        restClient.createClient(RepositoryClient) >> client

        when:
        List<Repository> repositories = repositoryService.findRepositories("an-organization")

        then:
        repositories.size() == 150
    }

    def "finds top 5 repositories"() {
        given:
        SearchRepositoryResponse response = new SearchRepositoryResponse(totalCount: 300, items: [])
        (0..4).forEach { response.items << new Repository() }

        TopRepositoryClient client = Mock()
        1 * client.findTopRepositories("org:an-organization fork:true", "stars", "desc", 5, "an-authToken") >> response
        restClient.createClient(TopRepositoryClient) >> client

        when:
        List<Repository> repositories = repositoryService.findTopRepos("an-organization", 5, "stars")

        then:
        repositories.size() == 5
    }
}
