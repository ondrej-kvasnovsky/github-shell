package shell.features.repository.command.strategy;

import org.springframework.stereotype.Component;
import shell.features.pullrequest.service.PullRequestService;
import shell.features.repository.model.Repository;
import shell.features.repository.service.RepositoryService;
import shell.features.repository.service.TopReposService;

import java.util.List;

@Component
public class ContributionStrategy implements TopRepositoryStrategy {

    private final TopReposService topReposService;
    private final RepositoryService repositoryService;
    private final PullRequestService pullRequestService;

    public ContributionStrategy(TopReposService topReposService, RepositoryService repositoryService, PullRequestService pullRequestService) {
        this.topReposService = topReposService;
        this.repositoryService = repositoryService;
        this.pullRequestService = pullRequestService;
    }

    @Override
    public List<Repository> findTopRepositories(String organization, int howMany) {
        List<Repository> allRepos = repositoryService.findRepositories(organization);
        pullRequestService.addPullRequestCounts(organization, allRepos);
        return topReposService.getTopReposByContribution(allRepos, howMany);
    }
}
