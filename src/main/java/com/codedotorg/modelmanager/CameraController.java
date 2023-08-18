package com.codedotorg.modelmanager;

import java.io.ByteArrayInputStream;

import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.opencv.imgcodecs.Imgcodecs;

public class CameraController {

    /** The predicted class from the model */
    private String predictedClass;

    /** The confidence score of the model */
    private float predictedScore;

    /** Whether or not the camera capture is running */
    private static volatile boolean running = true;

    /**
     * Constructor for CameraController class.
     * Loads OpenCV locally and initializes predictedClass and predictedScore to null and 0 respectively.
     */
    public CameraController() {
        // Load the OpenCV library locally
        nu.pattern.OpenCV.loadLocally();

        // Set predictedClass to null at start
        predictedClass = null;

        // Set predictedScore to 0 at start
        predictedScore = 0;
    }

    /**
     * Captures camera frames and displays them in an ImageView while running.
     * 
     * @param imageView the ImageView to display the captured frames
     * @param model the ModelManager to use for predicting the class and score of the captured frames
     */
    public void captureCamera(ImageView imageView, ModelManager model) {
        // Create a new thread to run the camera capture to prevent the camera from
        // from blocking the main thread and causing the app to become unresponsive
        new Thread(() -> {
            // Create a VideoCapture with the system default camera (0)
            VideoCapture camera = new VideoCapture(0);

            if (!camera.isOpened()) {
                System.out.println("Error! Camera can't be opened.");
                return;
            }

            // Create a new frame to host the image from the camera
            Mat frame = new Mat();

            while (running) {
                // Capture the frame
                if (camera.read(frame)) {
                    // Convert and display the image from the camera
                    Image img = matToImage(frame);

                    // Update the image displayed in the image view
                    Platform.runLater(() -> imageView.setImage(img));

                    // Get the predicted result from the model
                    Prediction result = model.getPrediction(frame);

                    // Get the predicted class from the result
                    predictedClass = result.getClassName();

                    // Get the predicted score from the result
                    predictedScore = result.getConfidence();
                }
                else {
                    System.out.println("Cannot capture the frame.");
                    break;
                }
            }

            // Release the camera after usage
            camera.release();
        }).start();
    }

    /**
     * Returns the predicted class from the model
     *
     * @return the predicted class from the model
     */
    public String getPredictedClass() {
        return predictedClass;
    }

    /**
     * Returns the predicted confidence score from the model
     *
     * @return the predicted confidence score from the model
     */
    public float getPredictedScore() {
        return predictedScore;
    }

    /**
     * Stops the camera capture.
     */
    public void stopCapture() {
        running = false;
    }

    /**
     * Converts a given OpenCV Mat object to a JavaFX Image object.
     * 
     * @param frame The OpenCV Mat object to be converted.
     * @return The JavaFX Image object created from the image encoded in the buffer.
     */
    private Image matToImage(Mat frame) {
        // Create a temporary buffer to store the encoded image data
        MatOfByte buffer = new MatOfByte();

        // Encode the frame in the buffer, according to the PNG format
        Imgcodecs.imencode(".png", frame, buffer);

        // Build and return an Image created from the image encoded in the buffer
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }
}
