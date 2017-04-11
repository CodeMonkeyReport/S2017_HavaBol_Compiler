package havaBol;

public class ResultList extends ResultValue {

	ResultValue internalValueList[]; // For array types we need a list of values
	public int iMaxSize;
	public int iCurrentSize;
	public ResultValue defaultValue;
								// -1 indicates that we are talking about the whole array

	public ResultList(String type, int iMaxSize)
	{
		super(type);
		this.iMaxSize = iMaxSize;
		this.structure = Type.ARRAY;
		if (iMaxSize >= Type.ARRAY_UNBOUNDED)
		{
			this.internalValueList = new ResultValue[1];
			this.defaultValue = new ResultValue(type);
			this.internalValueList[0] = this.defaultValue.Clone();
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
		if (index < 0) // Translate negative indexing
		{
			index = this.iCurrentSize + index;
		}
		
		if (index >= this.iMaxSize || index < 0)
			throw new ParserException(parser.scanner.currentToken.iSourceLineNr
					, "Array index out of bounds: " + index
					, parser.scanner.sourceFileName);
		
		if (this.iMaxSize == Type.ARRAY_UNBOUNDED && this.internalValueList.length < index) // Unbounded array needs to grow
		{
			int maxSize = this.internalValueList.length;
			while (maxSize < index)
				maxSize *= 2;
			
			ResultValue tempResultArray[] = new ResultValue[maxSize];
			for (int i = this.iCurrentSize; i < maxSize; i++)
				tempResultArray[i] = this.defaultValue.Clone();
			
			System.arraycopy(this.internalValueList, 0, tempResultArray, 0, this.internalValueList.length);
			this.internalValueList = tempResultArray;
			this.iCurrentSize = index+1;
		}
		else
		{
			this.iCurrentSize++;		
		}
		this.internalValueList[index] = value;
	}
	
	public ResultValue get(Parser parser, int index) throws ParserException
	{
		if (index < 0) // Translate negative indexing
		{
			index = this.iCurrentSize + index;
		}
		
		if (this.iMaxSize < index || index < 0)
			throw new ParserException(parser.scanner.currentToken.iSourceLineNr
					, "Array index out of bounds: " + index
					, parser.scanner.sourceFileName);
		
		else if (this.iMaxSize == Type.ARRAY_UNBOUNDED && this.iCurrentSize < index)
			this.insert(parser, index, defaultValue.Clone());
		
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
