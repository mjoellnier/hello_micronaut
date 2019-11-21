package hello.world.beans.engine;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named("v6")
public class V6Engine implements Engine {

    private int cylinders = 6;

    public String start() {
        return "Starting V6";
    }

    public int getCylinders() {
        return cylinders;
    }

    public void setCylinders(int cylinders) {
        this.cylinders = cylinders;
    }

}