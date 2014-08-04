package production;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;

/**
 * Created by Stefan Haan on 8/4/14.
 */
class DAEContentHandler extends DefaultHandler {
    private HashMap<String, DAEElement> elementsMap = new HashMap<String, DAEElement>();

    private boolean isReadingElement = false;
    private String currentElementTag = "";
    private String currentElementId = "";
    private StringBuilder currentElementContent = new StringBuilder();
    private void deleteCurrentContent() {
        currentElementContent.delete(0, currentElementContent.length());
    }

    private int objectCount;
    public int getElementCount() {
        return objectCount;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        String id = attributes.getValue("id");
        if (id != null){
            objectCount++;
            isReadingElement = true;
            currentElementTag = qName;
            currentElementId = id;
            deleteCurrentContent();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (isReadingElement)
            currentElementContent.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (isReadingElement){
            isReadingElement = false;
            elementsMap.put(
                    currentElementId,
                    DAEElementBuilder.build(currentElementTag, currentElementContent.toString())
            );
        }
    }

    public DAEElement getElementByID(String id) {
        return elementsMap.get(id);
    }
}
