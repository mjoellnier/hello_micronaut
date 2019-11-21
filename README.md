# Micronaut Hello World

## Intro

This is my Micronaut learning projetct. It is mainly based on the [official docs](https://docs.micronaut.io/latest/guide/index.html#) but it can have some minor adjustments to it. I'll try to keep this updated and comment especially on my changes. But mainly this is a **personal training** project that I _open sourced_ for fellow Micronauts.

**Any feedback is highly appreciated!**

## In General

    Micronaut is a modern, JVM-based, full stack microservices framework designed for building modular, easily testable microservice applications.

After insalling the CLI a new Micronaut project can be initialized via `mn create-app hello-world`. The default build system is Gradle. To use Maven just append `--build maven`. With `./mvnw compile exec:exec` the project is started, the default port is `8080`.

Different IDEs need different setups to work well with Micronaut! Look up the details [here](https://docs.micronaut.io/latest/guide/index.html#ideSetup).

# The Basics

## Basics of creating a Service

The basics of creating a simple service are very similia to Spring Boot:

```java
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/hello")
public class HelloController {

    @Get(produces = MediaType.TEXT_PLAIN)
    public String index() {
        return "Hello World";
    }
}
```

## Basics of testing a Service

Micronaut includes both an HTTP server and an HTTP client. A low-level HTTP client is provided out of the box which you can use to test the HelloController created in the previous section.

```java
import io.micronaut.context.annotation.Property;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class HelloControllerSpec {
    @Inject
    EmbeddedServer server;

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testHelloWorldResponse() {
        String response = client.toBlocking()
                .retrieve(HttpRequest.GET("/hello"));
        assertEquals("Hello World", response);
    }
}
```

In addition to the low-level client, Micronaut features a declarative, compile-time HTTP client. To use this simply add a `Client` annotation to an interface:

```java
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Single;

@Client("/hello")
public interface HelloClient {

    @Get
    Single<String> hello();
}
```

To test this client retrieve it from the Application context:

```java
import io.micronaut.test.annotation.MicronautTest;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class HelloClientSpec  {

    @Inject
    HelloClient client;

    @Test
    public void testHelloWorldResponse(){
        assertEquals("Hello World", client.hello().blockingGet());
    }
}
```

## Basics of deploying the application

The Micronaut application is deployed with a runnable JAR file. This can be created either via `./gradlew assemble` or `./mvnw package`. The Micronaut CLI also creates a `Dockerfile` during project creation which can be used for deployment.

# Inversion of Control/Dependency Injection

Spring inplements DI via runtime reflection and proxies. Micronaut on the other hand does this via compile time data hence the speed. Micronaut goes even so far to say that it doesn't use any Reflection or exzessive Caching at all -> **Speed**. In general it is possible to use the entire IoC functionality of Micronaut without the framework itself - but this isn't of interest for me so far. But it can be found [here](https://docs.micronaut.io/latest/guide/index.html#inversionofcontrol)

## Bean Scopes

- `@Singleton`: only one instance exists. They are created lazily!
- `@Context`: the bean is initialized at the same time as the ApplicationContext
- `@Prototype`: A new bean is created with every injection (this is the default scope and is equivalent with `@Bean`)
- `@Infrastructure`: A bean that can not be overriden or replaced (via `@Replaces`) as it is system critical
- `@ThreadLocal`: Associates a bean per thread
- `@Refreshable`: Allows the bean to be refreshed via `/refresh` endpoint
- `@RequestScope`: A new bean is created with every HTTP request

## Defining Beans

According to the docs: Micronaut implements the [JSR-330 (javax.inject)](http://javax-inject.github.io/javax-inject/) - Dependency Injection for Java specification hence to use Micronaut you simply use the [annotations provided by javax.inject](https://docs.oracle.com/javaee/6/api/javax/inject/package-summary.html). This also means that the `@Inject` annotation can be used to inject beans at runtime.

Micronaut supports multiple types of dependency injection:

- Constructor injection (must be one public constructor or a single contructor annotated with @Inject)
- Field injection
- JavaBean property injection
- Method parameter injection

## Qualifying by annotation

Besides using the `@Named` annotation you can also use the `@Qualifier` annotation:

```java
import javax.inject.Qualifier;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
public @interface V8 {
}
```

This can be used via:

```java
    @Inject Vehicle(@V8 Engine engine) {
        this.engine = engine;
    }
```

## Bean Factories

When using a third party framwork you can't annotate the code. To create beans from this classes you have to use the `@Factory` annotation:

```java
@Singleton
class CrankShaft {
}

class V8Engine implements Engine {
    private final int cylinders = 8;
    private final CrankShaft crankShaft;

    public V8Engine(CrankShaft crankShaft) {
        this.crankShaft = crankShaft;
    }

    public String start() {
        return "Starting V8";
    }
}

@Factory
class EngineFactory {

    @Singleton
    Engine v8Engine(CrankShaft crankShaft) {
        return new V8Engine(crankShaft);
    }
}
```
