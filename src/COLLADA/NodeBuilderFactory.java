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

    private static class ParentBuilder implements NodeBuilder{
        DAEParent parent = new DAEParent();

        @Override
        public void beginBuild(Attributes attributes) {}
        @Override
        public void setContent(String content) {}

        @Override
        public void addChild(String tagName, DAEElement childElement) {
            parent.addChild(tagName, childElement);
        }
        @Override
        public DAEElement getBuildResult() {
            return parent;
        }
    }

    private static class FloatArrayBuilder implements NodeBuilder{
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
        public void addChild(String tagName, DAEElement childElement) {}

        @Override
        public DAEElement getBuildResult() {
            return buildResult;
        }
    }
    private static class SourceBuilder implements NodeBuilder{
        DAESource buildResult;

        @Override
        public void beginBuild(Attributes attributes) {
            buildResult = new DAESource(
                    attributes.getValue("id")
            );
        }

        @Override
        public void setContent(String content) {}

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
    private static class AccessorBuilder implements NodeBuilder{
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
        public void setContent(String content) {}

        @Override
        public void addChild(String tagName, DAEElement childElement) {}

        @Override
        public DAEElement getBuildResult() {
            return buildResult;
        }
    }
    private static class GeometryBuilder implements NodeBuilder{
        DAEGeometry buildResult;
        @Override
        public void beginBuild(Attributes attributes) {
            buildResult = new DAEGeometry(
                    attributes.getValue("id")
            );
        }

        @Override
        public void setContent(String content) {}

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
    private static class InputBuilder implements NodeBuilder{
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
        public void setContent(String content) {}

        @Override
        public void addChild(String tagName, DAEElement childElement) {}

        @Override
        public DAEElement getBuildResult() {
            return buildResult;
        }
    }
    private static class IndicesBuilder implements NodeBuilder{
        DAEIntArray indices;
        @Override
        public void beginBuild(Attributes attributes) {}

        @Override
        public void setContent(String content) {
            indices = new DAEIntArray(parseIntArray(content));
        }

        @Override
        public void addChild(String tagName, DAEElement childElement) {}

        @Override
        public DAEElement getBuildResult() {
            return indices;
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
        return new ParentBuilder();
    }
}
