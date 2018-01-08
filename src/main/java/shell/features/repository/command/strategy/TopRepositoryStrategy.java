package shell.features.repository.command.strategy;

import shell.features.repository.OrderBy;
import shell.features.repository.model.Repository;

import java.util.List;

public interface TopRepositoryStrategy {

    boolean accept(OrderBy orderBy);

    List<Repository> findTopRepositories(String organization, int howMany);
}
