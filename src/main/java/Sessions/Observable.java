package Sessions;

import java.util.List;

public interface Observable {
    void addObserver(Observer observer);
    void deleteObserver(Observer observer);
    boolean hasChanged();
    void notifyObservers();
    List<Observer> getObservers();
}
