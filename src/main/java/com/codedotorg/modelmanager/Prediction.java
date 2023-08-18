package com.codedotorg.modelmanager;

public class Prediction {

    /** The name of the predicted class */
    private final String className;

    /** The confidence score of the prediction */
    private final float confidence;

    /**
     * Constructs a Prediction object with the given class name and confidence value.
     * 
     * @param className the name of the predicted class
     * @param confidence the confidence value of the prediction
     */
    public Prediction(String className, float confidence) {
        this.className = className;
        this.confidence = confidence;
    }

    /**
     * Returns the name of the class.
     * 
     * @return the name of the class
     */
    public String getClassName() {
        return className;
    }

    /**
     * Returns the confidence level of the prediction.
     * 
     * @return the confidence level as a float value
     */
    public float getConfidence() {
        return confidence;
    }

    /**
     * Returns a string representation of the Prediction object.
     * 
     * @return a string containing the class name and confidence of the prediction
     */
    public String toString() {
        return "Prediction Result: className=" + className + ", confidence: " + confidence;
    }
}
