package COLLADA;

import org.xml.sax.XMLReader;
import tests.MockMeshBuilder;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.nio.IntBuffer;
import java.util.HashMap;
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
        Map<int[], Integer> indicesMap = new HashMap<int[], Integer>();
        DAEGeometry meshGeometry = (DAEGeometry) getElementByID(meshId);
        DAESemantic[] availableSources = meshGeometry.getSemantics();
        DAEIntArray originalIndices = meshGeometry.getIndices();
        for (int i = 0; i <= originalIndices.getCount() - availableSources.length; i+=availableSources.length) {
            int[] tuple = new int[availableSources.length];
            for (int source_index = 0; source_index < tuple.length; source_index++) {
                tuple[source_index] = originalIndices.data[i + source_index];
            }
            if (indicesMap.containsKey(tuple))
                meshBuilder.onIndexData(indicesMap.get(tuple));
            else{
                //Construct vertex
                for (int source_index = 0; source_index < tuple.length; source_index++) {
                    DAESource source = meshGeometry.getSourceBySemantic(availableSources[source_index]);
                    meshBuilder.onVertexData(
                            availableSources[source_index],
                            source.getDataByIndex(tuple[source_index])
                            );
                }
                indicesMap.put(tuple, currentIndex);
                meshBuilder.onIndexData(currentIndex);
                currentIndex++;
            }
        }
    }
}
