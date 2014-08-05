package production;

/**
 * Created by Stefan Haan on 8/4/14.
 */
public class DAEElementBuilder {

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

    public static DAEElement build(String tag, String content) {
        if (tag.equalsIgnoreCase("float_array"))
            return new DAEFloatArray(
                    parseFloatArray(content)
            );
        if (tag.equalsIgnoreCase("p"))
            return new DAEIntArray(
                    parseIntArray(content)
            );
        return null;
    }


}
