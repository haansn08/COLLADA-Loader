package tests;

import junit.framework.TestCase;
import production.DAEElementBuilder;
import production.DAEFloatArray;
import production.DAEIntArray;

/**
 * Created by Stefan Haan on 8/4/14.
 */
public class DAEElementBuilder_Tests extends TestCase{
    public void testFloatArrayBuilder(){
        DAEFloatArray sourceElement = (DAEFloatArray)
                DAEElementBuilder.build("float_array","1 1 -1.213 1 -1.14 -1.214 -1 -1 -1 -1 1 -1 1 1");
        assertEquals(sourceElement.getCount(), 14);
    }
    public void testIntArrayBuilder(){
        DAEIntArray indexData = (DAEIntArray)
                DAEElementBuilder.build("p","0 0 1 0 2 0 7 1 6 1 5 1 4 2 5 2 1");
        assertEquals(indexData.getCount(), 17);
    }
}
