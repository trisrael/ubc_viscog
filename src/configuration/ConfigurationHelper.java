/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import common.filesystem.FileSystem;
import java.io.File;

/**
 *
 * @author Tristan Goffman(tgoffman@gmail.com) Oct 17, 2011
 */
public class ConfigurationHelper {
  /**
     * Retrieve a configuration file from the filesystem
     * @param filename
     * @return 
     */
    public static File retrieveConfFile(String filename){
        return new File(FileSystem.FOLDER_CONF + File.separatorChar + filename);
    }   
}
