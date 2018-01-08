package shell.features.repository.service;

import shell.features.repository.model.Repository;

import java.util.List;

public interface RepositoryService {

    List<Repository> findRepositories(String organization);

    List<Repository> findTopRepos(String organization, int howMany, String sortBy);
}
