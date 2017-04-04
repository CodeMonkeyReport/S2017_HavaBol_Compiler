package havaBol;

public class ResultList extends ResultValue {

	ResultValue internalValueList[]; // For array types we need a list of values
	public int iMaxSize;
	public int iCurrentSize;
								// -1 indicates that we are talking about the whole array

	public ResultList(String type, int iMaxSize)
	{
		super(type);
		this.iMaxSize = iMaxSize;
		if (iMaxSize >= Type.ARRAY_UNBOUNDED)
		{
			this.internalValueList = new ResultValue[1];
			this.iCurrentSize = 0;
		}
		else
		{
			this.internalValueList = new ResultValue[this.iMaxSize];
			this.iCurrentSize = 0;
		}
	}

	/**
	 * Puts a result value into the array and checks bounds for errors
	 * @param index
	 * @param value
	 * @throws ParserException 
	 */
	public void insert(Parser parser, int index, ResultValue value) throws ParserException
	{
		if (index >= this.iMaxSize)
			throw new ParserException(parser.scanner.currentToken.iSourceLineNr
					, "Array index out of bounds: " + index
					, parser.scanner.sourceFileName);
		
		if (this.iMaxSize == Type.ARRAY_UNBOUNDED && this.internalValueList.length < index) // Unbounded array needs to grow
		{
			int maxSize = this.internalValueList.length;
			while (maxSize < index)
				maxSize *= 2;
			
			ResultValue tempResultArray[] = new ResultValue[maxSize];
			System.arraycopy(this.internalValueList, 0, tempResultArray, 0, this.internalValueList.length);
			
		}
		else
		{
			
		}
		this.internalValueList[index] = value;
		this.iCurrentSize++;
	}
	
	public ResultValue get(Parser parser, int index) throws ParserException
	{
		if (this.iCurrentSize < index)
			throw new ParserException(parser.scanner.currentToken.iSourceLineNr
					, "Array index out of bounds: " + index
					, parser.scanner.sourceFileName);
		
		return internalValueList[index];
	}
	
	public String getInternalValue()
	{
		int i;
		StringBuilder sb = new StringBuilder();
		for(i = 0; i < internalValueList.length -1; i++)
		{
			sb.append(internalValueList[i].internalValue);
			sb.append(",");
		}
		sb.append(internalValueList[i].internalValue);
		return sb.toString();
	}
}
