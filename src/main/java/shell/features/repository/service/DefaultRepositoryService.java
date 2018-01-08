package shell.features.repository.service;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import shell.features.repository.client.RepositoryClient;
import shell.features.repository.client.TopRepositoryClient;
import shell.features.repository.model.Repository;
import shell.features.repository.model.SearchRepositoryResponse;
import shell.rest.RestClient;
import shell.security.session.SessionHolder;

import java.util.List;

@Component
public class DefaultRepositoryService implements RepositoryService {

    private final RestClient restClient;
    private final SessionHolder sessionHolder;

    public DefaultRepositoryService(RestClient restClient, SessionHolder sessionHolder) {
        this.restClient = restClient;
        this.sessionHolder = sessionHolder;
    }

    @Override
    public List<Repository> findRepositories(String organization) {
        String query = String.format("org:%s fork:true", organization);
        int currentPage = 1;
        int perPage = 100;
        String authToken = sessionHolder.getCurrentClientSession().get().getAuthToken();
        RepositoryClient client = restClient.createClient(RepositoryClient.class);
        SearchRepositoryResponse response = client.paginateRepositories(query, currentPage, perPage, authToken);

        List<Repository> repositories = response.getItems();
        int remainingRepos = response.getTotalCount() - perPage;
        while (remainingRepos > 0) {
            currentPage++;
            SearchRepositoryResponse nextResponse = client.paginateRepositories(query, currentPage, perPage, authToken);
            List<Repository> items = nextResponse.getItems();
            if (!CollectionUtils.isEmpty(items)) {
                repositories.addAll(items);
                remainingRepos = remainingRepos - items.size();
            }
        }
        return repositories;
    }

    @Override
    public List<Repository> findTopRepos(String organization, int howMany, String sortBy) {
        String query = String.format("org:%s fork:true", organization);
        String authToken = sessionHolder.getCurrentClientSession().get().getAuthToken();
        TopRepositoryClient client = restClient.createClient(TopRepositoryClient.class);
        SearchRepositoryResponse response = client.findTopRepositories(query, sortBy, "desc", howMany, authToken);
        return response.getItems();
    }
}
