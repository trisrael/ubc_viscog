
package common.logging;

import common.filesystem.FileSystem;
import experiment.Subject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import log.LogfileRow;
import util.Util;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Oct 12, 2011
 */
public class ExperimentLogging {
    static final String FOLDER_LOGS = FileSystem.FOLDER_LOGS;
    private static final String DELI = "_";
    private static String BACKSLASH = "/";
    
    
    

    private static boolean addSubject(Subject person) {
       synchronized(uniqueTimes){
            Object time = uniqueTimes.get(person);
           if ( time == null){
               uniqueTimes.put(person, Util.getNow("yyyy-MM-dd_HHmmss"));
               return true;
           }
           return false;
       }
    }

    /**
     * Return the file associated with the data folder for a given Subject
     * @param person 
     */
    private static File dataFolderFor(Subject person) {
        return getFile(person, PathParts.Folder);
    }

    /**
     * Return a file associated with a person (only those associated within the enum PathParts)
     * @param person
     * @param pathParts
     * @return 
     */
    private static File getFile(Subject person, PathParts pathParts) {
        return new File(fp(pathParts, person));
    }
    
    
    public static enum PathParts {
        Folder,
        Data,
        Summary,
        XML,
        Custom //enum to denote a custom file creation within data folder, the ExperimentLogging does not know more about this file
    }
    
    private static Map<PathParts, String> descriptMap = new HashMap<PathParts, String>();
    
    static {
        descriptMap.put(PathParts.Data, "data.txt");
        descriptMap.put(PathParts.Summary, "summary.txt");
        descriptMap.put(PathParts.Folder, "dir");
        descriptMap.put(PathParts.XML, "XML.xml");
    }
    
    
    private static String fp(PathParts part, Subject person, String filename){
        String desc;
        SubjectFilenameBuilder builder = person.getBuilder();
        
        Object pull;
       synchronized(uniqueTimes){
         pull =  uniqueTimes.get(person);
         
       }
         String inFst  = "";
         if(pull != null)
            inFst = String.class.cast(pull) + SubjectFilenameBuilder.delimiter;
         
         
         synchronized(descriptMap){desc = descriptMap.get(part);}
           
         switch (part){
             case Custom:
                 return fp(PathParts.Folder, person) + BACKSLASH + builder.buildFileName(person, filename);
             case Folder:
                  inFst = FOLDER_LOGS + inFst;
                  break;
             default:
                  inFst =  fp(PathParts.Folder, person) + BACKSLASH +  inFst;
                 break;
         }
         
          return inFst + builder.buildFileName(person, desc);
       
    }
    
    private static String fp(PathParts part, Subject person){
        return fp(part, person, null);
    }
    
    /*
     * As new files are created keep a reference to the data used at the time which prefixes all files, so that further calls 
     */
    private final static Map uniqueTimes = new HashMap<Subject, String>();  
   
    /**
     * Creates the log file and folder for this experiment.
     * @param experimentPrefix, string that gets appended to the current time 
     */
    public static void createLogfileAndFolder(Subject person) {
        if(addSubject(person)){
        
        // create log folder (to dump distributions, other data, etc.)
        try {
            File myFolder = dataFolderFor(person);
            myFolder.mkdir();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        // create logfile
        try {
            
            BufferedWriter out = new BufferedWriter(new FileWriter(createFile(PathParts.Data, person), false));
            out.write(LogfileRow.getTitle() + "\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // create a summary of a subjects data
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(createFile(PathParts.Summary, person), false));
            out.write("level	above	JND	trials  scalingVal dotSize numPoints dotStyle dotHue" + "\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        }
    }
    
    
    public static File createFile(PathParts part, Subject person){
        String path = fp(part, person);
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(ExperimentLogging.class.getName()).log(Level.SEVERE, "Can't create file at path: " + path , ex);
        }
        return file;
    }
    
    
    /**
     * Write data to a file of a known file to the Logging mechanism
     * @param person
     * @param pathParts
     * @param dataString 
     */
    public static void writeToFile(Subject person, PathParts part, String dataString) {
      writeOutToFile(getDataFile(person, part), dataString);
    }
    
    
     
    /**
     * Write data to a file of a custom file found within the data folder (this file should already be created)
     * @param person
     * @param pathParts
     * @param dataString 
     */
    public static void writeToFile(Subject person, String filename, String dataString){
      writeOutToFile(getCustomDataFile(person, filename), dataString);
    }
    
    /**
     * Given a file write out a given a data string to it.
     * @param toWriteTo
     * @param dataToWrite 
     */
    private static void writeOutToFile(File toWriteTo, String dataToWrite){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(toWriteTo, true));
            out.write(dataToWrite);
            out.newLine();
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Get the File object associated with a known Data file 
     * @param person, subject in use (for finding which folder to lookin)
     * @param part, PathPart known to logger
     * @return
     */
    public static File getDataFile(Subject person, PathParts part){
        return new File(fp(part, person));
    }
    
    
    /**
     * Get the File object associated with the filename and subject given, will look in the data folder associated with Subject
     * @param person, subject in use (for finding which folder to lookin)
     * @param filename, name of file searched for
     * @return
     */
    public static File getCustomDataFile(Subject person, String filename){
        return new File(fp(PathParts.Custom, person, filename));
    }
    
    
    
}
