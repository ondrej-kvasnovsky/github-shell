package shell.features.pullrequest.service;

import shell.features.repository.model.Repository;

import java.util.List;

public interface PullRequestService {

    int findNumberOfPullRequest(String organization, String repository);

    void addPullRequestCounts(String organization, List<Repository> repositories);
}
