package com.codedotorg;

import com.codedotorg.modelmanager.CameraController;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GameOverScene {

    /** Button to play the game again */
    private Button playAgainButton;
    
    /** Button to exit the app */
    private Button exitButton;

    /**
     * Constructs a GameOverScene object with an exit
     * button and a play again button.
     */
    public GameOverScene() {
        exitButton = new Button("Exit");
        playAgainButton = new Button("Play Again");
    }

    /**
     * Returns a Scene object that displays a message indicating that the computer has guessed the correct number.
     * 
     * @param correctNumber The correct number that the computer has guessed.
     * @param primaryStage The primary stage of the JavaFX application.
     * @param cameraController The CameraController object used to control the camera in the 3D scene.
     * @return A Scene object that displays a message indicating that the computer has guessed the correct number.
     */
    public Scene createGameOverScene(int correctNumber, CameraController cameraController) {
        // Set the action for when the exit button is clicked
        createExitButtonAction(cameraController);

        // Create the layout for the scene
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        // Create the labels to display the correct number and success message
        Label correctNumberLabel = new Label("Correct Number: " + correctNumber);
        Label successMessage = new Label("The computer guessed the number!");

        // Add the labels and buttons to the layout
        layout.getChildren().addAll(correctNumberLabel, successMessage, playAgainButton, exitButton);

        // Create the scene with the layout
        Scene correctGuessScene = new Scene(layout, 600, 750);
        correctGuessScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // Return the scene
        return correctGuessScene;
    }

    /**
     * Returns the play again button.
     * 
     * @return the play again button
     */
    public Button getPlayAgainButton() {
        return playAgainButton;
    }

    /**
     * Sets the action for the exit button. When clicked, it
     * stops the camera capture and exits the program.
     */
    private void createExitButtonAction(CameraController cameraController) {
        exitButton.setOnAction(event -> {
            cameraController.stopCapture();
            System.exit(0);
        });
    }

}
