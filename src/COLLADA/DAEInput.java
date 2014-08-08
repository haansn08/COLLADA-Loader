package COLLADA;

/**
 * Created by Stefan Haan on 8/8/14.
 */
public class DAEInput implements DAEElement {
    private DAESemantic semantic;
    private String sourceID;
    private int offset;

    public void setSemantic(DAESemantic semantic) {
        this.semantic = semantic;
    }

    public DAESemantic getSemantic() {
        return semantic;
    }

    @Override
    public boolean hasId() {
        return false;
    }

    @Override
    public String getId() {
        return null;
    }

    public String getSourceID() {
        return sourceID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
