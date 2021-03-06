package Decks;

import Flashcards.Flashcard;
import Sessions.Observable;
import Sessions.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a deck of flashcards
 */
public class Deck implements Observable {
    private final List<Observer> observers;
    private String name;
    private final List<Flashcard> flashcards;
    private List<Flashcard> flashcardsLastState;

    /**
     * Create a new deck with the given name and starting flashcards.
     * @param name The name of the deck
     * @param flashcards The starting flashcards of the deck
     */
    public Deck(String name, List<Flashcard> flashcards){
        this.name = name;
        this.flashcards = flashcards;
        this.flashcardsLastState = flashcards;
        this.observers = new ArrayList<>();
    }

    /**
     * Create a new, empty deck with the given name.
     * @param name The name of the deck
     */
    public Deck(String name){
        this.name = name;
        this.flashcards = new ArrayList<>();
        this.flashcardsLastState = flashcards;
        this.observers = new ArrayList<>();
    }

    /**
     * @return the name of this deck
     */
    public String getName() {
        return name;
    }

    /**
     * Set this deck's name to the given name
     * @param newName The new name for the deck
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Get the flashcards of this deck
     * @return a list of flashcards of the deck
     */
    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    /**
     * Get the flashcards last state of this deck
     * @return a list of flashcards of the deck
     */
    public List<Flashcard> getFlashcardsLastState() {
        return flashcardsLastState;
    }

    /**
     * Add the given flashcard to this deck
     * @param newFlashcard The flashcard to be added to this deck
     */
    public void addFlashcard(Flashcard newFlashcard){
        flashcards.add(newFlashcard);
    }

    /**
     * Delete the given flashcard from this deck.
     * @param flashcard The flashcard to be deleted from this deck
     */
    public void removeFlashcard(Flashcard flashcard) {
        flashcards.remove(flashcard);
    }

    /**
     * @return A copy of this deck with the same name and flashcards
     */
    public Deck copyDeck(){
        List<Flashcard> copiedFlashcards = new ArrayList<>(this.flashcards);
        return new Deck(this.name, copiedFlashcards);
    }

    /**
     * add an observer to the observable
     * @param observer The observer to be added
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);

    }

    /**
     * delete an observer from the observable
     * @param observer The observer to be added
     */
    @Override
    public void deleteObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * check if the state of the deck has changed
     * @return boolean. Return true if the state of the deck has changed
     */
    @Override
    public boolean hasChanged() {
        if (flashcards.equals(flashcardsLastState)){
            return false;
        }
        flashcardsLastState = flashcards;
        return true;
    }

    /**
     * notify observers of any changes. Update the observers if there are changes
     */
    @Override
    public void notifyObservers() {
        if (hasChanged()){
            for (Observer observer: observers){
                observer.update();
            }
        }
    }

    /**
     * getter method for the list of observers
     * @return A list of observers
     */
    @Override
    public List<Observer> getObservers(){
        return this.observers;
    }
}
