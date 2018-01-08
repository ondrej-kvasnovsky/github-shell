package shell.features.repository.command.view;

import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.Table;
import org.springframework.shell.table.TableBuilder;
import shell.features.repository.OrderBy;
import shell.features.repository.model.Repository;

import java.util.LinkedHashMap;
import java.util.List;

public class RepositoryTableBuilder {

    private List<Repository> repositories;
    private LinkedHashMap<String, Object> header = new LinkedHashMap<>();

    public RepositoryTableBuilder withRepositories(List<Repository> repositories) {
        this.repositories = repositories;
        return this;
    }

    public RepositoryTableBuilder withHeader(OrderBy orderBy) {
        withHeaderWithStarsForks();
        if (orderBy.equals(OrderBy.pullRequests) || orderBy.equals(OrderBy.contribution)) {
            withHeaderWithPRsContribution();
        }
        return this;
    }

    public RepositoryTableBuilder withHeaderWithStarsForks() {
        this.header.put("fullName", "Repository Name");
        this.header.put("stargazersCount", "Stars");
        this.header.put("forks", "Forks");
        return this;
    }

    public RepositoryTableBuilder withHeaderWithPRsContribution() {
        this.header.put("pullRequests", "Nr of PRs");
        this.header.put("contributionPercentage", "Contribution %");
        return this;
    }

    public Table build() {
        BeanListTableModel<Repository> model = new RepositoryListTableModel<>(repositories, header);
        return new TableBuilder(model)
                .addFullBorder(BorderStyle.fancy_light)
                .build();
    }
}
