package shell.features.repository.service;

import shell.features.repository.model.Repository;

import java.util.List;

public interface TopReposService {

    List<Repository> getTopReposByPullRequests(List<Repository> repositories, int howMany);

    List<Repository> getTopReposByContribution(List<Repository> repositories, int howMany);
}
