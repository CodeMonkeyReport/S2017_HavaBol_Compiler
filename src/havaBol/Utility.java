package havaBol;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

	public static boolean isNumeric(Parser parser, ResultValue value) throws ParserException {
		if (value.type.equals(Type.INT)) {
			return true;
		} else if (value.type.equals(Type.FLOAT)) {
			return true;
		} else if (value.type.equals(Type.STRING)) {
			ResultValue res = convertStringToNumeric(parser, value);
		}
		return false;
	}

	private static ResultValue convertStringToNumeric(Parser parser, ResultValue value) throws ParserException {
		ResultValue res;
		int tempInt;
		double tempDouble;
		if (!value.type.equals("String")) {
			throw new ParserException(parser.scanner.lineNumber,
					"Error type missmatch expected \'String\' found \'" + value.internalValue + "\'",
					parser.scanner.sourceFileName);
		}
		try {
			res = new ResultValue(Type.INT);
			tempInt = Integer.parseInt(value.internalValue);
			res.internalValue = Integer.toString(tempInt);
		} catch (Exception e) {
			// Do nothing
		}

		try { // If it's not an Int, make it a Float instead
			res = new ResultValue(Type.FLOAT);
			tempDouble = Double.parseDouble(value.internalValue);
			res.internalValue = Double.toString(tempDouble);

		} catch (Exception e) {
			throw new ParserException(parser.scanner.lineNumber,
					"Can not parse \'" + value.internalValue + "\' as Numeric", parser.scanner.sourceFileName);
		}
		return res;
	}

	/**
	 * Handles variable assignment.
	 * <p>
	 * 
	 * @param parser
	 *            - Used for error generation
	 * @param target
	 *            - The variable we are assigning into
	 * @param value
	 *            - The value we are assigning
	 * @throws ParserException
	 *             - If we are somehow unable to assign, such as a type mismatch
	 */
	public static void assign(Parser parser, ResultValue target, ResultValue value) throws ParserException {
		ResultValue tempRes;
		if (target instanceof ResultList && value instanceof ResultList) // array
																			// to
																			// array
																			// assignment
		{
			target.set(parser, value);
			return;
		}
		if (target instanceof ResultTuple && value instanceof ResultTuple) {
			target.set(parser, value);
			return;
		}
		if (target instanceof ResultReference) {
			target = parser.callStack.get(((ResultReference) target).referencedEntry.environmentVector).storageManager
					.getVariableValue(((ResultReference) target).referencedEntry.referencedSymbol.symbol);
			if (target == null)
				return;
		}
		if (value instanceof ResultReference) {
			value = parser.callStack.get(((ResultReference) value).referencedEntry.environmentVector).storageManager
					.getVariableValue(((ResultReference) value).referencedEntry.referencedSymbol.symbol);
			if (value == null)
				return;
		}
		if (target.type.equals(value.type)) // If the types are the same
											// assignment is simple
		{
			value = Utility.coerceToType(parser, value.type, value);
			target.set(parser, value);
			return;
		}
		switch (target.type) {
		case (Type.INT):
			tempRes = coerceToInt(parser, value);
			target.set(parser, tempRes);
			break;
		case (Type.FLOAT):
			tempRes = coerceToFloat(parser, value);
			target.set(parser, tempRes);
			break;
		case (Type.STRING):
			target.set(parser, value); // If its a string we don't care
			break;
		case (Type.BOOL):
			tempRes = coerceToBool(parser, value);
			target.set(parser, tempRes);
			break;
		case (Type.DATE):
			tempRes = coerceToDate(parser, value);
			target.set(parser, tempRes);
			break;
		default: // Not a known type
			break;
		}

		if (parser.bShowAssign) {
			Utility.printAssign(parser, target, value);
		}
	}

	/**
	 * Handles converting to string type.
	 * <p>
	 * All types are coercible into strings so this simply creates a new object
	 * and returns it.
	 * 
	 * @param parser
	 * @param resultValue
	 * @return
	 */
	private static ResultValue coerceToString(Parser parser, ResultValue resultValue) {
		ResultValue res = new ResultValue(Type.STRING);
		res.internalValue = resultValue.internalValue;
		return res;
	}

	/**
	 * NOT IMPLEMENTED
	 * <p>
	 * 
	 * @param parser
	 * @param value
	 * @return
	 */
	private static ResultValue coerceToDate(Parser parser, ResultValue value) throws ParserException{
		ResultValue res = new ResultValue(Type.DATE);
		res.internalValue = value.internalValue;
		if(!checkForDate(value))
		{
			throw new ParserException(parser.scanner.lineNumber,
					"Token "+value.internalValue+" is not a valid date",
					parser.scanner.sourceFileName);
		}
		return res;
	}

	/**
	 * coerces a value into a Bool type and throws an exception if it can not be
	 * coerced.
	 * <p>
	 * 
	 * @param parser
	 *            - Used for error generation
	 * @param value
	 *            - ResultValue to be coerced
	 * @return
	 * @throws ParserException
	 *             - Value can not be parsed as a Bool
	 */
	private static ResultValue coerceToBool(Parser parser, ResultValue value) throws ParserException {
		ResultValue res = new ResultValue(Type.BOOL);

		if (value.internalValue.equals("T")) {
			res.internalValue = "T";
		} else if (value.internalValue.equals("F")) {
			res.internalValue = "F";
		} else {
			throw new ParserException(parser.scanner.lineNumber,
					"Can not parse \'" + value.internalValue + "\' as Bool", parser.scanner.sourceFileName);
		}
		return res;
	}

	/**
	 * coerces a value into a Float type and throws an exception if it can not
	 * be coerced.
	 * <p>
	 * 
	 * @param parser
	 *            - Used for error generation
	 * @param value
	 *            - ResultValue to be coerced
	 * @return
	 * @throws ParserException
	 *             - Value can not be parsed as a Float
	 */
	private static ResultValue coerceToFloat(Parser parser, ResultValue value) throws ParserException {
		ResultValue res = new ResultValue(Type.FLOAT);
		double tempDouble;
		try {
			tempDouble = Double.parseDouble(value.internalValue);
			res.internalValue = Double.toString(tempDouble);
			return res;
		} catch (Exception e) {
			throw new ParserException(parser.scanner.lineNumber, "Can not parse \'" + value.internalValue + "\' as Int",
					parser.scanner.sourceFileName);
		}
	}

	/**
	 * coerces a value into a Int type and throws an exception if it can not be
	 * coerced.
	 * <p>
	 * 
	 * @param parser
	 *            - Used for error generation
	 * @param value
	 *            - ResultValue to be coerced
	 * @return
	 * @throws ParserException
	 *             - Value can not be parsed as a Int
	 */
	public static ResultValue coerceToInt(Parser parser, ResultValue value) throws ParserException {
		ResultValue res = new ResultValue(Type.INT);
		int tempInt;
		try {
			tempInt = Integer.parseInt(value.internalValue);
			res.internalValue = Integer.toString(tempInt);
			return res;
		} catch (Exception e) {
			// Do nothing
		}
		try {
			tempInt = (int) Double.parseDouble(value.internalValue);
			res.internalValue = Integer.toString(tempInt);
			return res;
		} catch (Exception e) {
			// Do nothing
		}
		throw new ParserException(parser.scanner.lineNumber, "Can not parse \'" + value.internalValue + "\' as Int",
				parser.scanner.sourceFileName);
	}

	/**
	 * Used to concatenate one string onto another
	 * <p>
	 * 
	 * @param parser
	 *            - Used for error generation
	 * @param first
	 *            - The first part of the new string
	 * @param second
	 *            - The second part of the new string
	 * @return
	 */
	public static ResultValue concat(Parser parser, ResultValue first, ResultValue second) {
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
	 * @param parser
	 *            - Used for error generation
	 * @param leftValue
	 *            - Left hand side value we are subtracting from (Minuend).
	 * @param rightValue
	 *            - Right hand side value we are subtracting (Subtrahend).
	 * @return
	 * @throws ParserException
	 */
	public static ResultValue subtract(Parser parser, ResultValue leftValue, ResultValue rightValue)
			throws ParserException {
		ResultValue subResult = new ResultValue(leftValue.type);
		ResultValue tempResult;
		if (leftValue.type.equals(Type.INT)) // Integer case
		{
			tempResult = Utility.coerceToInt(parser, rightValue);
			int minuend = Integer.parseInt(leftValue.internalValue);
			int subtrahend = Integer.parseInt(tempResult.internalValue);
			int result = minuend - subtrahend;
			subResult.internalValue = String.valueOf(result);
		} else if (leftValue.type.equals(Type.FLOAT)) // Float case
		{
			tempResult = Utility.coerceToFloat(parser, rightValue);
			double minuend = Double.parseDouble(leftValue.internalValue);
			double subtrahend = Double.parseDouble(tempResult.internalValue);
			double result = minuend - subtrahend;
			subResult.internalValue = String.valueOf(result);
		} else if (leftValue.type.equals(Type.STRING)) // String case
		{
			tempResult = Utility.convertStringToNumeric(parser, leftValue);
			return Utility.subtract(parser, tempResult, rightValue);
		} else {
			throw new ParserException(parser.scanner.lineNumber,
					"Can not subtract from variable of type \'" + leftValue.type + "\'", parser.scanner.sourceFileName);
		}
		return subResult;
	}

	/**
	 * Add one numeric value to another.
	 * <p>
	 * 
	 * @param parser
	 *            - Used for error generation
	 * @param leftValue
	 *            - Left hand side value we are adding (Addend).
	 * @param rightValue
	 *            - Right hand side value we are adding (Addend).
	 * @return
	 * @throws ParserException
	 */
	public static ResultValue add(Parser parser, ResultValue leftValue, ResultValue rightValue) throws ParserException {
		ResultValue subResult = new ResultValue(leftValue.type);
		ResultValue tempResult;
		if (leftValue.type.equals(Type.INT)) // Integer case
		{
			tempResult = Utility.coerceToInt(parser, rightValue);
			int addend1 = Integer.parseInt(leftValue.internalValue);
			int addend2 = Integer.parseInt(tempResult.internalValue);
			int result = addend1 + addend2;
			subResult.internalValue = String.valueOf(result);
		} else if (leftValue.type.equals(Type.FLOAT)) // Float case
		{
			tempResult = Utility.coerceToFloat(parser, rightValue);
			double addend1 = Double.parseDouble(leftValue.internalValue);
			double addend2 = Double.parseDouble(tempResult.internalValue);
			double result = addend1 + addend2;
			subResult.internalValue = String.valueOf(result);
		} else if (leftValue.type.equals(Type.STRING)) // String case
		{
			tempResult = Utility.convertStringToNumeric(parser, leftValue);
			return Utility.add(parser, tempResult, rightValue);
		} else {
			throw new ParserException(parser.scanner.lineNumber,
					"Can not add to variable of type \'" + leftValue.type + "\'", parser.scanner.sourceFileName);
		}
		return subResult;
	}

	/**
	 * Multiply one numeric value with another.
	 * <p>
	 * 
	 * @param parser
	 *            - Used for error generation
	 * @param leftValue
	 *            - Left hand side value we are multiplying (Multiplier).
	 * @param rightValue
	 *            - Right hand side value we are multiplying (Multiplicand).
	 * @return
	 * @throws ParserException
	 */
	public static ResultValue multiply(Parser parser, ResultValue leftValue, ResultValue rightValue)
			throws ParserException {
		ResultValue subResult = new ResultValue(leftValue.type);
		ResultValue tempResult;
		if (leftValue.type.equals(Type.INT)) // Integer case
		{
			tempResult = Utility.coerceToInt(parser, rightValue);
			int multiplier = Integer.parseInt(leftValue.internalValue);
			int multiplicand = Integer.parseInt(tempResult.internalValue);
			int result = multiplier * multiplicand;
			subResult.internalValue = String.valueOf(result);
		} else if (leftValue.type.equals(Type.FLOAT)) // Float case
		{
			tempResult = Utility.coerceToFloat(parser, rightValue);
			double multiplier = Double.parseDouble(leftValue.internalValue);
			double multiplicand = Double.parseDouble(tempResult.internalValue);
			double result = multiplier * multiplicand;
			subResult.internalValue = String.valueOf(result);
		} else if (leftValue.type.equals(Type.STRING)) // String case
		{
			tempResult = Utility.convertStringToNumeric(parser, leftValue);
			return Utility.multiply(parser, tempResult, rightValue);
		} else {
			throw new ParserException(parser.scanner.lineNumber,
					"Can not multiply variable of type \'" + leftValue.type + "\'", parser.scanner.sourceFileName);
		}
		return subResult;
	}

	/**
	 * Raise a variable to the power of another.
	 * <p>
	 * 
	 * @param parser
	 *            - Used for error generation
	 * @param leftValue
	 *            - Left hand side value we are exponentiating (Base).
	 * @param rightValue
	 *            - Right hand side value we are exponentiating (Power).
	 * @return
	 * @throws ParserException
	 */
	public static ResultValue exponentiate(Parser parser, ResultValue leftValue, ResultValue rightValue)
			throws ParserException {
		ResultValue subResult = new ResultValue(leftValue.type);
		ResultValue tempResult;
		if (leftValue.type.equals(Type.INT)) // Integer case
		{
			tempResult = Utility.coerceToInt(parser, rightValue);
			int base = Integer.parseInt(leftValue.internalValue);
			int power = Integer.parseInt(tempResult.internalValue);
			int result = (int) Math.pow(base, power);
			subResult.internalValue = String.valueOf(result);
		} else if (leftValue.type.equals(Type.FLOAT)) // Float case
		{
			tempResult = Utility.coerceToFloat(parser, rightValue);
			double base = Double.parseDouble(leftValue.internalValue);
			double power = Double.parseDouble(tempResult.internalValue);
			double result = Math.pow(base, power);
			subResult.internalValue = String.valueOf(result);
		} else if (leftValue.type.equals(Type.STRING)) // String case
		{
			tempResult = Utility.convertStringToNumeric(parser, leftValue);
			return Utility.exponentiate(parser, tempResult, rightValue);
		} else {
			throw new ParserException(parser.scanner.lineNumber,
					"Can not exponentiate variable of type \'" + leftValue.type + "\'", parser.scanner.sourceFileName);
		}
		return subResult;
	}

	/**
	 * Divide one numeric value by another.
	 * <p>
	 * 
	 * @param parser
	 *            - Used for error generation
	 * @param leftValue
	 *            - Left hand side value we are dividing (Dividend)
	 * @param rightValue
	 *            - Right hand side value we are dividing by (Divisor)
	 * @return
	 * @throws ParserException
	 */
	public static ResultValue divide(Parser parser, ResultValue leftValue, ResultValue rightValue)
			throws ParserException 
	{
		ResultValue subResult = new ResultValue(leftValue.type);
		ResultValue tempResult;
		try 
		{
		if (leftValue.type.equals(Type.INT)) // Integer case
		{
			tempResult = Utility.coerceToInt(parser, rightValue);
			int dividend = Integer.parseInt(leftValue.internalValue);
			int divisor = Integer.parseInt(tempResult.internalValue);
			int result = dividend / divisor;
			subResult.internalValue = String.valueOf(result);
		} else if (leftValue.type.equals(Type.FLOAT)) // Float case
		{
			tempResult = Utility.coerceToFloat(parser, rightValue);
			double dividend = Double.parseDouble(leftValue.internalValue);
			double divisor = Double.parseDouble(tempResult.internalValue);
			double result = dividend / divisor;
			subResult.internalValue = String.valueOf(result);
		} else if (leftValue.type.equals(Type.STRING)) // String case
		{
			tempResult = Utility.convertStringToNumeric(parser, leftValue);
			return Utility.divide(parser, tempResult, rightValue);
		} else {
			throw new ParserException(parser.scanner.lineNumber,
					"Can not divide variable of type \'" + leftValue.type + "\'", parser.scanner.sourceFileName);
		}
		}
		catch (ArithmeticException e)
		{
			throw new ParserException(parser.scanner.lineNumber,
					"Dividing by zero detected", parser.scanner.sourceFileName);
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
	public static ResultValue lessThan(Parser parser, ResultValue operandOne, ResultValue operandTwo)
			throws ParserException {

		ResultValue res = new ResultValue(Type.BOOL);
		ResultValue tempResult;

		switch (operandOne.type) {
		case Type.INT:
			tempResult = Utility.coerceToInt(parser, operandTwo);
			int iOp1 = Integer.parseInt(operandOne.internalValue);
			int iOp2 = Integer.parseInt(tempResult.internalValue);
			if (iOp1 < iOp2)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.FLOAT:
			tempResult = Utility.coerceToFloat(parser, operandTwo);
			double fOp1 = Double.parseDouble(operandOne.internalValue);
			double fOp2 = Double.parseDouble(tempResult.internalValue);
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
			throw new ParserException(parser.scanner.lineNumber,
					"Can not apply comparison \'<\' to type \'" + Type.BOOL + "\'", parser.scanner.sourceFileName);
		case Type.DATE:
			throw new ParserException(parser.scanner.lineNumber,
					"DATE type not implemented \'" + operandOne.internalValue + "\'", parser.scanner.sourceFileName);
		default:
			throw new ParserException(parser.scanner.lineNumber, "Unknown type \'" + operandOne.type + "\'",
					parser.scanner.sourceFileName);

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
	public static ResultValue greaterThan(Parser parser, ResultValue operandOne, ResultValue operandTwo)
			throws ParserException {

		ResultValue res = new ResultValue(Type.BOOL);
		ResultValue tempResult;

		switch (operandOne.type) {
		case Type.INT:
			tempResult = Utility.coerceToInt(parser, operandTwo);
			int iOp1 = Integer.parseInt(operandOne.internalValue);
			int iOp2 = Integer.parseInt(tempResult.internalValue);
			if (iOp1 > iOp2)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.FLOAT:
			tempResult = Utility.coerceToFloat(parser, operandTwo);
			double fOp1 = Double.parseDouble(operandOne.internalValue);
			double fOp2 = Double.parseDouble(tempResult.internalValue);
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
			throw new ParserException(parser.scanner.lineNumber,
					"Can not apply comparison \'>\' to type \'" + Type.BOOL + "\'", parser.scanner.sourceFileName);
		case Type.DATE:
			throw new ParserException(parser.scanner.lineNumber,
					"DATE type not implemented \'" + operandOne.internalValue + "\'", parser.scanner.sourceFileName);
		default:
			throw new ParserException(parser.scanner.lineNumber, "Unknown type \'" + operandOne.type + "\'",
					parser.scanner.sourceFileName);

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
	public static ResultValue eqalTo(Parser parser, ResultValue operandOne, ResultValue operandTwo)
			throws ParserException {

		ResultValue res = new ResultValue(Type.BOOL);
		ResultValue tempResult;

		switch (operandOne.type) {
		case Type.INT:
			tempResult = Utility.coerceToInt(parser, operandTwo);
			int iOp1 = Integer.parseInt(operandOne.internalValue);
			int iOp2 = Integer.parseInt(tempResult.internalValue);
			if (iOp1 == iOp2)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.FLOAT:
			tempResult = Utility.coerceToFloat(parser, operandTwo);
			double fOp1 = Double.parseDouble(operandOne.internalValue);
			double fOp2 = Double.parseDouble(tempResult.internalValue);
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
			throw new ParserException(parser.scanner.lineNumber,
					"DATE type not implemented \'" + operandOne.internalValue + "\'", parser.scanner.sourceFileName);
		default:
			throw new ParserException(parser.scanner.lineNumber, "Unknown type \'" + operandOne.type + "\'",
					parser.scanner.sourceFileName);

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
	public static ResultValue lessThanEqalTo(Parser parser, ResultValue operandOne, ResultValue operandTwo)
			throws ParserException {

		ResultValue res = new ResultValue(Type.BOOL);
		ResultValue tempResult;

		switch (operandOne.type) {
		case Type.INT:
			tempResult = Utility.coerceToInt(parser, operandTwo);
			int iOp1 = Integer.parseInt(operandOne.internalValue);
			int iOp2 = Integer.parseInt(tempResult.internalValue);
			if (iOp1 <= iOp2)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.FLOAT:
			tempResult = Utility.coerceToFloat(parser, operandTwo);
			double fOp1 = Double.parseDouble(operandOne.internalValue);
			double fOp2 = Double.parseDouble(tempResult.internalValue);
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
			throw new ParserException(parser.scanner.lineNumber,
					"Can not apply comparison \'<=\' to type \'" + Type.BOOL + "\'", parser.scanner.sourceFileName);
		case Type.DATE:
			throw new ParserException(parser.scanner.lineNumber,
					"DATE type not implemented \'" + operandOne.internalValue + "\'", parser.scanner.sourceFileName);
		default:
			throw new ParserException(parser.scanner.lineNumber, "Unknown type \'" + operandOne.type + "\'",
					parser.scanner.sourceFileName);

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
	public static ResultValue greaterThanEqalTo(Parser parser, ResultValue operandOne, ResultValue operandTwo)
			throws ParserException {

		ResultValue res = new ResultValue(Type.BOOL);
		ResultValue tempResult;

		switch (operandOne.type) {
		case Type.INT:
			tempResult = Utility.coerceToInt(parser, operandTwo);
			int iOp1 = Integer.parseInt(operandOne.internalValue);
			int iOp2 = Integer.parseInt(tempResult.internalValue);
			if (iOp1 >= iOp2)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.FLOAT:
			tempResult = Utility.coerceToFloat(parser, operandTwo);
			double fOp1 = Double.parseDouble(operandOne.internalValue);
			double fOp2 = Double.parseDouble(tempResult.internalValue);
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
			throw new ParserException(parser.scanner.lineNumber,
					"Can not apply comparison \'>=\' to type \'" + Type.BOOL + "\'", parser.scanner.sourceFileName);
		case Type.DATE:
			throw new ParserException(parser.scanner.lineNumber,
					"DATE type not implemented \'" + operandOne.internalValue + "\'", parser.scanner.sourceFileName);
		default:
			throw new ParserException(parser.scanner.lineNumber, "Unknown type \'" + operandOne.type + "\'",
					parser.scanner.sourceFileName);

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
	public static ResultValue notEqalTo(Parser parser, ResultValue operandOne, ResultValue operandTwo)
			throws ParserException {

		ResultValue res = new ResultValue(Type.BOOL);
		ResultValue tempResult;

		switch (operandOne.type) {
		case Type.INT:
			tempResult = Utility.coerceToInt(parser, operandTwo);
			int iOp1 = Integer.parseInt(operandOne.internalValue);
			int iOp2 = Integer.parseInt(tempResult.internalValue);
			if (iOp1 != iOp2)
				res.internalValue = "T";
			else
				res.internalValue = "F";
			break;
		case Type.FLOAT:
			tempResult = Utility.coerceToFloat(parser, operandTwo);
			double fOp1 = Double.parseDouble(operandOne.internalValue);
			double fOp2 = Double.parseDouble(tempResult.internalValue);
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
			throw new ParserException(parser.scanner.lineNumber,
					"DATE type not implemented \'" + operandOne.internalValue + "\'", parser.scanner.sourceFileName);
		default:
			throw new ParserException(parser.scanner.lineNumber, "Unknown type \'" + operandOne.type + "\'",
					parser.scanner.sourceFileName);

		}
		return res;
	}

	public static ResultValue parseInt(Parser parser, Token intToken) throws ParserException {
		ResultValue res = new ResultValue(Type.INT);
		try {
			Integer.parseInt(intToken.tokenStr);
		} catch (Exception e) {
			throw new ParserException(parser.scanner.lineNumber, "Can not parse \'" + intToken.tokenStr + "\' as Int",
					parser.scanner.sourceFileName);
		}
		res.internalValue = intToken.tokenStr;
		return res;
	}

	public static ResultValue parseFloat(Parser parser, Token floatToken) throws ParserException {

		ResultValue res = new ResultValue(Type.FLOAT);
		try {
			Double.parseDouble(floatToken.tokenStr);
		} catch (Exception e) {
			throw new ParserException(parser.scanner.lineNumber,
					"Can not parse \'" + floatToken.tokenStr + "\' as Float", parser.scanner.sourceFileName);
		}

		res.internalValue = floatToken.tokenStr;
		return res;
	}

	public static ResultValue parseString(Parser parser, Token stringToken) throws ParserException {

		ResultValue res = new ResultValue(Type.STRING);
		if (stringToken.tokenStr == null) {
			throw new ParserException(parser.scanner.lineNumber, "Can not parse \'NULL\' as String",
					parser.scanner.sourceFileName);
		}

		res.internalValue = stringToken.tokenStr;
		return res;
	}

	public static ResultValue parseBool(Parser parser, Token stringToken) throws ParserException {

		ResultValue res = new ResultValue(Type.BOOL);
		res.internalValue = stringToken.tokenStr;

		res = coerceToBool(parser, res);

		return res;
	}

	public static ResultValue parseDate(Parser parser, Token dateToken) throws ParserException {

		ResultValue res = new ResultValue(Type.DATE);
		res.internalValue = dateToken.tokenStr;

		res = coerceToDate(parser, res);

		return res;
	}

	public static ResultValue evaluateBinaryOperator(Parser parser, ResultValue operandOne, ResultValue operandTwo,
			Token operator) throws ParserException {
		ResultValue res = null;

		if (operator.primClassif != Token.OPERATOR) {
			throw new ParserException(parser.scanner.lineNumber,
					"Unable to evaluate non operator \'" + operator.tokenStr + "\'", parser.scanner.sourceFileName);
		}

		switch (operator.tokenStr) {
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
			res = Utility.lessThan(parser, operandOne, operandTwo);
			break;
		case ">":
			res = Utility.greaterThan(parser, operandOne, operandTwo);
			break;
		case "#":
			res = Utility.concat(parser, operandOne, operandTwo);
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

		case "IN": // TODO NOT YET IMPLEMENTED
		case "NOTIN":
			throw new ParserException(parser.scanner.lineNumber, "NOT YET IMPLEMENTED", parser.scanner.sourceFileName);
		default:
			throw new ParserException(parser.scanner.lineNumber, "Unknown operator \'" + operator.tokenStr + "\'",
					parser.scanner.sourceFileName);
		}
		return res;
	}

	private static ResultValue and(Parser parser, ResultValue operandOne, ResultValue operandTwo) {
		// TODO Auto-generated method stub
		ResultValue res = new ResultValue(Type.BOOL);

		if (operandOne.internalValue.equals("T") && operandTwo.internalValue.equals("T"))
			res.internalValue = "T";
		else
			res.internalValue = "F";

		return res;
	}

	private static ResultValue or(Parser parser, ResultValue operandOne, ResultValue operandTwo) {

		ResultValue res = new ResultValue(Type.BOOL);

		if (operandOne.internalValue.equals("T") || operandTwo.internalValue.equals("T"))
			res.internalValue = "T";
		else
			res.internalValue = "F";

		return res;
	}

	public static ResultValue evaluateUnaryOperator(Parser parser, ResultValue operand, Token operator)
			throws ParserException {
		ResultValue res = null;

		if (operator.primClassif != Token.OPERATOR) {
			throw new ParserException(parser.scanner.lineNumber,
					"Unable to evaluate non operator \'" + operator.tokenStr + "\'", parser.scanner.sourceFileName);
		}

		switch (operator.tokenStr) {
		case "u-":

			res = new ResultValue(operand.type);
			if (!Utility.isNumeric(parser, res))
				throw new ParserException(parser.scanner.lineNumber,
						"Can not compute negative of non numeric type \'" + res.type + "\'",
						parser.scanner.sourceFileName);

			res.internalValue = "-" + operand.internalValue;
			break;
		case "not":
			res = new ResultValue(operand.type);
			if (!res.type.equals("Bool"))
				throw new ParserException(parser.scanner.lineNumber,
						"Can not negate non boolean type \'" + res.type + "\'", parser.scanner.sourceFileName);
			if (operand.internalValue.equals("T"))
				res.internalValue = "F";
			else
				res.internalValue = "T";
			break;
		default:
			throw new ParserException(parser.scanner.lineNumber, "Unknown operator \'" + operator.tokenStr + "\'",
					parser.scanner.sourceFileName);
		}
		return res;
	}

	public static void printResult(Parser parser, ResultValue res) {

		System.out.println("On line " + parser.scanner.lineNumber + " expression evaluates to: " + res.type + ", "
				+ res.internalValue);

	}

	public static void printAssign(Parser parser, ResultValue target, ResultValue result) {
		System.out.println("On line " + parser.scanner.lineNumber + " variable " + target.internalValue + " of type "
				+ target.type + " has value " + result.internalValue);
	}

	public static ResultValue coerceToType(Parser parser, String typeStr, ResultValue resultValue)
			throws ParserException {
		switch (typeStr) {
		case Type.BOOL:
			return Utility.coerceToBool(parser, resultValue);
		case Type.FLOAT:
			return Utility.coerceToFloat(parser, resultValue);
		case Type.INT:
			return Utility.coerceToInt(parser, resultValue);
		case Type.STRING:
			return Utility.coerceToString(parser, resultValue);
		case Type.DATE:
			return Utility.coerceToDate(parser, resultValue);
		default:
			return Utility.copyTuple(parser, typeStr, resultValue);
		}
	}

	private static ResultValue copyTuple(Parser parser, String typeStr, ResultValue resultValue)
			throws ParserException {

		if (!resultValue.type.equals(typeStr)) {
			throw new ParserException(parser.scanner.lineNumber,
					"Can not convert from \'" + resultValue.type + "\'" + " to \'" + typeStr + "\'",
					parser.scanner.sourceFileName);
		}

		return resultValue.Clone();
	}

	public static String insertString(String oldString, int index, String insert) {
		int end;
		if (index + insert.length() > oldString.length())
			end = oldString.length();
		else
			end = index + insert.length();

		StringBuilder newString = new StringBuilder(oldString);
		newString = newString.replace(index, end, insert);

		return newString.toString();
	}

	public static String getStringSlice(String target, int begin, int end) {
		return target.substring(begin, end);
	}

	public static boolean isPrimitiveType(Token typeToken) {
		switch (typeToken.tokenStr) {
		case Type.BOOL:
			return true;
		case Type.FLOAT:
			return true;
		case Type.INT:
			return true;
		case Type.STRING:
			return true;
		case Type.DATE:
			return true;
		default:
			return false;
		}
	}
	
	//doesn't work if dates are more that 365*4 years apart, but I doub't anyone will notice
	public static int dateAge(String date1, String date2)
	{
		int d1 = date2Int(date1);
		int d2 = date2Int(date2);
		
		return (d1-d2)/365;
	}
	
	public static String dateAdj(String date, int adj)
	{
		int newD = date2Int(date);
		newD+=adj;
		return int2Date(newD);
	}
	public static int dateDiff(String date1, String date2)
	{
		int newD = date2Int(date1);
		int oldD = date2Int(date2);
		return newD-oldD;
	}
	
	
	
	public static int date2Int(String date)
	{
		int i;
		int days=0;
		int yr = Integer.parseInt(date.substring(0,4));
		int mo = Integer.parseInt(date.substring(5,7));
		int dy = Integer.parseInt(date.substring(9));
		
		for(i=0;i<yr; i++)
		{
			days+=365;
			if(i%4==0)
				days++;
		}
		for(i=0;i<mo;i++)
		{
			days+=daysinMonth(i, yr);
		}
		days+=dy;
		
		return days;
	}
	
	public static String int2Date(int date)
	{
		int yr = 0;
		int mo = 0;
		int dy = 0;
		
		while((yr%4==0 && date>=366) || (yr%4!=0 && date>=365)) 
		{
			date-=365;
			if(yr++%4==0)
				date--;
		}
		while(date>daysinMonth(mo, yr))
		{
			date-=daysinMonth(mo++, yr);
		}
		dy = date;
		
		String year = Integer.toString(yr);
		String month = Integer.toString(mo);
		String day = Integer.toString(dy);
		while(year.length()<4)
		{
			year = "0"+year;
		}
		if(month.length()==1)
			month = "0"+month;
		if(day.length()==1)
			day = "0"+day;
		
		return year+"-"+month+"-"+day;
	}
	
	
	//tells you how many days are in each month
	//accepts as int from 1-12
	//returns the number of days in that month, -1 if invalid month given
	public static int daysinMonth(int month, int year)
	{
		switch(month)
		{
		case 1:return 31;
		case 2:
				if(year%4== 0)
					return 29;
				else
					return 28;
		case 3:return 31;
		case 4:return 30;
		case 5:return 31;
		case 6:return 30;
		case 7:return 31;
		case 8:return 31;
		case 9:return 30;
		case 10:return 31;
		case 11:return 30;
		case 12:return 31;
		default:return -1;
		}
	}
	
	public static Boolean checkForDate(ResultValue check)
	{
		String validate = check.internalValue;
		
		String pattern = "(\\d{4})-(\\d{2})-(\\d{2})";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(validate);
		
		if(m.matches())
			return true;
		else
			return false;
	}

}
