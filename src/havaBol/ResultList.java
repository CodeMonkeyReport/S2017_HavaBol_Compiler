package havaBol;

public class ResultList extends ResultValue {

	ResultValue internalValueList[]; // For array types we need a list of values
	int iMaxSize;
	int iCurrentSize;
	
	public ResultList(String type, int iMaxSize)
	{
		super(type);
		this.iMaxSize = iMaxSize;
	}
}
