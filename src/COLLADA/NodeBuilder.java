package COLLADA;

import org.xml.sax.Attributes;

/**
 * Created by Stefan Haan on 8/8/14.
 */
interface NodeBuilder {
    void beginBuild(Attributes attributes);
    void setContent(String content);
    void addChild(String tagName, DAEElement childElement);
    DAEElement getBuildResult();
}
