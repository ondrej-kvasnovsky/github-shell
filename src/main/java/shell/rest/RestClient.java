package shell.rest;

import feign.codec.Decoder;

public interface RestClient {

    <T> T createClient(Class<T> clazz);

    <T> T createClient(Class<T> clazz, Decoder decoder);
}
