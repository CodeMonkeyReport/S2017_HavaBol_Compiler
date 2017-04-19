package havaBol;

public class ResultValue implements Cloneable {
	
	public String internalValue = "";
	public String type = "";
	
	public int structure = Type.SCALAR; // Default to primitive type
	public String terminatingStr = "";
	
	public ResultValue()
	{
		
	}
	public ResultValue(String type)
	{
		this.type = type;
	}
	public String getInternalValue()
	{
		return this.internalValue;
	}
	
	public ResultValue Clone()
	{
		ResultValue res = new ResultValue(this.type);
		res.internalValue = this.internalValue;
		res.structure = this.structure;
		res.terminatingStr = this.terminatingStr;
		
		return res;
	}
	public void set(Parser parser, ResultValue newValue) throws ParserException
	{
		this.internalValue = newValue.internalValue;
	}
}
