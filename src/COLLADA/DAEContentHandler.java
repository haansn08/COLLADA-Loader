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
    Map<String, DAEElement> elementsMap = new HashMap<String, DAEElement>();
    public DAEElement getElementByID(String id) {
        return elementsMap.get(id);
    }


    StringBuffer nodeContent = new StringBuffer();
    Stack<NodeBuilder> nodeBuilderStack = new Stack<NodeBuilder>();
    @Override
    public void startElement(String uri, String localName, String tagName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, tagName, attributes);

        nodeContent.delete(0, nodeContent.length());

        NodeBuilder newNodeBuilder = NodeBuilderFactory.getNodeBuilder(tagName);
        newNodeBuilder.beginBuild(attributes);
        nodeBuilderStack.push(
                newNodeBuilder
        );
    }

    @Override
    public void endElement(String uri, String localName, String tagName) throws SAXException {
        super.endElement(uri, localName, tagName);
        NodeBuilder currentBuilder = nodeBuilderStack.pop();
        currentBuilder.setContent(nodeContent.toString());

        DAEElement builderResult = currentBuilder.getBuildResult();
        if (builderResult.hasId())
            elementsMap.put(builderResult.getId(), builderResult);

        if(!nodeBuilderStack.empty()){
            NodeBuilder parentBuilder = nodeBuilderStack.peek();
            parentBuilder.addChild(tagName, builderResult);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        nodeContent.append(ch, start, length);
    }
}
