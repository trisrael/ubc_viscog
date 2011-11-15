package StevensLevel.logging;

import StevensLevel.EventBusHelper;
import StevensLevel.listeners.StevensLogListener;
import StevensLevel.parts.Trial;
import common.logging.ExperimentLogging.PathParts;
import experiment.Subject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import render.GraphStyleSheet;
import sun.java2d.SunGraphicsEnvironment.T1Filter;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Nov 14, 2011
 */
public class ExperimentLogging implements StevensLogListener{

    private static ExperimentLogging singleton;
    final static String boolgetter = "is";
    final static String getter = "get";
    final static String setter = "set";
    
    private static TrialView trView = new ExperimentLogging.TrialView();
     
    static{
              singleton = new ExperimentLogging();
        EventBusHelper.listen(singleton, StevensLogListener.class);
    }

    

   static abstract class ObjView<E> {
        abstract List<String> getMethStrs();
        String tab = "\t";
        String nextline = "\n";
        
        public String getTitleString(){
            String out = "";
            List<String> strs = getMethStrs();
            for (String str : strs) {
                out+= str + tab;
            }
            
            return out;
        }

        public String getStringFor(E obj) {
            List<String> methStrs = getMethStrs();
            
            
            String toReturn = "";

            for (String name : methStrs) {
                Method meth = null;
                try {
                    meth = obj.getClass().getMethod(getter + name, null);
                } catch (Exception ex) {
                    Logger.getLogger(ExperimentLogging.class.getName()).log(Level.SEVERE, "No javabean getter for: " + name, ex);
                }

                if (meth == null) {
                    try {
                        meth = obj.getClass().getMethod(boolgetter + name, null);
                    } catch (Exception ex) {
                        Logger.getLogger(ExperimentLogging.class.getName()).log(Level.SEVERE, "No javabean boolean getter for: " + name, ex);
                    }
                }
                try {
                    
                    toReturn += (meth.invoke(obj)).toString() + tab;
                } catch (Exception ex) {
                    Logger.getLogger(ExperimentLogging.class.getName()).log(Level.SEVERE, "No method found at all for: " + name, ex);
                }
            }
            
            return toReturn + nextline;
        }
    }

    static class TrialView extends ObjView<Trial> {

        @Override
        List<String> getMethStrs() {
            return Arrays.asList("AdjustedCorr", "HighCorr", "LowCorr", "Points");
        }
        
        
        @Override
        public String getStringFor(Trial trial){
            GraphStyleSheetView gssView = new GraphStyleSheetView();
            return super.getStringFor(trial).replaceAll(nextline, "") + gssView.getStringFor(trial.getStylesheet());
        }
        
        public String titlesRepresent(){
            return getTitleString() + (new GraphStyleSheetView().getTitleString()) + nextline;
        }
        
    }

    static class GraphStyleSheetView extends ObjView<GraphStyleSheet> {
        @Override
        List<String> getMethStrs() {
            return Arrays.asList("AxisOn", "DotHue", "DotStyle", "LabelsOn", "DotScaling", "DotSize");
        }
    }

    public static void setup() {
        common.logging.ExperimentLogging.createLogfileAndFolder(singleton.subj);
        writeTitleString();
    }
    private Subject subj;


    private ExperimentLogging() {
    }

    public static void setSubject(Subject subject) {
        singleton.subj = (subject);
    }
    
    
    @Override
    public void logTrial(Trial trial) {
           singleton.write(trView.getStringFor(trial));
    }

    

    public void write(String string) {
        synchronized (singleton.subj) {
            common.logging.ExperimentLogging.writeToFile(singleton.subj, PathParts.StevensTrials, string);
        }
    }
    
    private static void writeTitleString(){
        singleton.write(trView.titlesRepresent());
    }
    
}