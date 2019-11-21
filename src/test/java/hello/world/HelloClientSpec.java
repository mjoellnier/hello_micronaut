package hello.world;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import hello.world.client.HelloClient;
import io.micronaut.test.annotation.MicronautTest;

@MicronautTest
public class HelloClientSpec {

    @Inject
    HelloClient client;

    @Test
    public void testHelloWorldResponse() {
        assertEquals("Hello World", client.hello().blockingGet());
    }
}