package COLLADA;

/**
 * Created by Stefan Haan on 8/29/14.
 */
public class DAEFloat implements DAEElement {
    private String sid;
    public float value;

    DAEFloat(String sid){
        this.sid = sid;
    }

    public String getSid() {
        return sid;
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
