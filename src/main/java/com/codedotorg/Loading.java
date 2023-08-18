package com.codedotorg;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Loading {
    
    /** Displays text letting the user know the camera is loading */
    private Label cameraLoadingLabel;

    /** The progress indicator for while the camera is loading */
    private ProgressIndicator progress;

    /**
     * Constructor for the Loading class.
     * Initializes a ProgressIndicator and sets its ID to "loading".
     * Initializes a Label with the text "Camera loading...".
     */
    public Loading() {
        progress = new ProgressIndicator();
        progress.setId("loading");
        cameraLoadingLabel = new Label("Camera loading...");
    }

    /**
     * Returns the camera loading label.
     * @return the camera loading label
     */
    public Label getCameraLoadingLabel() {
        return cameraLoadingLabel;
    }

    /**
     * Returns the ProgressIndicator object used to display the loading progress.
     *
     * @return the ProgressIndicator object used to display the loading progress
     */
    public ProgressIndicator getProgressIndicator() {
        return progress;
    }

    /**
     * Hides the camera view and displays the loading animation.
     */
    public void showLoadingAnimation(ImageView cameraView) {
        cameraView.setVisible(false);
        cameraLoadingLabel.setVisible(true);
        progress.setVisible(true);
    }

    /**
     * Hides the loading animation by removing the progress bar and camera loading
     * label from the root layout and making the camera view visible.
     */
    public void hideLoadingAnimation(VBox rootLayout, ImageView cameraView) {
        rootLayout.getChildren().remove(progress);
        rootLayout.getChildren().remove(cameraLoadingLabel);
        cameraView.setVisible(true);
    }
}
