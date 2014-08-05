package tests;

import junit.framework.TestCase;
import production.DAEElementBuilder;
import production.DAEFloatArray;

/**
 * Created by Stefan Haan on 8/4/14.
 */
public class DAEElementBuilder_Tests extends TestCase{
       public void testSourceBuilder(){
        DAEFloatArray sourceElement = (DAEFloatArray)
                DAEElementBuilder.build("float_array","1 1 -1 1 -1 -1 -1 -1 -1 -1 1 -1 1 1");
        assertEquals(sourceElement.getCount(), 14);
    }
}
