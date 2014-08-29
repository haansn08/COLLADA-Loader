package COLLADA;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stefan Haan on 8/29/14.
 */
public class DAEScene implements DAEElement{
    private String id;
    private List<DAESceneNode> nodes = new ArrayList<DAESceneNode>();

    @Override
    public boolean hasId() {
        return true;
    }

    @Override
    public String getId() {
        return id;
    }
    DAEScene(String id){
        this.id = id;
    }

    public DAESceneNode[] getNodes() {
        DAESceneNode[] sceneNodes = new DAESceneNode[nodes.size()];
        nodes.toArray(sceneNodes);
        return sceneNodes;
    }

    void addNode(DAESceneNode node){
        nodes.add(node);
    }
}
