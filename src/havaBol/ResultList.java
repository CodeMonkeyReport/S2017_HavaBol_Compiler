package havaBol;

public class ResultList extends ResultValue {

	ResultValue internalValueList[]; // For array types we need a list of values
	public int iMaxSize;
	public int iCurrentSize;
	public int iReferencedIndex = -1; 	// Will be used to talk about a specific index in the array
								// -1 indicates that we are talking about the whole array
	
	public ResultList(String type, int iMaxSize)
	{
		super(type);
		this.iMaxSize = iMaxSize;
	}
}
