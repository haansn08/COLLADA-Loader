package tests;

import junit.framework.TestCase;
import production.DAEFloatArray;
import production.DAEParser;

/**
 * Created by Stefan Haan on 8/4/14.
 */
public class DAEParser_Tests extends TestCase {
    private final static String CUBE_FILE = "res/Cube.dae";
    private final static String SUZANNE_FILE = "res/Suzanne.dae";

    public void testObjectCount() throws Exception{
        DAEParser cubeParser = new DAEParser(CUBE_FILE);
        assertEquals(10, cubeParser.getElementCount());

        DAEParser suzanneParser = new DAEParser(SUZANNE_FILE);
        assertEquals(8, suzanneParser.getElementCount());
    }

    public void testParseFloatArray() throws Exception {
        DAEParser cubeParser = new DAEParser(CUBE_FILE);
        DAEFloatArray cubeMeshPositions = (DAEFloatArray) cubeParser.getElementByID("Cube-mesh-positions-array");
        assertEquals(cubeMeshPositions.getCount(), 24);

        DAEFloatArray cubeMeshNormals = (DAEFloatArray) cubeParser.getElementByID("Cube-mesh-normals-array");
        assertEquals(cubeMeshNormals.getCount(), 36);
    }
}
