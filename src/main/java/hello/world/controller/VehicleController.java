package hello.world.controller;

import java.util.logging.Logger;

import javax.inject.Inject;

import hello.world.beans.engine.Vehicle;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller("/vehicle")
public class VehicleController {

    @Inject
    private Vehicle vehicle;

    @Post("/start")
    public void index() {
        Logger.getLogger("Vehicle Logging").info(vehicle.start());
    }

}
