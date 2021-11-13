import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class BasicShuffle implements CardShuffler {

    int index = 0;
    ArrayList<Flashcard> deckCopy;

    public BasicShuffle(Map<Flashcard, FlashcardData> flashcardData) {
        // This turns an ImmutableList, which ToList returns, to an ArrayList. Don't ask me why.
        this.deckCopy = new ArrayList<>(flashcardData.keySet().stream().toList());
    }

    public void shuffleCards() {
        Collections.shuffle(this.deckCopy);
    }

    @Override
    public Flashcard returnChosenFlashcard() {
        Flashcard chosenFlashcard;
        if (index == 0) {
            // System.out.println("First run!");
            shuffleCards();
            chosenFlashcard = this.deckCopy.get(index);
            index += 1;
        } else if (index < this.deckCopy.size() - 1) {
            // System.out.println("Removing first and adding to last");
            chosenFlashcard = this.deckCopy.get(index);
            index += 1;
        } else { // index == flashcardData.size() - 1
            // System.out.println("Reached end, shuffling!");
            chosenFlashcard = this.deckCopy.get(index);
            index = 0;

        }
        return chosenFlashcard;
    }
}
