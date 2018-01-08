package shell.features.repository.command.view

import org.springframework.shell.table.Table
import shell.features.repository.OrderBy
import shell.features.repository.model.Repository
import spock.lang.Specification

class RepositoryTableBuilderTest extends Specification {

    def "adds two rows into table - first header, second data"() {
        when:
        Table table = new RepositoryTableBuilder()
                .withRepositories([new Repository()])
                .withHeaderWithStarsForks()
                .build()

        then:
        table.model.rowCount == 2
    }

    def "adds header into the table"() {
        when:
        Table table = new RepositoryTableBuilder()
                .withRepositories([])
                .withHeader(OrderBy.pullRequests)
                .build()

        then:
        table.model.getValue(0, 0) == "Repository Name"
        table.model.getValue(0, 1) == "Stars"
        table.model.getValue(0, 2) == "Forks"
        table.model.getValue(0, 3) == "Nr of PRs"
        table.model.getValue(0, 4) == "Contribution %"
    }

    def "adds header with stars and forks the table"() {
        when:
        Table table = new RepositoryTableBuilder()
                .withRepositories([])
                .withHeaderWithStarsForks()
                .build()

        then:
        table.model.getValue(0, 0) == "Repository Name"
        table.model.getValue(0, 1) == "Stars"
        table.model.getValue(0, 2) == "Forks"
    }

    def "adds header with PRs and contribution into the table"() {
        when:
        Table table = new RepositoryTableBuilder()
                .withRepositories([])
                .withHeaderWithPRsContribution()
                .build()

        then:
        table.model.getValue(0, 0) == "Nr of PRs"
        table.model.getValue(0, 1) == "Contribution %"
    }
}
