package tests;

import junit.framework.TestCase;
import COLLADA.DAEFloatArray;
import COLLADA.DAEParser;
import COLLADA.DAESource;

/**
 * Created by Stefan Haan on 8/4/14.
 */
public class DAEParser_Tests extends TestCase {
    private final static String CUBE_FILE = "res/Cube.dae";
    private final static String SUZANNE_FILE = "res/Suzanne.dae";
    private final static String SPHERE_UV_FILE = "res/SphereUV.dae";

    public void testParseFloatArray() throws Exception {
        DAEParser cubeParser = new DAEParser(CUBE_FILE);
        DAEFloatArray cubeMeshPositions = (DAEFloatArray) cubeParser.getElementByID("Cube-mesh-positions-array");
        assertEquals(24, cubeMeshPositions.getCount());

        DAEFloatArray cubeMeshNormals = (DAEFloatArray) cubeParser.getElementByID("Cube-mesh-normals-array");
        assertEquals(36, cubeMeshNormals.getCount());
    }

   public void testParseSource() throws Exception{
       DAEParser sphereParser = new DAEParser(SPHERE_UV_FILE);
       DAESource positionSource = (DAESource)sphereParser.getElementByID("Icosphere-mesh-positions");
       DAEFloatArray positions = positionSource.getData();
       assertEquals(126, positions.getCount());
       assertEquals(42, positionSource.getCount());
       assertEquals(3, positionSource.getStride());

       DAESource normalsSource = (DAESource)sphereParser.getElementByID("Icosphere-mesh-normals");
       assertEquals(80, normalsSource.getCount());
   }
}
