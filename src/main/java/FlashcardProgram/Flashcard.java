package FlashcardProgram;

import java.awt.*;

public class Flashcard {

    private Front front;
    private String back;

    /**
     * Construct a Flashcard entity with a front and back.
     * @param front The front of a Flashcard
     * @param back The back of a Flashcard
     */
    public Flashcard(Front front, String back) {
        this.front = front;
        this.back = back;
    }

    /**
     * Set the front of this Flashcard to newFront.
     * @param newFront What to set this Flashcard's front to.
     */
    public void setFront(Front newFront) {
        this.front = newFront;
    }

    /**
     * Set the back of this Flashcard to newBack.
     * @param newBack What to set this Flashcard's back to.
     */
    public void setBack(String newBack) {
        this.back = newBack;
    }

    /**
     * Return the front of this Flashcard.
     * @return The front of this card.
     */
    public Front getFront() {
        return this.front;
    }

    /**
     * Return the back of this Flashcard.
     * @return The back of this card.
     */
    public String getBack() {
        return this.back;
    }

    public static class Front {
        private String text;
        private Image image;

        public Front(String text, Image image) {
            this.text = text;
            this.image = image;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Image getImage() {
            return image;
        }

        public void setImage(Image image) {
            this.image = image;
        }
    }

}
