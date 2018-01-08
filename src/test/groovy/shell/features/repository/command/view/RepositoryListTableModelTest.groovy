package shell.features.repository.command.view

import shell.features.repository.model.Repository
import spock.lang.Specification

class RepositoryListTableModelTest extends Specification {

    def "returns formatted value for double value"() {
        given:
        List<Repository> list = [new Repository()]
        Map header = ['contributionPercentage': 'A Label']
        RepositoryListTableModel model = new RepositoryListTableModel(list, header)

        when:
        def value = model.getValue(1, 0)

        then:
        value == "0.00"
    }

    def "returns string value"() {
        given:
        List<Repository> list = [new Repository(name: 'A Name')]
        Map header = ['name': 'A Label']
        RepositoryListTableModel model = new RepositoryListTableModel(list, header)

        when:
        def value = model.getValue(1, 0)

        then:
        value == "A Name"
    }
}
