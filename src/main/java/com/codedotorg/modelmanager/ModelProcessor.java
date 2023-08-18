package com.codedotorg.modelmanager;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.tensorflow.Graph;
import org.tensorflow.Operation;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

public class ModelProcessor {

    /** The path to the directory containing the model */
    private static final String MODEL_PATH = "src\\main\\java\\com\\codedotorg\\model\\";

    /** The path to the labels.txt file (should be in the root of the model directory) */
    private static final String LABELS_PATH = MODEL_PATH + "labels.txt";

    /** Represents the TensorFlow model and its associated variables */
    private SavedModelBundle bundle;

    /** Represents a TensorFlow session, which is used to run the model and make predictions */
    private Session session;

    /** The list of class labels for the model */
    private List<String> labels;

    /**
     * Constructs a new ModelProcessor object with null values for bundle and session.
     */
    public ModelProcessor() {
        bundle = null;
        session = null;
    }
    
    /**
     * Loads a saved model from the specified path and creates a session.
     * Prints a message to the console if the model is loaded successfully.
     * Prints an error message and stack trace to the console if the model fails to load.
     */
    public void loadModel() {
        try {
            // Load the TensorFlow model from the MODEL_PATH directory and create a new
            // SavedModelBundle object. "serve" specifies the model signature name.
            bundle = SavedModelBundle.load(MODEL_PATH, "serve");

            // Sets the session to a new Session object to run the TensorFlow model and make predictions
            session = bundle.session();
            System.out.println("Model loaded successfully");
        } catch (Exception e) {
            System.err.println("Failed to load the model");
            e.printStackTrace();
        }
    }

    /**
     * Returns the name of the input node in the TensorFlow model.
     *
     * @return The name of the input node.
     */
    public String getInputNodeName() {
        String inputNodeName = "";

        Graph graph = bundle.graph();
        Iterator<Operation> operations = graph.operations();

        while (operations.hasNext()) {
            Operation operation = operations.next();

            if (operation.name().contains("input")) {
                inputNodeName = operation.name();
            }
        }

        return inputNodeName;
    }

    /**
     * Returns the name of the output node of the TensorFlow model.
     * The output node is determined by finding the first operation in the graph that contains the string "input" in its name,
     * and then returning the name of the next operation in the graph.
     *
     * @return The name of the output node of the TensorFlow model.
     */
    public String getOutputNodeName() {
        String outputNodeName = "";

        Graph graph = bundle.graph();
        Iterator<Operation> operations = graph.operations();

        while (operations.hasNext()) {
            Operation operation = operations.next();

            if (operation.name().contains("input")) {
                Operation outputOperation = operations.next();
                outputNodeName = outputOperation.name();
            }
        }

        return outputNodeName;
    }

    /**
     * Reads all the lines from the file specified by LABELS_PATH and stores them in the labels list.
     * Prints a success message and the labels list if the operation is successful.
     * Prints an error message and the stack trace if the operation fails.
     */
    public void loadLabels() {
        try {
            // Read all the lines from the labels.txt file and returns them as a list of strings
            // Paths.get() creates a Path object representing the path to the labels file
            labels = Files.readAllLines(Paths.get(LABELS_PATH));
            
            System.out.println("Labels loaded successfully");
            System.out.println("Labels: " + labels);
        } catch (IOException e) {
            System.err.println("Failed to load the labels");
            e.printStackTrace();
        }
    }

    /**
     * Returns the current session object.
     *
     * @return the current session object.
     */
    public Session getSession() {
        return session;
    }

    /**
     * Returns a list of labels for the model.
     *
     * @return a list of labels
     */
    public List<String> getLabels() {
        return labels;
    }

    /**
     * Resizes the given Mat frame to the specified dimensions.
     *
     * @param frame The original Mat frame.
     * @return The resized Mat frame.
     */
    public Mat resizeFrame(Mat frame) {
        Mat resized = new Mat();
        Imgproc.resize(frame, resized, new Size(224, 224)); // resize to 224x224
        return resized;
    }

    /**
     * Converts the given Mat frame to a byte array.
     *
     * @param frame The Mat frame to convert.
     * @return The byte array representation of the Mat frame.
     */
    public byte[] matToByteArray(Mat frame) {
        byte[] byteArray = new byte[(int) (frame.total() * frame.channels())];
        frame.get(0, 0, byteArray);
        return byteArray;
    }

    /**
     * Converts a byte array to a float array and normalizes its values to [0,1].
     *
     * @param byteArray The byte array to convert.
     * @return The float array.
     */
    public float[] byteArrayToFloatArray(byte[] byteArray) {
        float[] floatArray = new float[byteArray.length];
        for (int i = 0; i < byteArray.length; i++) {
            floatArray[i] = ((byteArray[i] & 0xFF) - 127.5f) / 127.5f; // normalization to [-1,1], adjust if needed
        }
        return floatArray;
    }

    /**
     * Prepares the Mat frame for model input.
     *
     * @param frame The Mat frame to prepare.
     * @return The float array representing the reshaped frame.
     */
    public float[] prepareFrameForModel(Mat frame) {
        Mat resizedFrame = resizeFrame(frame);
        byte[] byteArray = matToByteArray(resizedFrame);
        return byteArrayToFloatArray(byteArray);
    }

    /**
     * Converts the float array to a TensorFlow Tensor.
     *
     * @param floatArray The float array to convert.
     * @return A Tensor representing the input data.
     */
    public Tensor<Float> floatArrayToTensor(float[] floatArray) {
        long[] shape = {1, 224, 224, 3}; // assuming the model expects input shape as [batch_size, height, width, channels]
        return Tensor.create(shape, FloatBuffer.wrap(floatArray));
    }

}
