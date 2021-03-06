package COLLADA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Stefan Haan on 8/7/14.
 */
public class DAEGeometry implements DAEElement {
    private String id;
    private List<DAESemantic> semantics = new ArrayList<DAESemantic>();
    private Map<DAESemantic, DAESource> sourceMap = new HashMap<DAESemantic, DAESource>();
    private DAEIntArray indices;

    DAEGeometry(String id){
        this.id = id;
    }

    void setIndices(DAEIntArray indices) {
        this.indices = indices;
    }
    void setSource (DAESemantic semantic, DAESource source){
        sourceMap.put(semantic, source);
        if (semantic != DAESemantic.VERTEX) //Just refers to positions
            semantics.add(semantic);
    }
    public DAEIntArray getIndices() {
        return indices;
    }
    public DAESource getSourceBySemantic(DAESemantic semantic) {
        return sourceMap.get(semantic);
    }


    @Override
    public boolean hasId() {
        return true;
    }
    @Override
    public String getId() {
        return id;
    }

    public DAESemantic[] getSemantics() {
       DAESemantic[] result = new DAESemantic[semantics.size()];
       semantics.toArray(result);
       return result;
    }
}
