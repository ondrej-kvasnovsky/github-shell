package shell.features.repository.command.strategy;

import org.springframework.stereotype.Component;
import shell.features.repository.OrderBy;
import shell.features.repository.model.Repository;
import shell.features.repository.service.RepositoryService;

import java.util.List;

@Component
public class ForksStrategy implements TopRepositoryStrategy {

    private final RepositoryService repositoryService;

    public ForksStrategy(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public List<Repository> findTopRepositories(String organization, int howMany) {
        return repositoryService.findTopRepos(organization, howMany, OrderBy.forks.name());
    }
}
