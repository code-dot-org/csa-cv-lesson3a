package com.codedotorg;

public class GameLogic {
    
    /** The left boundary for the binary search (lowest number in the range) */
    private int left;

    /** The right boundary for the binary search (highest number in the range) */
    private int right;

    /** The computer's guess, which is the middle number between the lowest and highest number */
    private int guess;

    /**
     * Initializes the game logic with a range of 0 to 100 and
     * sets the initial guess to the midpoint of the range.
     */
    public GameLogic() {
        left = 0;
        right = 100;
        guess = (left + right) / 2;
    }

    /**
     * Performs a binary search based on the user's response to a guess.
     * 
     * @param predictedClass the predicted class returned by the model
     * @return an integer representing the next guess to make (-1 if the user's response is invalid)
     */
    public int binarySearch(String predictedClass) {
        
        return 0;
    }

    /**
     * Checks if the user's guess is correct.
     * 
     * @param predictedClass the user's predicted class
     * @return true if the user's guess is "stop", false otherwise
     */
    public boolean isGuessCorrect(String predictedClass) {
        
        return false;
    }

    /**
     * Returns the next guess by assuming the number is higher than the current guess.
     * Updates the left boundary of the search space to be the current guess.
     * The next guess is the number in the middle of the left and right boundaries.
     * 
     * @return the next guess
     */
    public int guessHigher() {
        
        return 0;
    }

    /**
     * Calculates the next guess by setting the current guess as the upper bound and
     * gets the middle of the lower and upper bounds as the new guess.
     *
     * @return the new guess
     */
    public int guessLower() {
        
        return 0;
    }

    /**
     * Returns the correct guess.
     * 
     * @return the correct guess
     */
    public int guessCorrect() {

        return 0;
    }

    /**
     * Resets the game by setting the left boundary to 0, the right boundary to 100,
     * and the guess to the midpoint of the boundaries.
     */
    public void resetLogic() {
        left = 0;
        right = 100;
        guess = (left + right) / 2;
    }

}
