package utilities;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;

/**
 * Contains static methods used to convert arrays to buffers of corresponding
 * data types, used to load vertex data into VBOs
 * 
 * @author Chris Hurt
 * @version 1.1
 */
public class BufferConverter {

    /**
     * Converts the given float array into a new FloatBuffer object
     * 
     * @param values
     *            The array of floats to be converted
     * @return A new float buffer containing the given float values
     */
    public static FloatBuffer arrayToBuffer(float[] values) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
        buffer.put(values);
        buffer.flip();
        return buffer;
    }


    /**
     * Converts the given int array into a new IntBuffer object
     * 
     * @param values
     *            The array of ints to be converted
     * @return A new int buffer containing the given int values
     */
    public static IntBuffer arrayToBuffer(int[] values) {
        IntBuffer buffer = BufferUtils.createIntBuffer(values.length);
        buffer.put(values);
        buffer.flip();
        return buffer;
    }

}
