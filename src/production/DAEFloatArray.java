package production;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * Created by Stefan Haan on 8/4/14.
 */
public class DAEFloatArray implements DAEElement{
    private FloatBuffer floatBuffer;
    DAEFloatArray(float[] source)
    {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 * source.length);
        floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(source);
        floatBuffer.flip();
    }
    public int getCount() {
        return floatBuffer.capacity();
    }
}
