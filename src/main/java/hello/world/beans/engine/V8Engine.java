package hello.world.beans.engine;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named("v8")
public class V8Engine implements Engine {

    private int cylinders = 8;

    public String start() {
        return "Starting V8";
    }

    public int getCylinders() {
        return cylinders;
    }

    public void setCylinders(int cylinders) {
        this.cylinders = cylinders;
    }
}