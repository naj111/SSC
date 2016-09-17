package Error_Logs;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Logs;
//
//import java.io.IOException;
//import java.util.logging.FileHandler;
//import java.util.logging.Handler;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author CouriousSoul
// */
//public class Logger {
//
//    private static Handler logFile;
//    
//    //private Logger(){}
//
//    public static Logger getNewLogger(String loggerName) {
//        Logger tempLogger = Logger.getLogger(loggerName);
//        if (logFile == null) {
//            initiateHanlder();
//        }
//
//        tempLogger.addHandler(logFile);
//        logFile.setLevel(Level.ALL);
//        tempLogger.setLevel(Level.ALL);
//        return tempLogger;
//    }
//
//    private static void initiateHanlder() {
//        try {
//            logFile = new FileHandler("ErroLog.tut");
//        } catch (IOException e) {
//            System.err.println("Invaild path in config file");
//            System.err.println("Creating default log file");
//        }
//    }
//
//}
