package production;

/**
 * Created by Stefan Haan on 8/4/14.
 */
public class DAEElementBuilder {

    private static float[] parseFloatArray(String floats) {
        String[] singleFloatTexts = floats.split(" ");
        float[] result = new float[singleFloatTexts.length];
        for (int i = 0; i < singleFloatTexts.length; i++) {
            result[i] = Float.parseFloat(singleFloatTexts[i]);
        }
        return result;
    }

    public static DAEElement build(String tag, String content) {
        if (tag.equalsIgnoreCase("float_array"))
            return new DAEFloatArray(
                    parseFloatArray(content)
            );
        return null;
    }
}
