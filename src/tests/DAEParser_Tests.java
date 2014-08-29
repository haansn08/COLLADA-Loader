package tests;

import COLLADA.*;
import junit.framework.TestCase;
import org.junit.Assert;

/**
 * Created by Stefan Haan on 8/4/14.
 */
public class DAEParser_Tests extends TestCase {
    private final static String CUBE_FILE = "res/Cube.dae";
    DAEParser cubeParser = new DAEParser(CUBE_FILE);
    private final static String SUZANNE_FILE = "res/Suzanne.dae";
    DAEParser suzanneParser = new DAEParser(SUZANNE_FILE);
    private final static String SPHERE_UV_FILE = "res/SphereUV.dae";
    DAEParser sphereParser = new DAEParser(SPHERE_UV_FILE);
    private final static String LETTERS_FILE = "res/rgb.dae";
    DAEParser lettersParser = new DAEParser(LETTERS_FILE);

    public DAEParser_Tests() throws Exception {}

    public void testParseFloatArray() throws Exception {
        DAEFloatArray cubeMeshPositions = (DAEFloatArray) cubeParser.getElementByID("Cube-mesh-positions-array");
        assertEquals(24, cubeMeshPositions.getCount());

        DAEFloatArray cubeMeshNormals = (DAEFloatArray) cubeParser.getElementByID("Cube-mesh-normals-array");
        assertEquals(36, cubeMeshNormals.getCount());
    }

   public void testParseSource() throws Exception{
       DAESource positionSource = (DAESource)sphereParser.getElementByID("Icosphere-mesh-positions");
       DAEFloatArray positions = positionSource.getData();
       assertEquals(126, positions.getCount());
       assertEquals(42, positionSource.getAccessor().getCount());
       assertEquals(3, positionSource.getAccessor().getStride());

       DAESource normalsSource = (DAESource)sphereParser.getElementByID("Icosphere-mesh-normals");
       assertEquals(80, normalsSource.getAccessor().getCount());
   }

   public void testParseGeometry() throws Exception{
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
       DAEGeometry cubeGeometry = (DAEGeometry) cubeParser.getElementByID("Cube-mesh");
       DAESemantic[] cubeSemantics = cubeGeometry.getSemantics();
       assertEquals(2, cubeSemantics.length);
       Assert.assertArrayEquals(
               new DAESemantic[]{DAESemantic.POSITION, DAESemantic.NORMAL},
               cubeSemantics
       );
   }

   public void testBuildMeshSimple() throws Exception{
       MockMeshBuilder meshBuilder = new MockMeshBuilder();
       cubeParser.buildMesh(meshBuilder, "Cube-mesh");
       assertEquals(36, meshBuilder.getVertexCount());
       assertEquals(36, meshBuilder.getIndicesCount());
   }

    public void testGetEffect() throws Exception{
        DAEEffect redMatEffect = (DAEEffect) lettersParser.getElementByID("RED_MAT-effect");
        Assert.assertArrayEquals(new float[]{0.64f, 0f, 0f, 1f}, redMatEffect.diffuseColor, 0.01f);
        Assert.assertArrayEquals(new float[]{1f, 1f, 1f, 1f}, redMatEffect.specularColor, 0.01f);
        assertEquals(13f, redMatEffect.shininess);

        DAEEffect greenMatEffect = (DAEEffect) lettersParser.getElementByID("GREEN_MAT-effect");
        Assert.assertArrayEquals(new float[]{.5f, .5f, .5f, 1f}, greenMatEffect.specularColor, 0.01f);
        assertEquals(50f, greenMatEffect.shininess);
    }

    public void testGetSceneNodes() throws Exception{
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
