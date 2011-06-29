/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xml.sax;

import java.io.File;
import org.xml.sax.ContentHandler;

/**
 * This interface is for objects in this software that you should be able to
 * save to XML and restore back for later processing.
 * <p>
 * Currently certain screens implement this so they can be saved and
 * read in the future. Distribution2D will also implement this so we can save
 * and restore a distribution of points.
 *
 * @author Will
 */
public interface XMLSaveable extends XMLWriteable{

    /**
     * Returns the object's SAX ContentHandler.
     * <p>
     * This is the content handler tied to the object that will set all the
     * object's parameters from the XML string.
     *
     * @return
     */
    public ContentHandler getContentHandler();

    /**
     * Restores the object's state from the XML String.
     *
     * @param XML
     * @return
     */
    public boolean loadFromXML(String XML);

    /**
     * Restore the objecct's state from an XML file.
     *
     * @param filename
     * @return
     */
    public boolean loadFromXMLFile(File filename);

    /**
     * Save the object to an XML file
     *
     * @param filename
     * @return
     */
    public boolean saveToXMLFile(File filename);

    /**
     * Returns the XML String of this object.
     *
     * @return
     */
    public String getXMLString();

    /**
     * Get the version of the object.
     * <p>
     * This version should be saved with the XML file as if future changes are
     * made to the code of the class, future versions may not be able to
     * properly load the XML file any longer.
     *
     * @return
     */
    public String getXMLSaveVersion();

}
