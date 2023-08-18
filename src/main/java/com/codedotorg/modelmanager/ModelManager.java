package com.codedotorg.modelmanager;

import org.opencv.core.Mat;

public class ModelManager {
    
    /** The ModelProcessor to load and process the model */
    private ModelProcessor modelProcessor;

    /** The ModelPredictor to predict the class and obtain the confidence score */
    private ModelPredictor modelPredictor;

    /**
     * Constructs a new ModelManager object.
     * Initializes a ModelProcessor object, loads the model and labels, and initializes a ModelPredictor object.
     */
    public ModelManager() {
        modelProcessor = new ModelProcessor();
        modelProcessor.loadModel();
        modelProcessor.loadLabels();
        modelPredictor = new ModelPredictor(modelProcessor);
    }

    /**
     * Returns the ModelProcessor object associated with this ModelManager.
     *
     * @return the ModelProcessor object associated with this ModelManager
     */
    public ModelProcessor getModelProcessor() {
        return modelProcessor;
    }
    
    /**
     * Returns a Prediction object containing the predicted class name and confidence level for a given input frame.
     * 
     * @param frame the input frame to be processed
     * @return a Prediction object containing the predicted class name and confidence level
     */
    public Prediction getPrediction(Mat frame) {
        return modelPredictor.processFrameAndGetClassNameWithConfidence(frame);
    }
    
}
