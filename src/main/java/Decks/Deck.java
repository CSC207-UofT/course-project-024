package Decks;

import Flashcards.Flashcard;

import java.util.ArrayList;
import java.util.List;

public class Deck{
    private String name;
    private final List<Flashcard> flashcards;

    /**
     * Create a new deck with the given name and starting flashcards.
     * @param name The name of the deck
     * @param flashcards The starting flashcards of the deck
     */
    public Deck(String name, List<Flashcard> flashcards){
        this.name = name;
        this.flashcards = flashcards;
    }

    /**
     * Create a new, empty deck with the given name.
     * @param name The name of the deck
     */
    public Deck(String name){
        this.name = name;
        this.flashcards = new ArrayList<>();
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

}