package shell.features.repository.command.strategy;

import shell.features.repository.model.Repository;

import java.util.List;

public interface TopRepositoryStrategy {

    List<Repository> findTopRepositories(String organization, int howMany);
}
