package COLLADA;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * Created by Stefan Haan on 8/5/14.
 */
public class DAEIntArray implements DAEElement {
    IntBuffer dataBuffer;
    DAEIntArray (int[] data){
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 * data.length);
        dataBuffer = byteBuffer.asIntBuffer();
    }
    public int getCount() {
        return dataBuffer.capacity();
    }
}
