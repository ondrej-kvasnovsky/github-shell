package shell.features.pullrequest.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface PullRequestClient {

    @RequestLine("GET /repos/{organization}/{repository}/pulls?state=all&per_page=1")
    @Headers({"Accept: application/vnd.github.v3+json", "Authorization: Basic {authToken}"})
    List<PullRequest> findPullRequest(
            @Param("organization") String organization,
            @Param("repository") String repository,
            @Param("authToken") String authToken);
}
