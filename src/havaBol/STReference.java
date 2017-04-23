package havaBol;

public class STReference extends STEntry
{
	STEntry referencedSymbol;
	String type;
	public STReference(String symbol, int primClassif, int subClassif, int environmentVector) 
	{
		super(symbol, primClassif, subClassif, environmentVector);
		this.referencedSymbol = new STEntry("", primClassif, subClassif, environmentVector);
		// TODO Auto-generated constructor stub
	}
}
