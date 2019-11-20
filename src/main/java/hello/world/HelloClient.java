package hello.world;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Single;

@Client("/hello")
public interface HelloClient {

    @Get
    Single<String> hello(); // A RxJava Single is returned with the value read from the server
}