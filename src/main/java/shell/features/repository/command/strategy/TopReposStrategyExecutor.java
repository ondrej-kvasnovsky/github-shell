package shell.features.repository.command.strategy;

import shell.features.repository.OrderBy;
import shell.features.repository.model.Repository;

import java.util.List;

public interface TopReposStrategyExecutor {

    List<Repository> execute(String organization, int howMany, OrderBy orderBy);
}
