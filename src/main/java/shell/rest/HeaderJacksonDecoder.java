package shell.rest;

import feign.Response;
import feign.jackson.JacksonDecoder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

public class HeaderJacksonDecoder extends JacksonDecoder {

    private Map<String, Collection<String>> headers;

    @Override
    public Object decode(Response response, Type type) throws IOException {
        setHeaders(response.headers());
        return super.decode(response, type);
    }

    public Map<String, Collection<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Collection<String>> headers) {
        this.headers = headers;
    }
}