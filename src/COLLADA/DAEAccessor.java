package COLLADA;

/**
 * Created by Stefan Haan on 8/8/14.
 */
public class DAEAccessor implements DAEElement {
    private int count;
    private int stride;

    @Override
    public boolean hasId() {
        return false;
    }

    @Override
    public String getId() {
        return null;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setStride(int stride) {
        this.stride = stride;
    }

    public int getCount() {
        return count;
    }

    public int getStride() {
        return stride;
    }
}
