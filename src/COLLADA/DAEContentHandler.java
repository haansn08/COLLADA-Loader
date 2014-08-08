package COLLADA;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Stefan Haan on 8/4/14.
 */
class DAEContentHandler extends DefaultHandler {
    private DAEGeometry geometry = new DAEGeometry();
    private Map<String, DAEElement> elementsMap = new HashMap<String, DAEElement>();
    private Stack<String> elementTagStack = new Stack<String>();
    private Stack<String> elementIdStack = new Stack<String>();
    private StringBuilder currentElementContent = new StringBuilder();
    private void deleteCurrentContent() {
        currentElementContent.delete(0, currentElementContent.length());
    }

    @Override
    public void startElement(String uri, String localName, String tagName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, tagName, attributes);
        elementTagStack.push(tagName);
        deleteCurrentContent();
        String id = attributes.getValue("id");
        if (id != null)
            elementIdStack.push(id);

        DAEElement forwardBuild = DAEElementBuilder.buildForward(tagName);
        if (forwardBuild != null)
            elementsMap.put(id, forwardBuild);

        if(tagName.equalsIgnoreCase("geometry"))
        {
            geometry = new DAEGeometry();
            elementsMap.put(id, geometry);
        }

        if (tagName.equalsIgnoreCase("accessor")){
            String parentSourceId = elementIdStack.peek();
            DAESource parentSource = (DAESource) elementsMap.get(parentSourceId);
            parentSource.setCount(Integer.parseInt(attributes.getValue("count")));
            parentSource.setStride(Integer.parseInt(attributes.getValue("stride")));
        }
        if (tagName.equalsIgnoreCase("input")){
            try {
                String semanticString = attributes.getValue("semantic");
                DAESemantic semantic = DAESemantic.valueOf(semanticString);
                String sourceId = attributes.getValue("source").substring(1);
                DAESource source = (DAESource) getElementByID(sourceId);
                geometry.setSource(semantic, source);
            }
            catch (IllegalArgumentException e)
            {
                //Semantic not found
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        currentElementContent.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        String currentElementTag = elementTagStack.pop();
        DAEElement buildElement = DAEElementBuilder.build(currentElementTag, currentElementContent.toString());
        if (buildElement == null)
            return;
        elementsMap.put(
                elementIdStack.pop(),
                buildElement
        );

        if (elementTagStack.empty())
            return;
        if (currentElementTag.equalsIgnoreCase("float_array")){
            String parentSourceId = elementIdStack.peek();
            DAESource parentSource = (DAESource) elementsMap.get(parentSourceId);
            parentSource.setData((DAEFloatArray) buildElement);
        }
        if (currentElementTag.equalsIgnoreCase("p")){
            geometry.setIndices((DAEIntArray) buildElement);
        }
    }

    public DAEElement getElementByID(String id) {
        return elementsMap.get(id);
    }
}
