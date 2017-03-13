package havaBol;

public class Parser {

	Scanner scanner;
	StorageManager storageManager;
	
	public Parser(Scanner scanner, StorageManager storageManager)
	{
		this.scanner = scanner;
		this.storageManager = storageManager;
	}

	/**
	 * Function for handling declarations of variables.
	 * <p>
	 * Example: Int i;
	 * 	Assume currentToken is on "Int" when entering this method.
	 * 	This function should create a new entry into the symbol table and assign it the appropriate information.
	 * <p>
	 * @throws ParserException 
	 * 
	 */
	public void declareVarStmt() throws ParserException {
		
		if (scanner.nextToken.primClassif != Token.OPERAND && scanner.nextToken.subClassif != Token.IDENTIFIER) // If the next token is not an identifier
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Declaration of non identifier: " + scanner.nextToken.tokenStr
					, scanner.sourceFileName);
		}
		if (scanner.symbolTable.getSymbol(scanner.nextToken.tokenStr) != null) // If the next token string already exists in the symbol table
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Symbol " + scanner.nextToken.tokenStr + " Already declared"
					, scanner.sourceFileName);
		}
		
		// TODO This line will need modification based on by reference parameter passing later
		STIdentifier newIdentifier = new STIdentifier(scanner.nextToken.tokenStr, scanner.currentToken.tokenStr, 1, 1, 0);
		
		scanner.symbolTable.putSymbol(scanner.nextToken.tokenStr, newIdentifier); // add symbol to table
		
		ResultValue variableValue = new ResultValue(scanner.currentToken.tokenStr); // Create a new result value and set its type string
		storageManager.putVariableValue(scanner.nextToken.tokenStr, variableValue); // Insert the new empty variable into the storage table.
		
	}
	
	/**
	 * Handles assignment statements.
	 * <p>
	 * Assume that the current token is the identifier we are assigning into.
	 * <p>
	 * When this function completes we will have parsed through until the end of a following expression.
	 * <p>
	 * @return
	 * @throws Exception
	 */
	public ResultValue assignmentStmt() throws Exception
	{
		
		if (scanner.currentToken.subClassif != Token.IDENTIFIER) // Current token should be an identifier.
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Expected variable identifier for target of assignment, found: " + scanner.currentToken.tokenStr
					, scanner.sourceFileName);
		}
		Token targetToken = scanner.currentToken; // Store the left hand side token
		
		scanner.getNext();
		if (scanner.currentToken.primClassif != Token.OPERATOR) // Next token should be an operator.
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Expected assignment operator, found: " + scanner.currentToken.tokenStr
					, scanner.sourceFileName);
		}
		Token operatorToken = scanner.currentToken; // Store the operation we are performing
		
		ResultValue targetResult = storageManager.getVariableValue(targetToken.tokenStr);
		ResultValue subResult1;
		ResultValue subResult2;
		
		subResult1 = expression();
		if (operatorToken.tokenStr.equals("="))
		{													// Assignment is the simple case.
			Utility.assign(this, targetResult, subResult1);
		}
		else if (operatorToken.tokenStr.equals("#="))
		{
			if (!targetResult.type.equals("String")) // If the target is not a string we can't concat.
			{
				throw new ParserException(scanner.currentToken.iSourceLineNr
						, "Can not concatinate to non String variable: \'" + targetToken.tokenStr + "\'"
						, scanner.sourceFileName);
			}
			subResult2 = Utility.concat(this, targetResult, subResult1);
			targetResult.internalValue = subResult2.internalValue;
		}
		else if (!Utility.isNumeric(targetResult)) // If the target is not numeric we cant do any of the following assignments
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Can not perform operation \'" + operatorToken.tokenStr + "\' on non numeric variable \'" + targetToken.tokenStr + "\'"
					, scanner.sourceFileName);
		}
		else if (operatorToken.tokenStr.equals("-="))
		{
			subResult2 = Utility.subtract(this, targetResult, subResult1);
			targetResult.internalValue = subResult2.internalValue;
		}
		else if (operatorToken.tokenStr.equals("+="))
		{
			subResult2 = Utility.add(this, targetResult, subResult1);
			targetResult.internalValue = subResult2.internalValue;
		}
		else if (operatorToken.tokenStr.equals("*="))
		{
			subResult2 = Utility.multiply(this, targetResult, subResult1);
			targetResult.internalValue = subResult2.internalValue;
		}
		else if (operatorToken.tokenStr.equals("/="))
		{
			subResult2 = Utility.divide(this, targetResult, subResult1);
			targetResult.internalValue = subResult2.internalValue;
		}
		else if (operatorToken.tokenStr.equals("^="))
		{
			subResult2 = Utility.exponentiate(this, targetResult, subResult1);
			targetResult.internalValue = subResult2.internalValue;
		}
		
		
		return targetResult;
	}

	/**
	 * TODO Evaluate an expression and return the result.
	 * <p>
	 * Expressions are in Infix notation so a stack will be used to parse.
	 * <p>
	 * @return
	 */
	private ResultValue expression() {

		return null;
	}
	
	
	
	
}
