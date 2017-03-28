package havaBol;

import java.util.HashMap;

/**
 * This class manages the life cycle of variables and provides values when needed.
 * <p>
 * TODO This class will need to remove variables from the varTable when they go out of scope in the future.
 * <p>
 * 
 * @author ckw273
 *
 */
public class StorageManager {

	HashMap <String, ResultValue> varTable = new HashMap<String, ResultValue>();
	
	/**
	 * Get a ResultValue back from the table.
	 * <p>
	 * 
	 * @param varString - Identifier used as a key into the varTable hash map
	 * @return
	 */
	public ResultValue getVariableValue(String varString)
	{
		ResultValue res = varTable.get(varString);

		return res;
	}
	
	/**
	 * Put a new ResultValue into the varTable OR replace an existing value.
	 * <p>
	 * 
	 * @param varString - Identifier used as a key into the varTable hash map
	 * @param value - Value to insert or replace in the hash map
	 */
	public void putVariableValue(String varString, ResultValue value)
	{
		varTable.put(varString, value);
	}
}
