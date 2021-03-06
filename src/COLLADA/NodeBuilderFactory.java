package COLLADA;

import org.xml.sax.Attributes;

import java.util.Collection;

/**
 * Created by Stefan Haan on 8/8/14.
 */
class NodeBuilderFactory {
    private static float[] parseFloatArray(String floats) {
        String[] singleFloatStrings = floats.split(" ");
        float[] result = new float[singleFloatStrings.length];
        for (int i = 0; i < singleFloatStrings.length; i++) {
            result[i] = Float.parseFloat(singleFloatStrings[i]);
        }
        return result;
    }

    private static int[] parseIntArray(String ints) {
        String[] singleIntStrings = ints.split(" ");
        int[] result = new int[singleIntStrings.length];
        for (int i = 0; i < singleIntStrings.length; i++) {
            result[i] = Integer.parseInt(singleIntStrings[i]);
        }
        return result;
    }

    private static class ParentBuilder extends NodeBuilder{
        DAEParent parent = new DAEParent();
        @Override
        public void addChild(String tagName, DAEElement childElement) {
            parent.addChild(tagName, childElement);
        }
        @Override
        public DAEElement getBuildResult() {
            return parent;
        }
    }

    private static class FloatArrayBuilder extends NodeBuilder{
        String id;
        DAEFloatArray buildResult;

        @Override
        public void beginBuild(Attributes attributes) {
            id = attributes.getValue("id");
        }

        @Override
        public void setContent(String content) {
            float[] source = parseFloatArray(content);
            buildResult = new DAEFloatArray(id, source);
        }

        @Override
        public DAEElement getBuildResult() {
            return buildResult;
        }
    }
    private static class SourceBuilder extends NodeBuilder{
        DAESource buildResult;

        @Override
        public void beginBuild(Attributes attributes) {
            buildResult = new DAESource(
                    attributes.getValue("id")
            );
        }
        @Override
        public void addChild(String tagName, DAEElement childElement) {
            if (tagName.equalsIgnoreCase("float_array"))
                buildResult.setData((DAEFloatArray) childElement);
            if (tagName.equalsIgnoreCase("technique_common"))
                addAccessor((DAEParent) childElement);

        }

        private void addAccessor(DAEParent childElement) {
            DAEAccessor accessor = (DAEAccessor) childElement.getFirstChild();
            buildResult.setAccessor(accessor);
        }

        @Override
        public DAEElement getBuildResult() {
            return buildResult;
        }
    }
    private static class AccessorBuilder extends NodeBuilder{
        DAEAccessor buildResult = new DAEAccessor();
        @Override
        public void beginBuild(Attributes attributes) {
            buildResult.setCount(
                    Integer.parseInt(attributes.getValue("count"))
            );
            buildResult.setStride(
                    Integer.parseInt(attributes.getValue("stride"))
            );
        }
        @Override
        public DAEElement getBuildResult() {
            return buildResult;
        }
    }
    private static class GeometryBuilder extends NodeBuilder{
        DAEGeometry buildResult;
        @Override
        public void beginBuild(Attributes attributes) {
            buildResult = new DAEGeometry(
                    attributes.getValue("id")
            );
        }
        @Override
        public void addChild(String tagName, DAEElement childElement) {
            if (tagName.equalsIgnoreCase("mesh"))
            {
                DAEParent parent = (DAEParent) childElement;
                addSources(parent);
                addIndices(parent);
            }
        }

        private void addIndices(DAEParent parent) {
            Collection<DAEElement> indicesElement = parent.getChildrenByTagName("p");
            DAEIntArray indices = (DAEIntArray) indicesElement.iterator().next(); //first and only element
            buildResult.setIndices(indices);
        }

        private void addSources(DAEParent parent) {
            Collection<DAEElement> daeInputs = parent.getChildrenByTagName("input");
            for (DAEElement inputElement : daeInputs)
                addSource(parent, (DAEInput) inputElement);
        }

        private void addSource(DAEParent parent, DAEInput inputElement) {
            buildResult.setSource(
                    inputElement.getSemantic(),
                    (DAESource) parent.getChildById(inputElement.getSourceID())
            );
        }

        @Override
        public DAEElement getBuildResult() {
            return buildResult;
        }
    }
    private static class InputBuilder extends NodeBuilder{
        DAEInput buildResult = new DAEInput();
        @Override
        public void beginBuild(Attributes attributes) {
            buildResult.setSemantic(
                    DAESemantic.valueOf(
                            attributes.getValue("semantic")
                    )
            );
            buildResult.setSourceID(
                    attributes.getValue("source").substring(1)
            );

            String offset = attributes.getValue("offset");
            if (offset != null)
                buildResult.setOffset(
                        Integer.parseInt(offset)
                );
        }

        @Override
        public DAEElement getBuildResult() {
            return buildResult;
        }
    }
    private static class IndicesBuilder extends NodeBuilder{
        DAEIntArray indices;

        @Override
        public void setContent(String content) {
            indices = new DAEIntArray(parseIntArray(content));
        }

        @Override
        public DAEElement getBuildResult() {
            return indices;
        }
    }
    private static class EffectBuilder extends NodeBuilder{
        DAEEffect result;
        @Override
        DAEElement getBuildResult() {
            return result;
        }
        @Override
        void beginBuild(Attributes attributes) {
            result = new DAEEffect(
                    attributes.getValue("id")
            );
        }

        @Override
        void addChild(String tagName, DAEElement childElement) {
            if (tagName.equalsIgnoreCase("profile_COMMON")){
                addColors((DAEParent) childElement);
                addValues((DAEParent) childElement);
            }
        }

        private void addValues(DAEParent profileElement) {
            Collection<DAEElement> values =  profileElement.getChildrenByTagName("float");
            for (DAEElement value : values) {
                DAEFloat valueFloat = (DAEFloat) value;
                String sid = valueFloat.getSid();
                if (sid.equalsIgnoreCase("shininess"))
                    result.shininess = valueFloat.value;
            }
        }

        private void addColors(DAEParent profileElement) {
            Collection<DAEElement> colors = profileElement.getChildrenByTagName("color");
            for (DAEElement colorElement : colors) {
                DAEColor color = (DAEColor) colorElement;
                String sid = color.getSid();
                if (sid.equalsIgnoreCase("diffuse"))
                    result.diffuseColor = color.data;
                if (sid.equalsIgnoreCase("specular"))
                    result.specularColor = color.data;
            }
        }
    }
    private static class FloatBuilder extends NodeBuilder{
        DAEFloat result;

        @Override
        void beginBuild(Attributes attributes) {
            result = new DAEFloat(attributes.getValue("sid"));
        }

        @Override
        void setContent(String content) {
            result.value = Float.parseFloat(content);
        }

        @Override
        DAEElement getBuildResult() {
            return result;
        }
    }
    private static class ColorBuilder extends NodeBuilder {
        DAEColor result;
        @Override
        void beginBuild(Attributes attributes) {
            result = new DAEColor(attributes.getValue("sid"));
        }

        @Override
        void setContent(String content) {
            result.data = parseFloatArray(content);
        }

        @Override
        DAEElement getBuildResult() {
            return result;
        }
    }
    private static class SceneNodeBuilder extends NodeBuilder{
        DAESceneNode result;

        @Override
        void beginBuild(Attributes attributes) {
            result = new DAESceneNode(
                    attributes.getValue("id")
            );
        }

        @Override
        void addChild(String tagName, DAEElement childElement) {
            if (tagName.equalsIgnoreCase("matrix"))
                result.transformation = ((DAEFloatArray) childElement).data;
            if (tagName.equalsIgnoreCase("instance_geometry"))
                result.meshId = ((DAEIdReference)childElement).getReferenceId();
            if (tagName.equalsIgnoreCase("instance_material"))
                result.materialID = ((DAEIdReference) childElement).getReferenceId();
        }

        @Override
        DAEElement getBuildResult() {
            return result;
        }
    }
    private static class SceneBuilder extends NodeBuilder{
        DAEScene result;
        @Override
        void beginBuild(Attributes attributes) {
            result = new DAEScene(
                    attributes.getValue("id")
            );
        }
        @Override
        void addChild(String tagName, DAEElement childElement) {
            if (tagName.equalsIgnoreCase("node"))
                result.addNode((DAESceneNode) childElement);
        }
        @Override
        DAEElement getBuildResult() {
            return result;
        }
    }
    private static class InstanceGeometryBuilder extends NodeBuilder{
        private String attributeName;
        DAEIdReference result;

        InstanceGeometryBuilder(String attributeName){
            this.attributeName = attributeName;
        }

        @Override
        void beginBuild(Attributes attributes) {
            result = new DAEIdReference(
                    attributes.getValue(attributeName).substring(1)
            );
        }
        @Override
        DAEElement getBuildResult() {
            return result;
        }
    }

    static NodeBuilder getNodeBuilder(String tagName){
        if (tagName.equalsIgnoreCase("float_array"))
            return new FloatArrayBuilder();
        if (tagName.equalsIgnoreCase("source"))
            return new SourceBuilder();
        if (tagName.equalsIgnoreCase("accessor"))
            return new AccessorBuilder();
        if (tagName.equalsIgnoreCase("geometry"))
            return new GeometryBuilder();
        if (tagName.equalsIgnoreCase("input"))
            return new InputBuilder();
        if (tagName.equalsIgnoreCase("p"))
            return new IndicesBuilder();
        if (tagName.equalsIgnoreCase("effect"))
            return new EffectBuilder();
        if (tagName.equalsIgnoreCase("float"))
            return new FloatBuilder();
        if (tagName.equalsIgnoreCase("color"))
            return new ColorBuilder();
        if (tagName.equalsIgnoreCase("node"))
            return new SceneNodeBuilder();
        if (tagName.equalsIgnoreCase("visual_scene"))
            return new SceneBuilder();
        if (tagName.equalsIgnoreCase("matrix"))
            return new FloatArrayBuilder();
        if (tagName.equalsIgnoreCase("instance_geometry"))
            return new InstanceGeometryBuilder("url");
        if (tagName.equalsIgnoreCase("instance_material"))
            return new InstanceGeometryBuilder("target");
        return new ParentBuilder();
    }

}
