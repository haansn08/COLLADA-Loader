package COLLADA;

import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void buildMesh(MeshBuilder meshBuilder, String meshId) {
        int currentIndex = 0;
        Map<List<Integer>, Integer> indicesMap = new HashMap<List<Integer>, Integer>();
        DAEGeometry meshGeometry = (DAEGeometry) getElementByID(meshId);
        DAESemantic[] availableSources = meshGeometry.getSemantics();
        DAEIntArray originalIndices = meshGeometry.getIndices();
        for (int i = 0; i <= originalIndices.getCount() - availableSources.length; i+=availableSources.length) {
            List<Integer> tuple = new ArrayList<Integer>(availableSources.length);
            for (int source_index = 0; source_index < availableSources.length; source_index++)
                tuple.add(originalIndices.data[i + source_index]);

            if (indicesMap.containsKey(tuple))
                meshBuilder.onIndexData(indicesMap.get(tuple));
            else{
                //Construct vertex
                for (int source_index = 0; source_index < availableSources.length; source_index++) {
                    DAESource source = meshGeometry.getSourceBySemantic(availableSources[source_index]);
                    meshBuilder.onVertexData(
                            availableSources[source_index],
                            source.getDataByIndex(tuple.get(source_index))
                            );
                }
                indicesMap.put(tuple, currentIndex);
                meshBuilder.onIndexData(currentIndex);
                currentIndex++;
            }
        }
    }
}
