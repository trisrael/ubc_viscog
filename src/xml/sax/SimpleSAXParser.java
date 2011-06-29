/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xml.sax;

import experiment.Condition;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import xml.sax.ConditionContentHandler;

/**
 *
 * @author Will
 */
public class SimpleSAXParser {


    /**
     * Parses a condition XML file and returns a Condition.
     * 
     * @param myFile
     * @param handler
     * @return
     * @throws java.lang.Exception
     */
    public static Condition parseConditionFile(File myFile, ConditionContentHandler handler) throws Exception{

        InputStreamReader isr = null;

        try{
            isr = new FileReader(myFile);
        }catch(Exception e){
            e.printStackTrace();
        }

        InputSource is = new InputSource(isr);

        XMLReader parser = null;


        try{
            //parser = org.xml.sax.helpers.ParserFactory.makeParser(parserClass);
            parser = XMLReaderFactory.createXMLReader();
        }catch(Exception e){
            e.printStackTrace();
        }

        parser.setContentHandler(handler);

        try{
            parser.parse(is);
        }catch(Exception e){
            e.printStackTrace();
        }

        //return ((SaxLabelMeHandler)handler).getAnnotation();
        return null;

    }


    static public void main(String args[]){

        String filename = "./XML/test.xml";

        try{
            //LMAnnotation annotation = LMSimpleSAXParser.parse(new File(filename));
            //System.out.println(annotation.getXMLString());
        }catch(Exception e){
            System.err.println("Could not parse " + filename);
        }
    }

}
