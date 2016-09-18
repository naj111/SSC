/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Neural_Network;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Naj
 */


public class NuralN {
   
    public static String trainDataPath = "arff/traind.arff";
    public static String testDataPath = "arff/testd.arff"; //1
    public static String dataSetTemplate = "arff/temp.arff";
    public static IBk nN;

    public static Instances trainSet;
    public static Instances testSet;
    public static Instances temp;
    public static boolean trained = false;
    public static boolean tested = false;
    
   public static ArrayList<Double> predictDisease(String[] instanceData) {

        ArrayList<Double> predictions = new ArrayList<>();
        if (!trained) {
            System.err.println("Neural netowrk is not trained....");
        } else {
            Instance temp = toInstance(instanceData);
            try {
                temp.setClassValue(nN.classifyInstance(temp));
                for (double d : nN.distributionForInstance(temp)) {
                    // classify all the instance in array
                    predictions.add(d);
                }// giving a class value to the instance of teh image 
                // listing all the index
                predictions.add(temp.classValue());
                // adding the closes value to last with its class value
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
        return predictions;
    }

    private static Instance toInstance(String[] instanceData) {
        try {
            loadDataTemplate();
            Instance tempInstance = new Instance(temp.numAttributes());
            tempInstance.setDataset(temp);

            for (int index = 0; index < instanceData.length; index++) {
                tempInstance.setValue(index, instanceData[index]);
            }
            temp.add(tempInstance);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return temp.lastInstance();
    } 
    



    //Train the neural netowrk
    public static void trainNet() {

        System.out.println();
        try {
            loadTrainData();
            nN = new IBk();
            nN.buildClassifier(trainSet);
            // "Training Completed;
            trained = true;

        } catch (IOException e) {
            System.out.println("Train file missing....");
            System.err.println(e.toString());
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public int[] testNet() {

        System.out.println();
        int[] results = new int[2];
        if (!trained) {
            System.out.println("Neural netowrk is not trained....");
        } else {
            try {
                loadTestData();
                Evaluation tempEvaluator = new Evaluation(testSet);
                tempEvaluator.evaluateModel(nN, testSet);

                results[0] = (int) tempEvaluator.correct();
                results[1] = (int) tempEvaluator.incorrect();
                tested = true;
                // "Test completed;

            } catch (IOException e) {
                //"Test file missing
                System.out.println(e.toString());
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
        return results;
    }
    
    private static void loadDataTemplate() throws IOException {
        //Loading the training data arff
        BufferedReader tempReader
                = new BufferedReader(new FileReader(dataSetTemplate));
        //Converting data in to instances
        temp = new Instances(tempReader);
        //Class is last attribute in arff file type
        temp.setClassIndex(temp.numAttributes() - 1);
    }

    private static void loadTrainData() throws IOException {
        //Loading the training data arff
        BufferedReader tempReader
                = new BufferedReader(new FileReader(trainDataPath));
        //Converting data in to instances
        trainSet = new Instances(tempReader);
        //Class is last attribute in arff file type
        trainSet.setClassIndex(trainSet.numAttributes() - 1);
    }

    private static void loadTestData() throws IOException {
        //Loading the training data arff
        BufferedReader tempReader
                = new BufferedReader(new FileReader(testDataPath));
        //Converting data in to instances
        testSet = new Instances(tempReader);
        //Class is last attribute in arff file type
        testSet.setClassIndex(testSet.numAttributes() - 1);
    }

    public static boolean isTrained() {
        return trained;
    }

    public static boolean isTested() {
        return tested;
    }
     
    
    
 
}
