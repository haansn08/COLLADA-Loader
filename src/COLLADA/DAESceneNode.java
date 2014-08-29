package COLLADA;

/**
 * Created by Stefan Haan on 8/29/14.
 */
public class DAESceneNode implements DAEElement{
    private String id;
    @Override
    public boolean hasId() {
        return true;
    }

    @Override
    public String getId() {
        return id;
    }
    DAESceneNode(String id){
        this.id = id;
    }
}
