package shell.rest

import com.blogspot.toomuchcoding.spock.subjcollabs.Subject
import feign.Response
import spock.lang.Specification

import java.lang.reflect.Type

class HeaderJacksonDecoderTest extends Specification {

    @Subject
    HeaderJacksonDecoder decoder

    def "keeps headers from response"() {
        given:
        Map<String, Collection<String>> headers = ['a-key': ['a-value']]
        Response response = Response.builder().status(200).headers(headers).build()
        Type type = Mock()

        when:
        decoder.decode(response, type)

        then:
        decoder.headers == headers
    }
}
