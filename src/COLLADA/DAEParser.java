package COLLADA;

import org.xml.sax.XMLReader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Stefan Haan on 8/4/14.
 */
public class DAEParser{
    private DAEContentHandler contentHandler = new DAEContentHandler();

    public DAEParser(String filename) throws Exception {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(contentHandler);
        xmlReader.parse(filename);
    }

    public DAEElement getElementByID(String id) {
        return contentHandler.getElementByID(id);
    }
}
