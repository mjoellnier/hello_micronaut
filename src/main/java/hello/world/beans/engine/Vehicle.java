package hello.world.beans.engine;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class Vehicle {
    private final Engine engine;

    // The engine is injected via constructor pattern. The named annotation is
    // necassary as multiple implementations of the Engine interface exist
    public Vehicle(@Named("v6") Engine engine) {
        this.engine = engine;
    }

    public String start() {
        return engine.start();
    }
}