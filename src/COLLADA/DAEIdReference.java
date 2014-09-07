package COLLADA;

/**
 * Created by Stefan Haan on 9/7/14.
 */
public class DAEIdReference implements DAEElement{
    private String referenceId;

    public String getReferenceId() {
        return referenceId;
    }
    DAEIdReference(String referenceId){
        this.referenceId = referenceId;
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
