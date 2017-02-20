package havaBol;

public class STIdentifier extends STEntry {
	
	/*declaration type
	 * 	2: int
	 * 	3: float
	 * 	4: String
	 *  5: Bool
	 *  6: Date
	 */
	int dclType;
	
	/* data structure
	 *  1: primitive
	 *  2: fixed array
	 *  3: unbounded array
	 */
	int structure;
	
	/* parameter type
	 * 	1: not a parm
	 *  2: by reference
	 *  3: by value
	 */
	int parm;
	
	//non-local base address ref
	int nonLocal;
	
	public STIdentifier(String symbol, int dclType, int structure, int parm, int nonlocal )
	{
		
		super(symbol, Token.OPERAND, 0);
		
		this.dclType = dclType;
		this.structure = structure;
		this.parm = parm;
		this.nonLocal = nonlocal;
	}
	
	
	
}
