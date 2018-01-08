package shell.features.pullrequest.service;

import org.springframework.stereotype.Component;
import shell.features.pullrequest.client.PullRequestClient;
import shell.features.repository.model.Repository;
import shell.rest.HeaderJacksonDecoder;
import shell.rest.HttpHeaders;
import shell.rest.RestClient;
import shell.security.session.SessionHolder;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class DefaultPullRequestService implements PullRequestService {

    private final RestClient restClient;
    private final SessionHolder sessionHolder;

    public DefaultPullRequestService(RestClient restClient, SessionHolder sessionHolder) {
        this.restClient = restClient;
        this.sessionHolder = sessionHolder;
    }

    @Override
    public int findNumberOfPullRequest(String organization, String repository) {
        String authToken = sessionHolder.getCurrentClientSession().get().getAuthToken();
        HeaderJacksonDecoder headerDecoder = new HeaderJacksonDecoder();
        PullRequestClient client = restClient.createClient(PullRequestClient.class, headerDecoder);

        client.findPullRequest(organization, repository, authToken);
        Map<String, Collection<String>> headers = headerDecoder.getHeaders();
        return new HttpHeaders(headers).getLastPage();
    }

    @Override
    public void addPullRequestCounts(String organization, List<Repository> repositories) {
        List<Callable<Object>> tasks = createTasks(organization, repositories);

        ExecutorService executor = Executors.newFixedThreadPool(50);
        try {
            executor.invokeAll(tasks, 2, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Callable<Object>> createTasks(String organization, List<Repository> repositories) {
        return repositories.stream()
                .map(repository -> createTask(organization, repository))
                .collect(Collectors.toList());
    }

    private Callable<Object> createTask(String organization, Repository repository) {
        Callable<Object> task = () -> {
            String name = repository.getName();
            int numberOfPullRequest = findNumberOfPullRequest(organization, name);
            repository.setPullRequests(numberOfPullRequest);

            int forks = repository.getForks();
            if (forks != 0) {
                double contributionPercentage = numberOfPullRequest / (double) forks;
                repository.setContributionPercentage(contributionPercentage);
            }
            return null;
        };
        return task;
    }
}
