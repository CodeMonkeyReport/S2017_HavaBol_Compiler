package havaBol;


public class STControl extends STEntry {
	
	int subClassif;
	
	public STControl(String symbol, int subClassif)
	{
		super(symbol, Token.CONTROL);
		
		this.subClassif = subClassif;
	}
	
	

}
