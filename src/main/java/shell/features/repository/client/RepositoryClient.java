package shell.features.repository.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import shell.features.repository.model.SearchRepositoryResponse;

public interface RepositoryClient {

    @RequestLine("GET /search/repositories?q={q}&page={page}&per_page={perPage}")
    @Headers({"Accept: application/vnd.github.v3+json", "Authorization: Basic {authToken}"})
    SearchRepositoryResponse paginateRepositories(
            @Param("q") String q,
            @Param("page") int page,
            @Param("perPage") int perPage,
            @Param("authToken") String authToken);
}
