package COLLADA;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * Created by Stefan Haan on 8/4/14.
 */
public class DAEFloatArray implements DAEElement{
    private String id;
    float[] data;
    DAEFloatArray(String id, float[] source)
    {
        this.id = id;
        data = source;
    }
    public int getCount() {
        return data.length;
    }

    @Override
    public boolean hasId() {
        return true;
    }
    @Override
    public String getId() {
        return id;
    }
}
