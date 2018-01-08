package shell.rest

import spock.lang.Specification

class HttpHeadersTest extends Specification {

    def "returns last page number from headers"() {
        given:
        String links = '<https://api.com?per_page=1&page=2>; rel="next", <https://api.com?per_page=1&page=200>; rel="last"'
        Map<String, Collection<String>> headers = ['link': [links]]

        when:
        HttpHeaders httpHeaders = new HttpHeaders(headers)
        int lastPage = httpHeaders.lastPage

        then:
        lastPage == 200
    }

    def "returns 0 if 'link' is not contained in headers"() {
        given:
        Map<String, Collection<String>> headers = [:]

        when:
        HttpHeaders httpHeaders = new HttpHeaders(headers)
        int lastPage = httpHeaders.lastPage

        then:
        lastPage == 0
    }
}
