package Sessions;

/**
 * An interface for objects that need to observe something
 */
public interface Observer {

    /**
     * updates the observer on the most recent state of the observable
     */
    void update();
}
