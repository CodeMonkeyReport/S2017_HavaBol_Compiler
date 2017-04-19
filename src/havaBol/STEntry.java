package havaBol;

public class STEntry {
	String symbol;
	int primClassif;
	int subClassif;
	public int declaredSize; // For use in arrays
	public int structureType;
	
	public STEntry(String symbol, int primClassif, int subClassif)
	{
		this.symbol = symbol;
		this.primClassif = primClassif;
		this.subClassif = subClassif;
	}

}
