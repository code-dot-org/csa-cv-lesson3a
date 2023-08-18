package com.codedotorg.modelmanager;

import org.opencv.core.Mat;
import org.tensorflow.Tensor;

public class ModelPredictor {

    /** The ModelProcessor to load and process the model */
    private ModelProcessor modelProcessor;

    /**
     * Constructs a new ModelPredictor object with the given ModelProcessor.
     * 
     * @param modelProcessor the ModelProcessor to use for prediction
     */
    public ModelPredictor(ModelProcessor modelProcessor) {
        this.modelProcessor = modelProcessor;
    }
    
    /**
     * Predicts using the model.
     *
     * @param tensorInput The Tensor input for the model.
     * @return The list of predicted class probabilities.
     */
    public float[] predict(Tensor<Float> tensorInput) {
        // The name of the input node and output node may vary based on your model. Adjust accordingly.
        String inputNodeName = modelProcessor.getInputNodeName();
        String outputNodeName = modelProcessor.getOutputNodeName() + ":0";

        try (Tensor<Float> result = modelProcessor.getSession().runner()
                .feed(inputNodeName, tensorInput)
                .fetch(outputNodeName)
                .run()
                .get(0)
                .expect(Float.class)) {

            float[][] outputArray = new float[1][(int) result.shape()[1]]; // assuming the output shape is [batch_size, number_of_classes]
            result.copyTo(outputArray);
            return outputArray[0]; // return the predictions for the first (and only) batch
        }
    }

    /**
     * Process the frame and get the model's prediction.
     *
     * @param frame The Mat frame to be processed.
     * @return The list of predicted class probabilities.
     */
    public float[] processAndPredict(Mat frame) {
        float[] floatArrayInput = modelProcessor.prepareFrameForModel(frame);
        Tensor<Float> tensorInput = modelProcessor.floatArrayToTensor(floatArrayInput);
        return predict(tensorInput);
    }

    /**
     * Determines the index of the maximum value in the given array.
     *
     * @param array The array of values.
     * @return The index of the maximum value.
     */
    public int getIndexOfMaxValue(float[] array) {
        int maxIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    /**
     * Gets the class name corresponding to the given prediction.
     *
     * @param predictions The predicted class probabilities.
     * @return The name of the most likely class.
     */
    public String getPredictedClassName(float[] predictions) {
        int predictedClassIndex = getIndexOfMaxValue(predictions);
        return modelProcessor.getLabels().get(predictedClassIndex);
    }

    /**
     * Process the frame, predict, and get the most likely class name.
     *
     * @param frame The Mat frame to be processed.
     * @return The name of the most likely class.
     */
    public String processFrameAndGetClassName(Mat frame) {
        float[] predictions = processAndPredict(frame);
        return getPredictedClassName(predictions);
    }

    /**
     * Gets the confidence score for the predicted class.
     *
     * @param predictions The predicted class probabilities.
     * @return The confidence score of the most likely class.
     */
    public float getPredictedClassConfidence(float[] predictions) {
        int predictedClassIndex = getIndexOfMaxValue(predictions);
        return predictions[predictedClassIndex];
    }

    /**
     * Processes a frame and returns the predicted class name and confidence level.
     * 
     * @param frame the frame to be processed
     * @return a Prediction object containing the predicted class name and confidence level
     */
    public Prediction processFrameAndGetClassNameWithConfidence(Mat frame) {
        float[] predictions = processAndPredict(frame);
        String className = getPredictedClassName(predictions);
        float confidence = getPredictedClassConfidence(predictions);
        return new Prediction(className, confidence);
    }

}
