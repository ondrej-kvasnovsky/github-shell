package shell.features.repository

import shell.features.repository.model.Repository

class RepositoryBuilder {

    Repository repository = new Repository()

    RepositoryBuilder withPullRequests(int pullRequests) {
        repository.pullRequests = pullRequests
        this
    }

    RepositoryBuilder withForks(int forks) {
        repository.forks = forks
        this
    }

    Repository build() {
        repository.contributionPercentage = repository.pullRequests / (double) repository.forks
        repository
    }
}
