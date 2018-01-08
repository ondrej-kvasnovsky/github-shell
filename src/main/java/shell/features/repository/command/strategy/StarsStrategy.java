package shell.features.repository.command.strategy;

import org.springframework.stereotype.Component;
import shell.features.repository.OrderBy;
import shell.features.repository.model.Repository;
import shell.features.repository.service.RepositoryService;

import java.util.List;

@Component
public class StarsStrategy implements TopRepositoryStrategy {

    private final RepositoryService repositoryService;

    public StarsStrategy(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public boolean accept(OrderBy orderBy) {
        return orderBy.equals(OrderBy.stars);
    }

    @Override
    public List<Repository> findTopRepositories(String organization, int howMany) {
        return repositoryService.findTopRepos(organization, howMany, OrderBy.stars.name());
    }
}
