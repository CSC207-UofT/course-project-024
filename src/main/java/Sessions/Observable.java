package java.Sessions;

public interface Observable {
    void addObserver(Observer observer);
    void deleteObserver(Observer observer);
    boolean hasChanged();
    void notifyObservers();
}
