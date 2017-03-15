package havaBol;

public class ResultValue {
	
	public String internalValue = "";
	public String type = "";
	
	public int structure = Type.PRIMITIVE; // Default to primitive type
	public String terminatingStr = "";
	
	public ResultValue()
	{
		
	}
	public ResultValue(String type)
	{
		this.type = type;
	}
}
