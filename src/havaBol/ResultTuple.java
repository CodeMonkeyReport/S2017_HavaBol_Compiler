package havaBol;

import java.util.HashMap;

public class ResultTuple extends ResultValue implements Cloneable
{
		HashMap<String, ResultValue> memberHash = new HashMap<String, ResultValue>();
		
		public ResultTuple(String type)
		{
			super(type);
		}
		
		public void addMember(String symbol, ResultValue member)
		{
			memberHash.put(symbol, member);
		}
		
		public ResultValue getMember(String key)
		{
			return memberHash.get(key);
		}
		
		public boolean checkMember(String symbol, ResultValue member)
		{
			return (memberHash.get(symbol) != null);
		}
		
		public ResultTuple Clone()
		{
			ResultTuple res = new ResultTuple(this.type);
			res.memberHash = (HashMap<String, ResultValue>) this.memberHash.clone();
			return res;
		}
		
		
		public void set(Parser parser, ResultValue value) throws ParserException
		{
			if (value.type.equals(this.type))
			{
				this.memberHash = (HashMap<String, ResultValue>) ((ResultTuple)value).memberHash.clone();
			}
		}
}
