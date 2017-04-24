package havaBol;

public class STControl extends STEntry {

	int subClassif;

	public STControl(String symbol, int subClassif, int environmentVector) {
		super(symbol, Token.CONTROL, subClassif, environmentVector);

		this.subClassif = subClassif;
	}
}
