package shell.rest;

import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

public class HttpHeaders {
    private static final String LINK_HEADER = "link";
    private static final String LINKS_DELIMITER = ",";
    private static final String LINK_ITEM_DELIMITER = ";";
    private static final String PAGE_PARAMETER = "page";

    private final Map<String, Collection<String>> headers;

    public HttpHeaders(Map<String, Collection<String>> headers) {
        this.headers = headers;
    }

    /**
     * Extracts a page number from link to the last page.
     * <p>
     * There are pagination links in headers in the following format:
     * <code><https://api.github.com/repositories/25136308/pulls?per_page=1&page=2&sort=created&direction=decs&state=all>; rel="next", <https://api.github.com/repositories/25136308/pulls?per_page=1&page=206&sort=created&direction=decs&state=all>; rel="last"</code>
     *
     * @return last page
     */
    public int getLastPage() {
        if (headers != null && headers.containsKey(LINK_HEADER)) {
            Collection<String> linkHeader = headers.get(LINK_HEADER);
            if (!CollectionUtils.isEmpty(linkHeader)) {
                String links = linkHeader.iterator().next();
                String rawLastLink = links.split(LINKS_DELIMITER)[1].split(LINK_ITEM_DELIMITER)[0];
                String lastPageLink = rawLastLink.substring(1, rawLastLink.length() - 1);
                MultiValueMap<String, String> queryParams = fromUriString(lastPageLink).build().getQueryParams();
                List<String> page = queryParams.get(PAGE_PARAMETER);
                String lastPage = page.get(0);
                return Integer.parseInt(lastPage);
            }
        }
        return 0;
    }
}
