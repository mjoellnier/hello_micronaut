package hello.world.controller;

import java.util.logging.Logger;

import javax.inject.Inject;

import hello.world.beans.engine.Vehicle;
import hello.world.beans.events.TestEvent;
import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller("/vehicle")
public class VehicleController {

    @Inject
    private Vehicle vehicle;
    @Inject
    private ApplicationEventPublisher eventPublisher;

    @Post("/start")
    public void index() {
        eventPublisher.publishEvent(new TestEvent());
        Logger.getLogger("Vehicle Logging").info(vehicle.start());
    }

}
