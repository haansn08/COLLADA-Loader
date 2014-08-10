package tests;

import COLLADA.DAESemantic;
import COLLADA.DAESource;
import COLLADA.MeshBuilder;

/**
 * Created by shaan on 8/10/14.
 */
public class MockMeshBuilder implements MeshBuilder {

    private int vertexCount;
    private int indicesCount;

    @Override
    public void onVertexData(DAESemantic semantic, float[] data) {
        if(semantic == DAESemantic.POSITION)
            vertexCount++;
    }

    @Override
    public void onIndexData(int index) {
        indicesCount++;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public int getIndicesCount() {
        return indicesCount;
    }
}
