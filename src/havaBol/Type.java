package havaBol;

/**
 * This is a static class used for references to different type declarations.
 * <p>
 * 
 * @author ckw273
 *
 */
public class Type {

	// Variable structure types
	public static final int PRIMITIVE = 0;
	public static final int ARRAY_FIXED = 1;
	public static final int ARRAY_UNBOUNDED = 2;
	public static final int STRUCT = 3; // May never be implemented
	
	// Primitive variable value types
	public static final String INT = "Int";
	public static final String FLOAT = "Float";
	public static final String STRING = "String";
	public static final String BOOL = "Bool";
	public static final String DATE = "Date";
	
	// Variable reference type
	public static final int VALUE = 1;
	public static final int REFERENCE = 2;
	
}
