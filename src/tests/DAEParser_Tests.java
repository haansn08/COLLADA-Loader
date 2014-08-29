package tests;

import COLLADA.*;
import junit.framework.TestCase;
import org.junit.Assert;

/**
 * Created by Stefan Haan on 8/4/14.
 */
public class DAEParser_Tests extends TestCase {
    private final static String CUBE_FILE = "res/Cube.dae";
    private final static String SUZANNE_FILE = "res/Suzanne.dae";
    private final static String SPHERE_UV_FILE = "res/SphereUV.dae";
    private final static String LETTERS_FILE = "res/rgb.dae";

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
       assertEquals(42, positionSource.getAccessor().getCount());
       assertEquals(3, positionSource.getAccessor().getStride());

       DAESource normalsSource = (DAESource)sphereParser.getElementByID("Icosphere-mesh-normals");
       assertEquals(80, normalsSource.getAccessor().getCount());
   }

   public void testParseGeometry() throws Exception{
       DAEParser suzanneParser = new DAEParser(SUZANNE_FILE);
       DAEGeometry suzanneGeometry = (DAEGeometry) suzanneParser.getElementByID("Suzanne-mesh");
       DAESource meshPositions = suzanneGeometry.getSourceBySemantic(DAESemantic.POSITION);
       assertEquals(507, meshPositions.getAccessor().getCount());
       assertEquals(3, meshPositions.getAccessor().getStride());

       DAESource meshNormals = suzanneGeometry.getSourceBySemantic(DAESemantic.NORMAL);
       assertEquals(968, meshNormals.getAccessor().getCount());
       assertEquals(968 * 3, meshNormals.getData().getCount());

       DAEIntArray indices = suzanneGeometry.getIndices();
       assertEquals(5808, indices.getCount());
   }

   public void testGetSemanticsTest() throws Exception{
       DAEParser cubeParser = new DAEParser(CUBE_FILE);
       DAEGeometry cubeGeometry = (DAEGeometry) cubeParser.getElementByID("Cube-mesh");
       DAESemantic[] cubeSemantics = cubeGeometry.getSemantics();
       assertEquals(2, cubeSemantics.length);
       Assert.assertArrayEquals(
               new DAESemantic[]{DAESemantic.POSITION, DAESemantic.NORMAL},
               cubeSemantics
       );
   }

   public void testBuildMeshSimple() throws Exception{
       DAEParser cubeParser = new DAEParser(CUBE_FILE);
       MockMeshBuilder meshBuilder = new MockMeshBuilder();
       cubeParser.buildMesh(meshBuilder, "Cube-mesh");
       assertEquals(36, meshBuilder.getVertexCount());
       assertEquals(36, meshBuilder.getIndicesCount());
   }

    public void testGetEffect() throws Exception{
        DAEParser lettersParser = new DAEParser(LETTERS_FILE);
        DAEEffect redMatEffect = (DAEEffect) lettersParser.getElementByID("RED_MAT-effect");
        Assert.assertArrayEquals(new float[]{0.64f, 0f, 0f, 1f}, redMatEffect.diffuseColor, 0.01f);
        Assert.assertArrayEquals(new float[]{1f, 1f, 1f, 1f}, redMatEffect.specularColor, 0.01f);
        assertEquals(13f, redMatEffect.shininess);

        DAEEffect greenMatEffect = (DAEEffect) lettersParser.getElementByID("GREEN_MAT-effect");
        Assert.assertArrayEquals(new float[]{.5f, .5f, .5f, 1f}, greenMatEffect.specularColor, 0.01f);
        assertEquals(50f, greenMatEffect.shininess);
    }

    public void testGetSceneNodes() throws Exception{
        DAEParser lettersParser = new DAEParser(LETTERS_FILE);
        DAEScene lettersScene = (DAEScene) lettersParser.getElementByID("Scene");
        DAESceneNode[] lettersNodes = lettersScene.getNodes();
        String[] lettersNodesIDs = new String[lettersNodes.length];
        for (int i = 0; i < lettersNodes.length; i++)
            lettersNodesIDs[i] = lettersNodes[i].getId();
        Assert.assertArrayEquals(
                new String[]{"LETTER_R", "LETTER_B", "LETTER_G", "GROUND"},
                lettersNodesIDs
        );
    }
}
