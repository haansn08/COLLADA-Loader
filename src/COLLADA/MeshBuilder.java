package COLLADA;

/**
 * Created by shaan on 8/10/14.
 */
public interface MeshBuilder {
    public void onVertexData (DAESemantic semantic, float[] data);
    public void onIndexData (int index);
}
