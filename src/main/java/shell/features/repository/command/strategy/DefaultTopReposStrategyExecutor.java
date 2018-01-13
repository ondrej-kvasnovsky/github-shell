package shell.features.repository.command.strategy;

import org.springframework.stereotype.Component;
import shell.features.repository.OrderBy;
import shell.features.repository.model.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DefaultTopReposStrategyExecutor implements TopReposStrategyExecutor {

    private final Map<OrderBy, TopRepositoryStrategy> strategies = new HashMap<>();

    public DefaultTopReposStrategyExecutor(TopRepositoryStrategy starsStrategy,
                                           TopRepositoryStrategy forksStrategy,
                                           TopRepositoryStrategy pullRequestsStrategy,
                                           TopRepositoryStrategy contributionStrategy) {
        strategies.put(OrderBy.stars, starsStrategy);
        strategies.put(OrderBy.forks, forksStrategy);
        strategies.put(OrderBy.pullRequests, pullRequestsStrategy);
        strategies.put(OrderBy.contribution, contributionStrategy);
    }

    @Override
    public List<Repository> execute(String organization, int howMany, OrderBy orderBy) {
        if (strategies.containsKey(orderBy)) {
            return strategies.get(orderBy).findTopRepositories(organization, howMany);
        }
        return Collections.emptyList();
    }
}
