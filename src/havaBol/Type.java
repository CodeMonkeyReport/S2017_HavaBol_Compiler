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
	public static final int INT = 0;
	public static final int FLOAT = 1;
	public static final int STRING = 2;
	public static final int BOOL = 3;
	public static final int DATE = 4;
	
	// Variable reference type
	public static final int VALUE = 1;
	public static final int REFERENCE = 2;
	
}
