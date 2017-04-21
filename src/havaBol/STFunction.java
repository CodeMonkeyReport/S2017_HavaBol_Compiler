package havaBol;

import java.util.ArrayList;



public class STFunction extends STEntry 
{
	Token positionToken;
	String returnType;
	int definedBy;
	int numArgs;
	ArrayList<STEntry> parmList;
	
	public STFunction(String symbol, String returnType, int definedBy, int numArgs, int environmentVector)
	{
		super(symbol, Token.FUNCTION, definedBy, environmentVector);

		this.returnType = returnType;
		this.definedBy = definedBy;
		this.numArgs = numArgs;
	}
	
	public STFunction(String symbol, String returnType, int definedBy, int numArgs, ArrayList<STEntry> parmList, Token positionToken, int environmentVector)
	{
		super(symbol, Token.FUNCTION, definedBy, environmentVector);
		this.positionToken = positionToken;
		this.parmList = parmList;
	}
}
