package tests;

import COLLADA.*;
import junit.framework.TestCase;

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

   public void testBuildMesh() throws Exception{
       DAEParser cubeParser = new DAEParser(CUBE_FILE);
       MockMeshBuilder meshBuilder = new MockMeshBuilder();
       sphereParser.buildMesh(meshBuilder, "Cube-mesh");
       assertEquals(8, ((DAESource)meshBuilder.getPositionSource()).getAccessor().getCount());
       assertEquals(12, ((DAESource)meshBuilder.getNormalsSource()).getAccessor().getCount());
       assertEquals(36, meshBuilder.getIndicesCalls());
   }
}
