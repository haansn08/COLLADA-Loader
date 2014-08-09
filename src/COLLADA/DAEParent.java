package COLLADA;

import static java.util.AbstractMap.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Stefan Haan on 8/8/14.
 */
public class DAEParent implements DAEElement {
    //Tag - Element pairs
    List<SimpleEntry<String, DAEElement>> children = new LinkedList<SimpleEntry<String, DAEElement>>();

    void addChild(String tagName, DAEElement child){
        children.add(
                new SimpleEntry<String, DAEElement>(tagName, child)
        );
    }

    @Override
    public boolean hasId() {
        return false;
    }

    @Override
    public String getId() {
        return null;
    }

    DAEElement getChildById(String id){
        for (SimpleEntry<String, DAEElement> tagElementPair : children) {
            DAEElement child = tagElementPair.getValue();
            if(child instanceof DAEParent){
                DAEElement childResult = ((DAEParent) child).getChildById(id);
                if (childResult != null)
                    return childResult;
            }
            else {
                if (child.hasId() && child.getId().equalsIgnoreCase(id))
                    return child;
            }
        }
        return null;
    }

    Collection<DAEElement> getChildrenByTagName(String tagName){
        List<DAEElement> results = new LinkedList<DAEElement>();
        for (SimpleEntry<String, DAEElement> tagElementPair : children) {
            DAEElement child = tagElementPair.getValue();
            if (child instanceof DAEParent)
                results.addAll(((DAEParent) child).getChildrenByTagName(tagName));
            else if (tagElementPair.getKey().equalsIgnoreCase(tagName))
                results.add(child);
        }
        return results;
    }

    DAEElement getFirstChild(){
        return children.get(0).getValue();
    }

}
