package COLLADA;

/**
 * Created by Stefan Haan on 8/29/14.
 */
public class DAEEffect implements DAEElement{
    private String id;

    public float[] diffuseColor;
    public float[] specularColor;
    public float shininess;

    @Override
    public boolean hasId() {
        return true;
    }

    @Override
    public String getId() {
        return id;
    }

    DAEEffect(String id){
        this.id = id;
    }
}
