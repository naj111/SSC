/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Neural_Network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;

/**
 *
 * @author CouriousSoul
 */
public class Training_NN {
    private static  String trainDataPath="arff/train.arff";
    private static String testDataPath="arff/test.arff"; //1
    private static String dataSetTemplate="arff/template.arff";
    public static IBk nN;

    private static Instances trainSet;
    private static Instances testSet;
    public static Instances template;
    public static boolean trained = false;
    public static boolean tested = false; 
                //Train the neural netowrk
    public  static void  trainNet() {
        
        System.out.println();
        try{
            loadTrainData();
            nN = new IBk();
            nN.buildClassifier(trainSet);
           // "Training Completed;
            trained = true;

        }catch (IOException e) {
            System.out.println("Train file missing....");
      //      jLabel1.setText("Train file missing....");
            System.err.println(e.toString());
        } catch (Exception e) {
            System.err.println(e.toString());
        }
     }
    
    
        public static int[] testNet() {
     
       System.out.println();
        int[] results = new int[2];
        if (!trained) {
            System.out.println("Neural netowrk is not trained....");
            //jLabel1.setText("Neural netowrk is not trained....");
        } else {
            try {
                loadTestData();
                Evaluation tempEvaluator = new Evaluation(testSet);
                tempEvaluator.evaluateModel(nN, testSet);
                
                results[0]=(int)tempEvaluator.correct();
                results[1]=(int)tempEvaluator.incorrect();
                tested=true;
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
        
        
        
         public static void loadDataTemplate() throws IOException{
        //Loading the training data arff
        BufferedReader tempReader
                = new BufferedReader(new FileReader(dataSetTemplate));
        //Converting data in to instances
        template = new Instances(tempReader);
        //Class is last attribute in arff file type
        template.setClassIndex(template.numAttributes() - 1);
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
    
    public static boolean isTrained(){
        return trained;
    }
    
    public static boolean isTested(){
        return tested;
    }
        
    
}
