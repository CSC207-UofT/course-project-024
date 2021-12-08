package Sessions;

import java.util.List;

/**
 * An interface for objects that need to be observed
 */
public interface Observable {

    /**
     * add an observer to the observable
     * @param observer
     */
    void addObserver(Observer observer);

    /**
     * delete an observer from the observable
     * @param observer
     */
    void deleteObserver(Observer observer);

    /**
     * check if the state of the observable has changed
     * @return boolean. Return true if the state of the observable has changed
     */
    boolean hasChanged();

    /**
     * notify observers of any changes. Update the observers if there are changes
     */
    void notifyObservers();

    /**
     * getter method for the list of observers
     * @return A list of observers
     */
    List<Observer> getObservers();
}
