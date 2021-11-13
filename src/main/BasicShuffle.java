import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;

public class BasicShuffle implements CardShuffler {

    int counter = 0;
    LinkedList<Flashcard> linkedListDeck;

    public BasicShuffle(Map<Flashcard, FlashcardData> flashcardData) {
        // This turns an ImmutableList, which ToList returns, to a LinkedList. Don't ask me why.
        this.linkedListDeck = new LinkedList<>(flashcardData.keySet().stream().toList());
    }

    public void shuffleCards() {
        Collections.shuffle(this.linkedListDeck);
    }

    @Override
    public Flashcard returnChosenFlashcard() {
        if (counter == 0) {
            // System.out.println("First run!");
            shuffleCards();
            counter += 1;
            return linkedListDeck.getFirst();
        } else if (counter < linkedListDeck.size()) {
            // System.out.println("Removing first and adding to last");
            Flashcard oldFirstFlashcard = linkedListDeck.getFirst();
            linkedListDeck.removeFirst();
            linkedListDeck.addLast(oldFirstFlashcard);
            counter += 1;
            return linkedListDeck.getFirst();
        } else { // counter == flashcardData.size()
            // System.out.println("Reached end, shuffling!");
            Collections.shuffle(linkedListDeck);
            counter = 1;
            return linkedListDeck.getFirst();

        }

    }
    // TODO: Implement
    @Override
    public void updateCardShuffler() {

    }
}
