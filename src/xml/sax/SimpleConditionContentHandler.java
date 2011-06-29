/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xml.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This handler sets up conditions and returns them.
 * <p>
 * This is a centralized approach to parsing Conditions. If more modularity is
 * required, think about splitting this file up into smaller pieces, or
 * implement the parser handler code in the actual Condition class.
 *
 * @author Will
 */
public class SimpleConditionContentHandler extends DefaultHandler implements ConditionContentHandler{


    /**
     * Does stuff at the beginning of encountered XML tags.
     * <p>
     * Use this to create new classes.
     *
     * @param namespaceURI
     * @param name
     * @param qName
     * @param atts
     * @throws org.xml.sax.SAXException
     */
    public void startElement(String namespaceURI, String name, String qName, Attributes atts) throws SAXException {

        /*
        if(name.equalsIgnoreCase("ANNOTATION")){
            currentAnnotation = new LMAnnotation();
        }
        else if(name.equalsIgnoreCase("OBJECT")){
            currentObject = new LMObject();
        }
        else if(name.equalsIgnoreCase("POLYGON")){
            currentPolygon = new LMPolygon();
        }
        else if(name.equalsIgnoreCase("PT")){
            currentPoint = new LMPoint();
        }

        currentElement = name.toUpperCase();
        */
    }

    public void endElement(String namespaceURI, String name, String qName) throws SAXException {

        /*
        if(name.equalsIgnoreCase("OBJECT")){
            currentAnnotation.addObject(currentObject);
        }
        else if(name.equalsIgnoreCase("POLYGON")){
            currentObject.setPolygon(currentPolygon);
        }
        else if(name.equalsIgnoreCase("PT")){
            currentPolygon.addPoint(currentPoint);
        }
         */
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length);

        if(value.trim().equals("")){
            return;
        }

        value = value.trim();

        /* ignored tags:
         *  deleted
         *  verified
         */

        /*
        if(currentElement.equalsIgnoreCase("FILENAME")){
            this.currentAnnotation.setFilename(value);
        }else if(currentElement.equalsIgnoreCase("FOLDER")){
            this.currentAnnotation.setFolder(value);
        }else if(currentElement.equalsIgnoreCase("SOURCEIMAGE")){
            this.currentAnnotation.setSourceImage(value);
        }else if(currentElement.equalsIgnoreCase("SOURCEANNOTATION")){
            this.currentAnnotation.setSourceAnnotation(value);
        }

        else if(currentElement.equalsIgnoreCase("NAME")){
            this.currentObject.setName(value);
        }else if(currentElement.equalsIgnoreCase("DATE")){
            this.currentObject.setDate(value);
        }else if(currentElement.equalsIgnoreCase("DELETED")){
            this.currentObject.setDeleted(value.equals("0")?false:true);
        }else if(currentElement.equalsIgnoreCase("VERIFIED")){
            this.currentObject.setVerified(value.equals("0")?false:true);
        }

        else if(currentElement.equalsIgnoreCase("X")){
            this.currentPoint.setX(Integer.parseInt(value));
        }else if(currentElement.equalsIgnoreCase("Y")){
            this.currentPoint.setY(Integer.parseInt(value));
        }
    */

    }

}
