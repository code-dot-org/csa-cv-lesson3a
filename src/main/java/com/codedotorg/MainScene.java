package com.codedotorg;

import com.codedotorg.modelmanager.CameraController;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class MainScene {
    
    /** The root layout of the main scene */
    private VBox rootLayout;

    /** Displays the camera feed in the app */
    private ImageView cameraView;

    /** Displays the title of the app */
    private Label titleLabel;

    /** Displays the computer's guess */
    private Label computerGuessLabel;

    /** Displays the predicted class and confidence score */
    private Label predictionLabel;

    /** Displays a prompt to tell the user to think of a number */
    private Label promptLabel;

    /** Button to exit the app */
    private Button exitButton;

    /** The loading animation while the camera is loading */
    private Loading cameraLoading;

    /** Whether or not this is the first time the camera has loaded */
    private boolean firstCapture;

    /**
     * Constructs a new MainScene object.
     * Initializes the cameraView, progress, exitButton, titleLabel, computerGuessLabel,
     * predictionLabel, promptLabel, cameraLoadingLabel, and firstCapture.
     */
    public MainScene() {
        cameraView = new ImageView();
        cameraView.setId("camera");

        exitButton = new Button("Exit");
        
        titleLabel = new Label("Guessing Game");
        titleLabel.setId("titleLabel");

        computerGuessLabel = new Label("");
        predictionLabel = new Label("");
        promptLabel = new Label("Think of a number between 1 and 100!");

        cameraLoading = new Loading();
        firstCapture = true;
    }

    /**
     * Returns the camera view ImageView object.
     *
     * @return the camera view ImageView object
     */
    public ImageView getCameraView() {
        return cameraView;
    }

    /**
     * Returns the loading animation
     * 
     * @return the Loading object for the loading animation
     */
    public Loading getLoadingAnimation() {
        return cameraLoading;
    }

    /**
     * Creates the main screen of the game.
     * 
     * @return the main scene of the game
     */
    public Scene createMainScene(CameraController cameraController) {
        // Sets the action for when the exit button is clicked
        createExitButtonAction(cameraController);

        // Initialize the root layout
        rootLayout = new VBox();
        rootLayout.setAlignment(Pos.CENTER);

        // Create spacers for above and below the cameraView
        Region cameraSpacer1 = createSpacer(20);
        Region cameraSpacer2 = createSpacer(20);

        // Create spacer for above the exit button
        Region buttonSpacer = createSpacer(10);

        // Add the title label, prompt label, loading animation, camera view, prediction label, and exit button to the layout
        rootLayout.getChildren().addAll(titleLabel, promptLabel, cameraLoading.getCameraLoadingLabel(),
            cameraSpacer1, cameraView, cameraSpacer2, cameraLoading.getProgressIndicator(), computerGuessLabel, predictionLabel, buttonSpacer, exitButton);

        // Creates a new scene and set the layout as its root
        Scene mainScene = new Scene(rootLayout, 600, 750);
        mainScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // Returns the main scene
        return mainScene;
    }

    /**
     * Returns whether this is the first capture of the game.
     * 
     * @return true if this is the first capture, false otherwise.
     */
    public boolean isFirstCapture() {
        return firstCapture;
    }
    
    /**
     * Sets the boolean value of firstCapture to false.
     */
    public void setFirstCaptureFalse() {
        this.firstCapture = false;
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

    /**
     * Displays the predicted user response on the UI.
     * 
     * @param predictedClass The predicted class of the user response.
     * @param predictedScore The predicted score of the user response.
     */
    public void showUserResponse(String predictedClass, double predictedScore) {
        // Hide the loading animation
        cameraLoading.hideLoadingAnimation(rootLayout, cameraView);

        // Update the label to prompt the user to think of a number
        promptLabel.setText("Higher, Lower, or Correct?");
        
        // Get the predicted class without the leading number
        String user = predictedClass.substring(predictedClass.indexOf(" ") + 1);

        // Convert the predicted score to an integer percentage
        int percentage = (int)(predictedScore * 100);

        // Create a String with the predicted class and confidence score
        String userResult = "User: " + user + " (" + percentage + "% Confidence)";

        // Update the predictionLabel to show the user's response and score
        Platform.runLater(() -> predictionLabel.setText(userResult));
    }

    /**
     * Displays the computer's guess on the computerGuessLabel.
     * 
     * @param computerGuess the computer's guess to be displayed
     */
    public void showComputerResponse(String computerGuess) {
        computerGuessLabel.setText(computerGuess);
    }

    /**
     * Creates a spacer region with the specified height.
     * 
     * @param amount the preferred height of the spacer region
     * @return a Region object with the specified height
     */
    private Region createSpacer(double amount) {
        Region temp = new Region();
        temp.setPrefHeight(amount);
        return temp;
    }
    
}
