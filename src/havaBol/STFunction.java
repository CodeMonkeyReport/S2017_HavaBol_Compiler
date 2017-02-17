package havaBol;

import java.util.ArrayList;



public class STFunction extends STEntry 
{
	int returnType;
	int definedBy;
	int numArgs;
	ArrayList<?> parmList;
	SymbolTable ST;
	
	public STFunction(String symbol, int returnType, int definedBy, int numArgs)
	{
		super(symbol, Token.FUNCTION);
		
		this.returnType = returnType;
		this.definedBy = definedBy;
		this.numArgs = numArgs;
	}
	
	public STFunction(String symbol, int returnType, int definedBy, int numArgs, ArrayList<?> parmList, SymbolTable ST)
	{
		
		this(symbol, returnType, definedBy, numArgs);
		
		this.parmList = parmList;
		this.ST = ST;
	}

}
