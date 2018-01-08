package shell.rest;

import feign.Feign;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.stereotype.Component;

@Component
public class FeignRestClient implements RestClient {

    private static final String API = "https://api.github.com";

    public <T> T createClient(Class<T> clazz) {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(clazz, API);
    }

    @Override
    public <T> T createClient(Class<T> clazz, Decoder decoder) {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(decoder)
                .target(clazz, API);
    }
}
