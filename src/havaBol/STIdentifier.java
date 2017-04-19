package havaBol;

public class STIdentifier extends STEntry 
{
	/*declaration type
	 *  1: Non primitive (struct)
	 * 	2: int
	 * 	3: float
	 * 	4: String
	 *  5: Bool
	 *  6: Date
	 */
	String type;
	
	String valueString;
	
	
	/* parameter type
	 * 	1: not a parm
	 *  2: by reference
	 *  3: by value
	 */
	int referenceType;
	
	//non-local base address ref
	int nonLocal;
		
	public STIdentifier(String symbol, String valueString, int structureType, int referenceType, int nonlocal )
	{
		super(symbol, Token.OPERAND, Token.IDENTIFIER);

		this.valueString = valueString;
		this.structureType = structureType;
		this.referenceType = referenceType;
		this.nonLocal = nonlocal;
	}
}
