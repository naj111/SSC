package Neural_Network;

import java.io.IOException;
import java.util.ArrayList;
import weka.core.Instance;

public class DetectDisease_NN {

    public static void DetectDisease(String[] ArrayVal) {

        ArrayList<Double> PResult = PredictDisease(ArrayVal);
        String value = "";
        for (int index = 0; index < (PResult.size() - 1); index++) {
            value = ("Number " + index + " : " + PResult.get(index));

        }

        double FinalResult = (PResult.get(PResult.size() - 1));
        String N = Double.toString(FinalResult);
        System.out.println("Neural Networks Predicted Value");
        System.out.println("Preedicted Value : " + N.substring(0, 1));

    }

    public static ArrayList<Double> PredictDisease(String[] InstanceValue) {

        ArrayList<Double> Predicts = new ArrayList<>();
        if (!Training_NN.trained) {
            System.err.println("Nueral network has not been trained yet!!");
        } else {
            Instance temp = toInstance(InstanceValue);
            try {
                temp.setClassValue(Training_NN.nN.classifyInstance(temp));
                for (double d : Training_NN.nN.distributionForInstance(temp)) {
                    // classify all the instance in array
                    Predicts.add(d);
                }// giving a class value to the instance of teh image 
                // listing all the index
                Predicts.add(temp.classValue());
                // adding the closes value to last with its class value
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
        return Predicts;
    }

    private static Instance toInstance(String[] instanceData) {
        try {
            Training_NN.loadDataTemplate();
            Instance tempInstance = new Instance(Training_NN.template.numAttributes());
            tempInstance.setDataset(Training_NN.template);

            for (int index = 0; index < instanceData.length; index++) {
                tempInstance.setValue(index, instanceData[index]);
            }
            Training_NN.template.add(tempInstance);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return Training_NN.template.lastInstance();
    }

}
