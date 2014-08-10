package COLLADA;

/**
 * Created by Stefan Haan on 8/6/14.
 */
public class DAESource implements DAEElement{
    private DAEFloatArray data;
    private DAEAccessor accessor;
    private String id;

    public DAESource(String id) {
        this.id = id;
    }

    void setData(DAEFloatArray data) {
        this.data = data;
    }

    public DAEFloatArray getData() {
        return data;
    }

    @Override
    public boolean hasId() {
        return true;
    }

    @Override
    public String getId() {
        return id;
    }

    public DAEAccessor getAccessor() {
        return accessor;
    }

    void setAccessor(DAEAccessor accessor) {
        this.accessor = accessor;
    }

    public float[] getDataByIndex(int index) {
        float[] result = new float[accessor.getStride()];
        for (int i = 0; i < result.length; i++) {
            result[i] = data.data[index*result.length + i];
        }
        return result;
    }

}
