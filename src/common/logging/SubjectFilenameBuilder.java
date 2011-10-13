package common.logging;

import experiment.Subject;

/**
 *Define how a filename is to be printed out for the Logging utility class
 * @author tristangoffman
 */
public interface SubjectFilenameBuilder {
    final String delimiter = "_"; 
    /**
     * 
     * @param person all experiments are run on a subject therefore these
     * @param fend the name of the file & file type
     * @return 
     */
    String buildFileName(Subject person, String fend); 
    
}
