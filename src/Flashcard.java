
public class Flashcard {
    String front;
    String back;

    /**
     * Construct a Flashcard entity with a front and back.
     * @param front A string representing the text at the front of a Flashcard
     * @param back A string representing the text at the back of a Flashcard
     */
    public Flashcard(String front, String back) {
        this.front = front;
        this.back = back;
    }

    /**
     * Set the front of this Flashcard to newFront.
     * @param newFront A string of what to set this Flashcard's front to.
     */
    public void setFront(String newFront) {
        this.front = newFront;
    }

    /**
     * Set the back of this Flashcard to newBack.
     * @param newBack A string of what to set this Flashcard's back to.
     */
    public void setBack(String newBack) {
        this.back = newBack;
    }

    /**
     * Return the front of this Flashcard.
     * @return The (String) front of this card.
     */
    public String getFront() {
        return this.front;
    }

    /**
     * Return the back of this Flashcard.
     * @return The (String) back of this card.
     */
    public String getBack() {
        return this.back;
    }

}
