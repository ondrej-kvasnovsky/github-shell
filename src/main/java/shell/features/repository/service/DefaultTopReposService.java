package shell.features.repository.service;

import org.springframework.stereotype.Service;
import shell.features.repository.model.Repository;

import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.compare;

@Service
public class DefaultTopReposService implements TopReposService {

    @Override
    public List<Repository> getTopReposByPullRequests(List<Repository> repositories, int howMany) {
        repositories.sort((repo1, repo2) -> {
            int pullRequests1 = repo1.getPullRequests();
            int pullRequests2 = repo2.getPullRequests();
            return compare(pullRequests2, pullRequests1);
        });
        return getTopRepositories(repositories, howMany);
    }

    @Override
    public List<Repository> getTopReposByContribution(List<Repository> repositories, int howMany) {
        repositories.sort((repo1, repo2) -> {
            double contribution1 = repo1.getContributionPercentage();
            double contribution2 = repo2.getContributionPercentage();
            return compare(contribution2, contribution1);
        });
        return getTopRepositories(repositories, howMany);
    }

    private List<Repository> getTopRepositories(List<Repository> repositories, int howMany) {
        if (repositories.size() < howMany) {
            return repositories;
        }
        return repositories.subList(0, howMany);
    }
}
