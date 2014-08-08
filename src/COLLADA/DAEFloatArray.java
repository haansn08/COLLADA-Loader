package COLLADA;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * Created by Stefan Haan on 8/4/14.
 */
public class DAEFloatArray implements DAEElement{
    private String id;
    private FloatBuffer floatBuffer;
    DAEFloatArray(String id, float[] source)
    {
        this.id = id;

        ByteBuffer byteBuffer = ByteBuffer.allocate(4 * source.length);
        floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(source);
        floatBuffer.flip();
    }
    public int getCount() {
        return floatBuffer.capacity();
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
