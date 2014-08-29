package COLLADA;

/**
 * Created by Stefan Haan on 8/29/14.
 */
public class DAEColor implements DAEElement{
    private String sid;
    public float[] data;

    public String getSid() {
        return sid;
    }

    DAEColor(String sid){
        this.sid = sid;
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
