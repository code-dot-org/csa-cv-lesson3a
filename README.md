# Guessing Game

This application is a guessing game where the computer tries to guess a number between 1 and 100 that the user is thinking of. The computer's logic is based on binary search. The user's feedback on the computer's guesses (higher, lower, or correct) is predicted using a model.

## 🔎 Existing Code

### GuessingGame.java

The `GuessingGame` class interfaces with all the classes and serves as the main driver of the game experience.

**Suggested Modifications and New Features:**

* Implement save and resume game functionality.
* Introduce user profiles or game history tracking.
* Offer in-game tutorials or guidance for newcomers.

### MainScene.java

The `MainScene` class constructs the primary game interface, which displays prompts, receives user responses, and presents computer predictions.

**Suggested Modifications and New Features:**

* Introduce robust error handling and user-friendly messages.
* Add features such as scoreboards or multiplayer modes.

### GameOverScene.java

The `GameOverScene` class displays the game's outcome and provides options for restarting or exiting.

**Suggested Modifications and New Features:**

* Display a summary of game statistics.
* Introduce varying game-over graphics or animations based on outcomes.

### Loading.java

The `Loading` class manages the loading animations and labels shown during camera initialization or data processing.

**Suggested Modifications and New Features:**

* Introduce different loading animations for variety.
* Display a loading percentage or estimated time remaining.
* Implement a retry or cancel mechanism for prolonged loading times.

## ✅ TO DO: GameLogic.java

The `GameLogic` class controls the core mechanics of the game, determining the computer's guessing strategy and processing user input.

Implement the following methods in `GameLogic.java` to incorporate your Teachable Machine model in the app.

`binarySearch(String predictedClass)`

* Use the `predictedClass` to determine if the user thinks the guess is too high, too low, or correct.
* If the guess is too high, call `guessLower()`.
* If the guess is too low, call `guessHigher()`.
* If the guess is correct, call `guessCorrect()`.
* If the `predictedClass` is invalid or doesn't match any of the expected values, return `-1`.

`isGuessCorrect(String predictedClass)`

* Check if the `predictedClass` corresponds to the correct guess (e.g., check if `predictedClass` equals `"stop"` or another relevant string).
* Return `true` if the guess is correct, and `false` otherwise.

`guessHigher()`

* Update the `left` boundary to be the current `guess`.
* Calculate the new `guess` as the midpoint between `left` and `right`.
* Return the new `guess`.

`guessLower()`

* Update the `right` boundary to be the current `guess`.
* Calculate the new `guess` as the midpoint between `left` and `right`.
* Return the new `guess`.

`guessCorrect()`

* Since the current `guess` is correct, return the current `guess`.