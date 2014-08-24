package COLLADA;

import org.xml.sax.Attributes;

/**
 * Created by Stefan Haan on 8/8/14.
 */
abstract class NodeBuilder {
    void beginBuild(Attributes attributes){};
    void setContent(String content){};
    void addChild(String tagName, DAEElement childElement){};
    abstract DAEElement getBuildResult();
}
