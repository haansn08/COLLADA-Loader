package COLLADA;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * Created by Stefan Haan on 8/5/14.
 */
public class DAEIntArray implements DAEElement {
    int[] data;
    DAEIntArray(int[] data){
        this.data = data;
    }
    public int getCount() {
        return data.length;
    }

    @Override
    public boolean hasId() {
        return false;
    }

    @Override
    public String getId() {
        return null;
    }
}
