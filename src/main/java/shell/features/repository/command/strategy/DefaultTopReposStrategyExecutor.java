package shell.features.repository.command.strategy;

import org.springframework.stereotype.Component;
import shell.features.repository.OrderBy;
import shell.features.repository.model.Repository;

import java.util.Collections;
import java.util.List;

@Component
public class DefaultTopReposStrategyExecutor implements TopReposStrategyExecutor {

    private final TopRepositoryStrategy starsStrategy;
    private final TopRepositoryStrategy forksStrategy;
    private final TopRepositoryStrategy pullRequestsStrategy;
    private final TopRepositoryStrategy contributionStrategy;

    public DefaultTopReposStrategyExecutor(TopRepositoryStrategy starsStrategy,
                                           TopRepositoryStrategy forksStrategy,
                                           TopRepositoryStrategy pullRequestsStrategy,
                                           TopRepositoryStrategy contributionStrategy) {
        this.starsStrategy = starsStrategy;
        this.forksStrategy = forksStrategy;
        this.pullRequestsStrategy = pullRequestsStrategy;
        this.contributionStrategy = contributionStrategy;
    }

    @Override
    public List<Repository> execute(String organization, int howMany, OrderBy orderBy) {
        TopRepositoryStrategy[] strategies = {starsStrategy, forksStrategy, pullRequestsStrategy, contributionStrategy};
        for (TopRepositoryStrategy topRepositoryStrategy : strategies) {
            if (topRepositoryStrategy.accept(orderBy)) {
                return topRepositoryStrategy.findTopRepositories(organization, howMany);
            }
        }
        return Collections.emptyList();
    }
}
