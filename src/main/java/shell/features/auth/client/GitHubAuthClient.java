package shell.features.auth.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface GitHubAuthClient {

    @RequestLine("GET /")
    @Headers({"Accept: application/vnd.github.v3+json", "Authorization: Basic {authToken}"})
    void authenticate(@Param("authToken") String authToken);
}
