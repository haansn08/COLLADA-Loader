package COLLADA;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stefan Haan on 8/7/14.
 */
public class DAEGeometry implements DAEElement {
    private Map<DAESemantic, DAESource> sourceMap = new HashMap<DAESemantic, DAESource>();
    private DAEIntArray indices;

    void setIndices(DAEIntArray indices) {
        this.indices = indices;
    }
    public DAEIntArray getIndices() {
        return indices;
    }

    public DAESource getSourceBySemantic(DAESemantic semantic) {
        return sourceMap.get(semantic);
    }
    void setSource (DAESemantic semantic, DAESource source){
        sourceMap.put(semantic, source);
    }

}
