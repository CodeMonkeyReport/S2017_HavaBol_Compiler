package havaBol;

public class ResultReference extends ResultValue implements Cloneable
{
	public ResultReference(String typeStr) {
		super(typeStr);
	}

	STReference referencedEntry;
	
	public ResultReference Clone()
	{
		ResultReference ref = new ResultReference(this.type);
		ref.referencedEntry = this.referencedEntry;
		return ref;
	}
}
