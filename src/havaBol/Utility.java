package havaBol;

public class Utility {
	
	public static boolean isNumeric(ResultValue value)
	{
		if (value.type.equals(Type.INT))
		{
			return true;
		}
		else if (value.type.equals(Type.FLOAT))
		{
			return true;
		}
		return false;
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
		if (target.type.equals(value.type)) // If the types are the same assignment is simple
		{
			target.internalValue = value.internalValue;
			return;
		}
		switch (target.type)
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
		
		if(parser.bShowAssign)
		{
			Utility.printAssign(parser, target, value);
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
		ResultValue newResult = new ResultValue(Type.STRING);
		
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
		if (leftValue.type.equals(Type.INT)) // Integer case
		{
			temp = Utility.coerceToInt(parser, rightValue);
			int minuend = Integer.parseInt(leftValue.internalValue);
			int subtrahend = Integer.parseInt(temp);
			int result = minuend - subtrahend;
			subResult.internalValue = String.valueOf(result);
		}
		else if (leftValue.type.equals(Type.FLOAT)) // Float case
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
		if (leftValue.type.equals(Type.INT)) // Integer case
		{
			temp = Utility.coerceToInt(parser, rightValue);
			int addend1 = Integer.parseInt(leftValue.internalValue);
			int addend2 = Integer.parseInt(temp);
			int result = addend1 + addend2;
			subResult.internalValue = String.valueOf(result);
		}
		else if (leftValue.type.equals(Type.FLOAT)) // Float case
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
		if (leftValue.type.equals(Type.INT)) // Integer case
		{
			temp = Utility.coerceToInt(parser, rightValue);
			int multiplier = Integer.parseInt(leftValue.internalValue);
			int multiplicand = Integer.parseInt(temp);
			int result = multiplier * multiplicand;
			subResult.internalValue = String.valueOf(result);
		}
		else if (leftValue.type.equals(Type.FLOAT)) // Float case
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
		if (leftValue.type.equals(Type.INT)) // Integer case
		{
			temp = Utility.coerceToInt(parser, rightValue);
			int base = Integer.parseInt(leftValue.internalValue);
			int power = Integer.parseInt(temp);
			int result = (int)Math.pow(base,  power);
			subResult.internalValue = String.valueOf(result);
		}
		else if (leftValue.type.equals(Type.FLOAT)) // Float case
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
		if (leftValue.type.equals(Type.INT)) // Integer case
		{
			temp = Utility.coerceToInt(parser, rightValue);
			int dividend = Integer.parseInt(leftValue.internalValue);
			int divisor = Integer.parseInt(temp);
			int result = dividend / divisor;
			subResult.internalValue = String.valueOf(result);
		}
		else if (leftValue.type.equals(Type.FLOAT)) // Float case
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
	
	/**
	 * Evaluate a less than comparison on two ResultValues
	 * 
	 * @param parser
	 * @param operandOne
	 * @param operandTwo
	 * @return
	 * @throws ParserException 
	 */
	public static ResultValue lessThan(Parser parser, ResultValue operandOne, ResultValue operandTwo) throws ParserException 
	{
		
		ResultValue res = new ResultValue(Type.BOOL);
		String temp;
		
		switch (operandOne.type)
		{
		case Type.INT:
			temp = Utility.coerceToInt(parser, operandTwo);
			int iOp1 = Integer.parseInt(operandOne.internalValue);
			int iOp2 = Integer.parseInt(temp);
			if (iOp1 < iOp2)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.FLOAT:
			temp = Utility.coerceToFloat(parser, operandTwo);
			double fOp1 = Double.parseDouble(operandOne.internalValue);
			double fOp2 = Double.parseDouble(temp);
			if (fOp1 < fOp2)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.STRING:
			int comResult = operandOne.internalValue.compareTo(operandTwo.internalValue);
			if (comResult < 0)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.BOOL:
			throw new ParserException(parser.scanner.lineNumber
					, "Can not apply comparison \'<\' to type \'" + Type.BOOL + "\'"
					, parser.scanner.sourceFileName);
		case Type.DATE:
			throw new ParserException(parser.scanner.lineNumber
					, "DATE type not implemented \'" + operandOne.internalValue + "\'"
					, parser.scanner.sourceFileName);
		default:
			throw new ParserException(parser.scanner.lineNumber
					, "Unknown type \'" + operandOne.type + "\'"
					, parser.scanner.sourceFileName);
				
		}
		return res;
	}

	
	/**
	 * Evaluate a greater than comparison on two ResultValues
	 * 
	 * @param parser
	 * @param operandOne
	 * @param operandTwo
	 * @return
	 * @throws ParserException 
	 */
	public static ResultValue greaterThan(Parser parser, ResultValue operandOne, ResultValue operandTwo) throws ParserException 
	{
		
		ResultValue res = new ResultValue(Type.BOOL);
		String temp;
		
		switch (operandOne.type)
		{
		case Type.INT:
			temp = Utility.coerceToInt(parser, operandTwo);
			int iOp1 = Integer.parseInt(operandOne.internalValue);
			int iOp2 = Integer.parseInt(temp);
			if (iOp1 > iOp2)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.FLOAT:
			temp = Utility.coerceToFloat(parser, operandTwo);
			double fOp1 = Double.parseDouble(operandOne.internalValue);
			double fOp2 = Double.parseDouble(temp);
			if (fOp1 > fOp2)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.STRING:
			int comResult = operandOne.internalValue.compareTo(operandTwo.internalValue);
			if (comResult > 0)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.BOOL:
			throw new ParserException(parser.scanner.lineNumber
					, "Can not apply comparison \'>\' to type \'" + Type.BOOL + "\'"
					, parser.scanner.sourceFileName);
		case Type.DATE:
			throw new ParserException(parser.scanner.lineNumber
					, "DATE type not implemented \'" + operandOne.internalValue + "\'"
					, parser.scanner.sourceFileName);
		default:
			throw new ParserException(parser.scanner.lineNumber
					, "Unknown type \'" + operandOne.type + "\'"
					, parser.scanner.sourceFileName);
				
		}
		return res;
	}
	
	/**
	 * Evaluate a equal to comparison on two ResultValues
	 * 
	 * @param parser
	 * @param operandOne
	 * @param operandTwo
	 * @return
	 * @throws ParserException 
	 */
	public static ResultValue eqalTo(Parser parser, ResultValue operandOne, ResultValue operandTwo) throws ParserException 
	{
		
		ResultValue res = new ResultValue(Type.BOOL);
		String temp;
		
		switch (operandOne.type)
		{
		case Type.INT:
			temp = Utility.coerceToInt(parser, operandTwo);
			int iOp1 = Integer.parseInt(operandOne.internalValue);
			int iOp2 = Integer.parseInt(temp);
			if (iOp1 == iOp2)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.FLOAT:
			temp = Utility.coerceToFloat(parser, operandTwo);
			double fOp1 = Double.parseDouble(operandOne.internalValue);
			double fOp2 = Double.parseDouble(temp);
			if (fOp1 == fOp2)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.BOOL: // In this case bool is the same as string
		case Type.STRING:
			int comResult = operandOne.internalValue.compareTo(operandTwo.internalValue);
			if (comResult == 0)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.DATE:
			throw new ParserException(parser.scanner.lineNumber
					, "DATE type not implemented \'" + operandOne.internalValue + "\'"
					, parser.scanner.sourceFileName);
		default:
			throw new ParserException(parser.scanner.lineNumber
					, "Unknown type \'" + operandOne.type + "\'"
					, parser.scanner.sourceFileName);
				
		}
		return res;
	}

	/**
	 * Evaluate a less than or equal to comparison on two ResultValues
	 * 
	 * @param parser
	 * @param operandOne
	 * @param operandTwo
	 * @return
	 * @throws ParserException 
	 */
	public static ResultValue lessThanEqalTo(Parser parser, ResultValue operandOne, ResultValue operandTwo) throws ParserException 
	{
		
		ResultValue res = new ResultValue(Type.BOOL);
		String temp;
		
		switch (operandOne.type)
		{
		case Type.INT:
			temp = Utility.coerceToInt(parser, operandTwo);
			int iOp1 = Integer.parseInt(operandOne.internalValue);
			int iOp2 = Integer.parseInt(temp);
			if (iOp1 <= iOp2)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.FLOAT:
			temp = Utility.coerceToFloat(parser, operandTwo);
			double fOp1 = Double.parseDouble(operandOne.internalValue);
			double fOp2 = Double.parseDouble(temp);
			if (fOp1 <= fOp2)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.STRING:
			int comResult = operandOne.internalValue.compareTo(operandTwo.internalValue);
			if (comResult <= 0)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.BOOL:
			throw new ParserException(parser.scanner.lineNumber
					, "Can not apply comparison \'<=\' to type \'" + Type.BOOL + "\'"
					, parser.scanner.sourceFileName);
		case Type.DATE:
			throw new ParserException(parser.scanner.lineNumber
					, "DATE type not implemented \'" + operandOne.internalValue + "\'"
					, parser.scanner.sourceFileName);
		default:
			throw new ParserException(parser.scanner.lineNumber
					, "Unknown type \'" + operandOne.type + "\'"
					, parser.scanner.sourceFileName);
				
		}
		return res;
	}
	
	/**
	 * Evaluate a less than or equal to comparison on two ResultValues
	 * 
	 * @param parser
	 * @param operandOne
	 * @param operandTwo
	 * @return
	 * @throws ParserException 
	 */
	public static ResultValue greaterThanEqalTo(Parser parser, ResultValue operandOne, ResultValue operandTwo) throws ParserException 
	{
		
		ResultValue res = new ResultValue(Type.BOOL);
		String temp;
		
		switch (operandOne.type)
		{
		case Type.INT:
			temp = Utility.coerceToInt(parser, operandTwo);
			int iOp1 = Integer.parseInt(operandOne.internalValue);
			int iOp2 = Integer.parseInt(temp);
			if (iOp1 >= iOp2)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.FLOAT:
			temp = Utility.coerceToFloat(parser, operandTwo);
			double fOp1 = Double.parseDouble(operandOne.internalValue);
			double fOp2 = Double.parseDouble(temp);
			if (fOp1 >= fOp2)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.STRING:
			int comResult = operandOne.internalValue.compareTo(operandTwo.internalValue);
			if (comResult >= 0)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.BOOL:
			throw new ParserException(parser.scanner.lineNumber
					, "Can not apply comparison \'>=\' to type \'" + Type.BOOL + "\'"
					, parser.scanner.sourceFileName);
		case Type.DATE:
			throw new ParserException(parser.scanner.lineNumber
					, "DATE type not implemented \'" + operandOne.internalValue + "\'"
					, parser.scanner.sourceFileName);
		default:
			throw new ParserException(parser.scanner.lineNumber
					, "Unknown type \'" + operandOne.type + "\'"
					, parser.scanner.sourceFileName);
				
		}
		return res;
	}
	
	/**
	 * Evaluate a less than or equal to comparison on two ResultValues
	 * 
	 * @param parser
	 * @param operandOne
	 * @param operandTwo
	 * @return
	 * @throws ParserException 
	 */
	public static ResultValue notEqalTo(Parser parser, ResultValue operandOne, ResultValue operandTwo) throws ParserException 
	{
		
		ResultValue res = new ResultValue(Type.BOOL);
		String temp;
		
		switch (operandOne.type)
		{
		case Type.INT:
			temp = Utility.coerceToInt(parser, operandTwo);
			int iOp1 = Integer.parseInt(operandOne.internalValue);
			int iOp2 = Integer.parseInt(temp);
			if (iOp1 != iOp2)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.FLOAT:
			temp = Utility.coerceToFloat(parser, operandTwo);
			double fOp1 = Double.parseDouble(operandOne.internalValue);
			double fOp2 = Double.parseDouble(temp);
			if (fOp1 != fOp2)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.BOOL: // In this case bool is the same as string
		case Type.STRING:
			int comResult = operandOne.internalValue.compareTo(operandTwo.internalValue);
			if (comResult != 0)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;	
		case Type.DATE:
			throw new ParserException(parser.scanner.lineNumber
					, "DATE type not implemented \'" + operandOne.internalValue + "\'"
					, parser.scanner.sourceFileName);
		default:
			throw new ParserException(parser.scanner.lineNumber
					, "Unknown type \'" + operandOne.type + "\'"
					, parser.scanner.sourceFileName);
				
		}
		return res;
	}
	
	public static ResultValue parseInt(Parser parser, Token intToken) throws ParserException {
		ResultValue res = new ResultValue(Type.INT);
		try {
			Integer.parseInt(intToken.tokenStr);
		} catch (Exception e)
		{
			throw new ParserException(parser.scanner.lineNumber
					, "Can not parse \'" + intToken.tokenStr + "\' as Int"
					, parser.scanner.sourceFileName);
		}
		res.internalValue = intToken.tokenStr;
		return res;
	}
	
	public static ResultValue parseFloat(Parser parser, Token floatToken) throws ParserException {
		
		ResultValue res = new ResultValue(Type.FLOAT);
		try {
			Double.parseDouble(floatToken.tokenStr);
		} catch (Exception e)
		{
			throw new ParserException(parser.scanner.lineNumber
					, "Can not parse \'" + floatToken.tokenStr + "\' as Float"
					, parser.scanner.sourceFileName);
		}
		
		res.internalValue = floatToken.tokenStr;
		return res;
	}

	public static ResultValue parseString(Parser parser, Token stringToken) throws ParserException {
		
		ResultValue res = new ResultValue(Type.STRING);
		if (stringToken.tokenStr == null)
		{
			throw new ParserException(parser.scanner.lineNumber
					, "Can not parse \'NULL\' as String"
					, parser.scanner.sourceFileName);
		}
		
		res.internalValue = stringToken.tokenStr;
		return res;
	}
	
	public static ResultValue parseBool(Parser parser, Token stringToken) throws ParserException {
		
		ResultValue res = new ResultValue(Type.BOOL);
		res.internalValue = stringToken.tokenStr;
		
		res.internalValue = coerceToBool(parser, res);
		
		return res;
	}
	
	public static ResultValue parseDate(Parser parser, Token dateToken) throws ParserException {
		
		ResultValue res = new ResultValue(Type.DATE);
		res.internalValue = dateToken.tokenStr;
		
		res.internalValue = coerceToDate(parser, res);
		
		return res;
	}

	public static ResultValue evaluateBinaryOperator(Parser parser, ResultValue operandOne, ResultValue operandTwo, Token operator) throws ParserException
	{
		ResultValue res = null;
		
		if (operator.primClassif != Token.OPERATOR)
		{
			throw new ParserException(parser.scanner.lineNumber
					, "Unable to evaluate non operator \'" + operator.tokenStr + "\'"
					, parser.scanner.sourceFileName);
		}
		
		switch (operator.tokenStr)
		{
		case "+":
			res = Utility.add(parser, operandOne, operandTwo);
			break;
		case "-":
			res = Utility.subtract(parser, operandOne, operandTwo);
			break;
		case "*":
			res = Utility.multiply(parser, operandOne, operandTwo);
			break;
		case "/":
			res = Utility.divide(parser, operandOne, operandTwo);
			break;
		case "^":
			res = Utility.exponentiate(parser, operandOne, operandTwo);
			break;
		case "<": 
			res = Utility.lessThan(parser,operandOne, operandTwo);
			break;
		case ">":
			res = Utility.greaterThan(parser, operandOne, operandTwo);
			break;
		case "<=":
			res = Utility.lessThanEqalTo(parser, operandOne, operandTwo);
			break;
		case ">=":
			res = Utility.greaterThanEqalTo(parser, operandOne, operandTwo);
			break;
		case "==":
			res = Utility.eqalTo(parser, operandOne, operandTwo);
			break;
		case "!=":
			res = Utility.notEqalTo(parser, operandOne, operandTwo);
			break;
		case "and":
			res = Utility.and(parser, operandOne, operandTwo);
			break;
		case "or":
			res = Utility.or(parser, operandOne, operandTwo);
			break;
			
			
		case "in": // TODO NOT YET IMPLEMENTED
		case "notin":
			throw new ParserException(parser.scanner.lineNumber
					, "NOT YET IMPLEMENTED"
					, parser.scanner.sourceFileName);
		default:
			throw new ParserException(parser.scanner.lineNumber
					, "Unknown operator \'" + operator.tokenStr + "\'"
					, parser.scanner.sourceFileName);
		}
		return res;
	}

	private static ResultValue or(Parser parser, ResultValue operandOne, ResultValue operandTwo) {
		// TODO Auto-generated method stub
		ResultValue res = new ResultValue(Type.BOOL);
		
		if (operandOne.internalValue.equals("T") && operandTwo.internalValue.equals("T"))
			res.internalValue = "T";
		else
			res.internalValue = "F";
		
		return res;
	}

	private static ResultValue and(Parser parser, ResultValue operandOne, ResultValue operandTwo) {

		ResultValue res = new ResultValue(Type.BOOL);
		
		if (operandOne.internalValue.equals("T") || operandTwo.internalValue.equals("T"))
			res.internalValue = "T";
		else
			res.internalValue = "F";
		
		return res;
	}

	public static ResultValue evaluateUnaryOperator(Parser parser, ResultValue operand, Token operator) throws ParserException 
	{
		ResultValue res = null;
		
		if (operator.primClassif != Token.OPERATOR)
		{
			throw new ParserException(parser.scanner.lineNumber
					, "Unable to evaluate non operator \'" + operator.tokenStr + "\'"
					, parser.scanner.sourceFileName);
		}
		
		switch (operator.tokenStr)
		{
		case "u-":
			
			res = new ResultValue(operand.type);
			if (!Utility.isNumeric(res))
				throw new ParserException(parser.scanner.lineNumber
						, "Can not compute negative of non numeric type \'" + res.type + "\'"
						, parser.scanner.sourceFileName);
			
			res.internalValue = "-" + operand.internalValue;
			break;
		case "not":
			res = new ResultValue(operand.type);
			if (!res.type.equals("Bool"))
				throw new ParserException(parser.scanner.lineNumber
						, "Can not negate non boolean type \'" + res.type + "\'"
						, parser.scanner.sourceFileName);
			if (operand.internalValue.equals("T"))
				res.internalValue = "F";
			else 
				res.internalValue = "T";
			break;
		default:
			throw new ParserException(parser.scanner.lineNumber
					, "Unknown operator \'" + operator.tokenStr + "\'"
					, parser.scanner.sourceFileName);
		}
		return res;
	}

	public static void printResult(Parser parser, ResultValue res) {
		
		System.out.println("On line " 
				+ parser.scanner.lineNumber + " expression evaluates to: "
				+ res.type + ", " 
				+ res.internalValue);
		
	}
	
	public static void printAssign(Parser parser, ResultValue target, ResultValue result)
	{
		System.out.println("On line " 
				+ parser.scanner.lineNumber + " variable " 
				+ target.internalValue + " of type " 
				+ target.type + " has value " 
				+ result.internalValue);
	}
	
	
}
