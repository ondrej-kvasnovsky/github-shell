package shell.features.repository.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import shell.features.repository.OrderBy;
import shell.features.repository.command.strategy.TopReposStrategyExecutor;
import shell.features.repository.command.view.RepositoryTableBuilder;
import shell.features.repository.model.Repository;
import shell.security.annotation.Secured;

import java.util.List;

@ShellComponent
public class TopReposCommand {

    private final TopReposStrategyExecutor topReposStrategyExecutor;

    public TopReposCommand(TopReposStrategyExecutor topReposStrategyExecutor) {
        this.topReposStrategyExecutor = topReposStrategyExecutor;
    }

    @Secured
    @ShellMethod(key = "top-repos", value = "Provides list of top repositories. Usage: top-repos <number of top repos> <by {stars | forks | pullRequests | contribution}>")
    public String topRepos(@ShellOption(defaultValue = "10") int howMany,
                           @ShellOption OrderBy orderBy) {
        String organization = "github";
        List<Repository> topRepos = topReposStrategyExecutor.execute(organization, howMany, orderBy);
        return new RepositoryTableBuilder()
                .withRepositories(topRepos)
                .withHeader(orderBy)
                .build()
                .render(600);
    }
}
