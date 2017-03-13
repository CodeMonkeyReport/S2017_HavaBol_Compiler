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
		return false;
	}

	public static int getType(ResultValue value)
	{
		if (value.type.equals("Int"))
		{
			return Type.INT;
		}
		else if (value.type.equals("Float"))
		{
			return Type.FLOAT;
		}
		else if (value.type.equals("String"))
		{
			return Type.STRING;
		}
		else if (value.type.equals("Bool"))
		{
			return Type.BOOL;
		}
		else if (value.type.equals("Date"))
		{
			return Type.DATE;
		}
		return -1;
	}
	
	/**
	 * Handles variable assignment.
	 * <p>
	 * 
	 * @param parser - Used for error generation
	 * @param target - The variable we are assigning into
	 * @param value - The value we are assigning
	 * @throws ParserException - If we are somehow unable to assign, such as a type mismatch
	 */
	public static void assign(Parser parser, ResultValue target, ResultValue value) throws ParserException 
	{
		// TODO Auto-generated method stub
		if (target.type.equals(value.type)) // If the types are the same assignment is simple
		{
			target.internalValue = value.internalValue;
			return;
		}
		switch (Utility.getType(target))
		{
		case (Type.INT):
			target.internalValue = coerceToInt(parser, value);
			break;
		case (Type.FLOAT):
			target.internalValue = coerceToFloat(parser, value);
			break;
		case (Type.STRING):
			target.internalValue = value.internalValue; // If its a string we don't care
			break;
		case (Type.BOOL):
			target.internalValue = coerceToBool(parser, value);
			break;
		case (Type.DATE):
			target.internalValue = coerceToDate(parser, value);
			break;
		default: // Not a known type
			break;
		}
	}

	/**
	 * NOT IMPLEMENTED
	 * <p>
	 * @param parser
	 * @param value
	 * @return
	 */
	private static String coerceToDate(Parser parser, ResultValue value) 
	{
		// TODO NOT IMPLEMENTED
		return null;
	}

	/**
	 * coerces a value into a Bool type and throws an exception if it can not be coerced.
	 * <p>
	 * 
	 * @param parser - Used for error generation
	 * @param value - ResultValue to be coerced
	 * @return
	 * @throws ParserException - Value can not be parsed as a Bool
	 */
	private static String coerceToBool(Parser parser, ResultValue value) throws ParserException 
	{

		if (value.internalValue.equals("T"))
		{
			return "T";
		}
		else if (value.internalValue.equals("F"))
		{
			return "F";
		}
		else
		{
			throw new ParserException(parser.scanner.lineNumber
					, "Can not parse \'" + value.internalValue + "\' as Bool"
					, parser.scanner.sourceFileName);
		}
	}

	/**
	 * coerces a value into a Float type and throws an exception if it can not be coerced.
	 * <p>
	 * 
	 * @param parser -  Used for error generation
	 * @param value - ResultValue to be coerced
	 * @return
	 * @throws ParserException - Value can not be parsed as a Float
	 */
	private static String coerceToFloat(Parser parser, ResultValue value) throws ParserException 
	{
		try {
			Double.parseDouble(value.internalValue);
			return value.internalValue;
		} catch (Exception e)
		{
			// Do nothing
		}
		throw new ParserException(parser.scanner.lineNumber
				, "Can not parse \'" + value.internalValue + "\' as Int"
				, parser.scanner.sourceFileName);
	}

	/**
	 * coerces a value into a Int type and throws an exception if it can not be coerced.
	 * <p>
	 * 
	 * @param parser -  Used for error generation
	 * @param value - ResultValue to be coerced
	 * @return
	 * @throws ParserException - Value can not be parsed as a Int
	 */
	public static String coerceToInt(Parser parser, ResultValue value) throws ParserException 
	{
		int temp;
		try {
			Integer.parseInt(value.internalValue);
			return value.internalValue;
		} catch (Exception e)
		{
			// Do nothing
		}
		try {
			temp = (int)Double.parseDouble(value.internalValue);
			return Integer.toString(temp);
		} catch (Exception e)
		{
			// Do nothing
		}
		throw new ParserException(parser.scanner.lineNumber
				, "Can not parse \'" + value.internalValue + "\' as Int"
				, parser.scanner.sourceFileName);
	}

	/**
	 * Used to concatenate one string onto another
	 * <p>
	 * 
	 * @param parser - Used for error generation
	 * @param first - The first part of the new string
	 * @param second - The second part of the new string
	 * @return
	 */
	public static ResultValue concat(Parser parser, ResultValue first, ResultValue second) 
	{
		ResultValue newResult = new ResultValue(first.type);
		
		StringBuilder sb = new StringBuilder(first.internalValue);
		sb.append(second.internalValue);
		newResult.internalValue = sb.toString();
		
		return newResult;
	}

	/**
	 * Subtract one numeric value from another.
	 * <p>
	 * 
	 * @param parser - Used for error generation
	 * @param leftValue - Left hand side value we are subtracting from (Minuend).
	 * @param rightValue - Right hand side value we are subtracting (Subtrahend).
	 * @return
	 * @throws ParserException
	 */
	public static ResultValue subtract(Parser parser, ResultValue leftValue, ResultValue rightValue) throws ParserException 
	{
		ResultValue subResult = new ResultValue(leftValue.type);
		String temp;
		if (leftValue.type.equals("Int")) // Integer case
		{
			temp = Utility.coerceToInt(parser, rightValue);
			int minuend = Integer.parseInt(leftValue.internalValue);
			int subtrahend = Integer.parseInt(temp);
			int result = minuend - subtrahend;
			subResult.internalValue = String.valueOf(result);
		}
		else if (leftValue.type.equals("Float")) // Float case
		{
			temp = Utility.coerceToFloat(parser, rightValue);
			double minuend = Double.parseDouble(leftValue.internalValue);
			double subtrahend = Double.parseDouble(temp);
			double result = minuend - subtrahend;
			subResult.internalValue = String.valueOf(result);
		}
		else 
		{
			throw new ParserException(parser.scanner.lineNumber
					, "Can not subtract from variable of type \'" + leftValue.type + "\'"
					, parser.scanner.sourceFileName);
		}
		return subResult;
	}

	/**
	 * Add one numeric value to another.
	 * <p>
	 * 
	 * @param parser - Used for error generation
	 * @param leftValue - Left hand side value we are adding (Addend).
	 * @param rightValue - Right hand side value we are adding (Addend).
	 * @return
	 * @throws ParserException
	 */
	public static ResultValue add(Parser parser, ResultValue leftValue, ResultValue rightValue) throws ParserException 
	{
		ResultValue subResult = new ResultValue(leftValue.type);
		String temp;
		if (leftValue.type.equals("Int")) // Integer case
		{
			temp = Utility.coerceToInt(parser, rightValue);
			int addend1 = Integer.parseInt(leftValue.internalValue);
			int addend2 = Integer.parseInt(temp);
			int result = addend1 + addend2;
			subResult.internalValue = String.valueOf(result);
		}
		else if (leftValue.type.equals("Float")) // Float case
		{
			temp = Utility.coerceToFloat(parser, rightValue);
			double addend1 = Double.parseDouble(leftValue.internalValue);
			double addend2 = Double.parseDouble(temp);
			double result = addend1 + addend2;
			subResult.internalValue = String.valueOf(result);
		}
		else 
		{
			throw new ParserException(parser.scanner.lineNumber
					, "Can not add to variable of type \'" + leftValue.type + "\'"
					, parser.scanner.sourceFileName);
		}
		return subResult;
	}

	/**
	 * Multiply one numeric value with another.
	 * <p>
	 * 
	 * @param parser - Used for error generation
	 * @param leftValue - Left hand side value we are multiplying (Multiplier).
	 * @param rightValue - Right hand side value we are multiplying (Multiplicand).
	 * @return
	 * @throws ParserException
	 */
	public static ResultValue multiply(Parser parser, ResultValue leftValue, ResultValue rightValue) throws ParserException 
	{
		ResultValue subResult = new ResultValue(leftValue.type);
		String temp;
		if (leftValue.type.equals("Int")) // Integer case
		{
			temp = Utility.coerceToInt(parser, rightValue);
			int multiplier = Integer.parseInt(leftValue.internalValue);
			int multiplicand = Integer.parseInt(temp);
			int result = multiplier * multiplicand;
			subResult.internalValue = String.valueOf(result);
		}
		else if (leftValue.type.equals("Float")) // Float case
		{
			temp = Utility.coerceToFloat(parser, rightValue);
			double multiplier = Double.parseDouble(leftValue.internalValue);
			double multiplicand = Double.parseDouble(temp);
			double result = multiplier * multiplicand;
			subResult.internalValue = String.valueOf(result);
		}
		else 
		{
			throw new ParserException(parser.scanner.lineNumber
					, "Can not multiply variable of type \'" + leftValue.type + "\'"
					, parser.scanner.sourceFileName);
		}
		return subResult;
	}

	/**
	 * Raise a variable to the power of another.
	 * <p>
	 * 
	 * @param parser - Used for error generation
	 * @param leftValue - Left hand side value we are exponentiating (Base).
	 * @param rightValue - Right hand side value we are exponentiating (Power).
	 * @return
	 * @throws ParserException
	 */
	public static ResultValue exponentiate(Parser parser, ResultValue leftValue, ResultValue rightValue) throws ParserException 
	{
		ResultValue subResult = new ResultValue(leftValue.type);
		String temp;
		if (leftValue.type.equals("Int")) // Integer case
		{
			temp = Utility.coerceToInt(parser, rightValue);
			int base = Integer.parseInt(leftValue.internalValue);
			int power = Integer.parseInt(temp);
			int result = (int)Math.pow(base,  power);
			subResult.internalValue = String.valueOf(result);
		}
		else if (leftValue.type.equals("Float")) // Float case
		{
			temp = Utility.coerceToFloat(parser, rightValue);
			double base = Double.parseDouble(leftValue.internalValue);
			double power = Double.parseDouble(temp);
			double result = Math.pow(base, power);
			subResult.internalValue = String.valueOf(result);
		}
		else 
		{
			throw new ParserException(parser.scanner.lineNumber
					, "Can not exponentiate variable of type \'" + leftValue.type + "\'"
					, parser.scanner.sourceFileName);
		}
		return subResult;
	}

	/**
	 * Divide one numeric value by another.
	 * <p>
	 * 
	 * @param parser - Used for error generation
	 * @param leftValue - Left hand side value we are dividing (Dividend)
	 * @param rightValue - Right hand side value we are dividing by (Divisor)
	 * @return
	 * @throws ParserException
	 */
	public static ResultValue divide(Parser parser, ResultValue leftValue, ResultValue rightValue) throws ParserException 
	{
		ResultValue subResult = new ResultValue(leftValue.type);
		String temp;
		if (leftValue.type.equals("Int")) // Integer case
		{
			temp = Utility.coerceToInt(parser, rightValue);
			int dividend = Integer.parseInt(leftValue.internalValue);
			int divisor = Integer.parseInt(temp);
			int result = dividend / divisor;
			subResult.internalValue = String.valueOf(result);
		}
		else if (leftValue.type.equals("Float")) // Float case
		{
			temp = Utility.coerceToFloat(parser, rightValue);
			double dividend = Double.parseDouble(leftValue.internalValue);
			double divisor = Double.parseDouble(temp);
			double result = dividend / divisor;
			subResult.internalValue = String.valueOf(result);
		}
		else 
		{
			throw new ParserException(parser.scanner.lineNumber
					, "Can not divide variable of type \'" + leftValue.type + "\'"
					, parser.scanner.sourceFileName);
		}
		return subResult;
	}

}
