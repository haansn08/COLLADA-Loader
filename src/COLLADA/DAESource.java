package COLLADA;

/**
 * Created by Stefan Haan on 8/6/14.
 */
public class DAESource implements DAEElement{
    private DAEFloatArray data;
    private int count;
    private int stride;

    public void setCount(int count) {
        this.count = count;
    }

    void setStride(int stride) {
        this.stride = stride;
    }

    void setData(DAEFloatArray data) {
        this.data = data;
    }

    public DAEFloatArray getData() {
        return data;
    }

    public int getCount() {
        return count;
    }

    public int getStride() {
        return stride;
    }
}
