package havaBol;

public class Utility {
	
	public static boolean isNumeric(ResultValue value)
	{
		if (value.type.equals("Int"))
		{
			return true;
		}
		else if (value.type.equals("Float"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
