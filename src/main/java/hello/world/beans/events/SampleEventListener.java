package hello.world.beans.events;

import java.util.logging.Logger;

import javax.inject.Singleton;

import io.micronaut.runtime.event.annotation.EventListener;

@Singleton
public class SampleEventListener {

    @EventListener
    public void onApplicationEvent(TestEvent event) {
        Logger.getLogger("Sample Event Listener")
                .info("This is the Event listener with this event message: " + event.getMessage());
    }

}