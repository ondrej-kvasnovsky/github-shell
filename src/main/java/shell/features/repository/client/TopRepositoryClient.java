package shell.features.repository.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import shell.features.repository.model.SearchRepositoryResponse;

public interface TopRepositoryClient {

    @RequestLine("GET /search/repositories?q={q}&sort={sort}&order={order}&per_page={perPage}")
    @Headers({"Accept: application/vnd.github.v3+json", "Authorization: Basic {authToken}"})
    SearchRepositoryResponse findTopRepositories(
            @Param("q") String q,
            @Param("sort") String sort,
            @Param("order") String order,
            @Param("perPage") int perPage,
            @Param("authToken") String authToken);
}
