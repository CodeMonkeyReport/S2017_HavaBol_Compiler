package havaBol;

import java.util.Stack;

public class Parser {

	Scanner scanner;
	StorageManager storageManager;

	public Parser(Scanner scanner, StorageManager storageManager)
	{
		this.scanner = scanner;
		this.storageManager = storageManager;
	}


	/**
	 * 	This method executes a list of statements until an expected terminating string.
	 * <p>
	 * This method is called as the main program loop with the parameters true and "" indicating EOF
	 * <p>
	 * 
	 * @param bExecuting - Indicates if the method should execute the list of statements
	 * @param expectedTerminator
	 * @throws Exception 
	 */
	public ResultValue statements(boolean bExecuting) throws ParserException
	{
		boolean state;
		ResultValue res = null;
		// Get next token
		while (!scanner.currentToken.tokenStr.equals("")) // While we have not reached the EOF
		{
			scanner.getNext();
			switch (scanner.currentToken.primClassif)
			{
			case Token.CONTROL:
				switch (scanner.currentToken.subClassif)
				{
				case Token.DECLARE:
					declareStmt(bExecuting);
					break;
				case Token.FLOW:
					if (scanner.currentToken.tokenStr.equals("if"))
						res = ifStmt(bExecuting);
					if (scanner.currentToken.tokenStr.equals("while"))
						res = whileStmt(bExecuting);
					if (scanner.currentToken.tokenStr.equals("for"))
						res = forStmt(bExecuting);
					break;
				case Token.END:
					//return bExecuting;
				default:
					break;
				}
			case Token.IDENTIFIER:
				assignmentStmt(bExecuting);
				break;
				// Function call
			}
		}
		
		return res;
	}
	
	
	
	private ResultValue forStmt(boolean bExecuting) {
		// TODO Auto-generated method stub
		return null;
	}


	private ResultValue whileStmt(boolean bExecuting) {
		// TODO Auto-generated method stub
		return null;
	}


	private ResultValue ifStmt(boolean bExecuting) throws ParserException {
		ResultValue res;
		
		if (bExecuting == false) // If we are not executing then we don't care
		{
			scanner.skipTo(":");
			res = statements(false);
			return res;
		}
		
		scanner.getNext();
		res = expression(":");
		
		if (res.internalValue.equals("T"))
			statements(true);
		else 
			statements(false);
		
		if (scanner.currentToken.tokenStr.equals("endif") == false)
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Expected \'endif\' after if statement"
					, scanner.sourceFileName);
		
		return res;
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
	public void declareStmt(boolean bExecuting) throws ParserException {
		
		if (bExecuting == false)
		{
			scanner.skipTo(";");
			return;
		}
		
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
	 * Current token will be on ';'
	 * <p>
	 * If bExecuting is false then simply skip to ';' and return null
	 * <p>
	 * @return
	 * @throws Exception
	 */
	public ResultValue assignmentStmt(boolean bExecuting) throws ParserException
	{
		
		if (bExecuting == false)
		{
			scanner.skipTo(";");
			return null;
		}
		
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
		
		scanner.getNext();
		String expectedTerminator = ";";
		subResult1 = expression(expectedTerminator);
		if (operatorToken.tokenStr.equals("="))
		{													// Assignment is the simple case.
			Utility.assign(this, targetResult, subResult1);
		}
		else if (operatorToken.tokenStr.equals("#="))
		{
			if (!targetResult.type.equals("String")) // If the target is not a string we can't concatenate.
			{
				throw new ParserException(scanner.currentToken.iSourceLineNr
						, "Target of concatination assignment must be String type, found: \'" + targetResult.type + "\'"
						, scanner.sourceFileName);
			}
			subResult2 = Utility.concat(this, targetResult, subResult1);
			Utility.assign(this, targetResult, subResult2);
		}
		else if (!Utility.isNumeric(targetResult)) // If the target is not numeric we can't do any of the following assignments
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Can not perform operation \'" + operatorToken.tokenStr + "\' on non numeric variable \'" + targetToken.tokenStr + "\'"
					, scanner.sourceFileName);
		}
		else if (operatorToken.tokenStr.equals("-="))
		{
			subResult2 = Utility.subtract(this, targetResult, subResult1);

			Utility.assign(this, targetResult, subResult2);
		}
		else if (operatorToken.tokenStr.equals("+="))
		{
			subResult2 = Utility.add(this, targetResult, subResult1);

			Utility.assign(this, targetResult, subResult2);
		}
		else if (operatorToken.tokenStr.equals("*="))
		{
			subResult2 = Utility.multiply(this, targetResult, subResult1);

			Utility.assign(this, targetResult, subResult2);
		}
		else if (operatorToken.tokenStr.equals("/="))
		{
			subResult2 = Utility.divide(this, targetResult, subResult1);

			Utility.assign(this, targetResult, subResult2);
		}
		else if (operatorToken.tokenStr.equals("^="))
		{
			subResult2 = Utility.exponentiate(this, targetResult, subResult1);

			Utility.assign(this, targetResult, subResult2);
		}

		return targetResult;
	}

	/**
	 * TODO Evaluate an expression and return the result.
	 * <p>
	 * Expressions are in Infix notation so a stack will be used to parse.
	 * A stack of ResultValue will be used for the output
	 * <p>
	 * Assume currentToken is on the first operand of the expression on entering the method.
	 * When this method completes, the current token will be on the separator indicating the end of the expression.
	 * <p>
	 * @return
	 * @throws Exception 
	 */
	public ResultValue expression(String expectedTerminator) throws ParserException 
	{
		Stack<StackToken> operatorStack = new Stack<StackToken>();
		Stack<ResultValue> outputStack = new Stack<ResultValue>();
		ResultValue res, tempRes01, tempRes02, tempRes03;
		
		Token temp;
		StackToken popped, sToken;
		
		int expected = Token.OPERAND; // The type of term we are expecting next
		boolean bFound = false; // Used to see if we found a lparen when evaluating a rparen.
		
		while(!scanner.currentToken.tokenStr.equals(expectedTerminator) ) // Until we reach a semicolon, colon or end of file?
		{
			switch (scanner.currentToken.primClassif)
			{
			case Token.OPERAND:
				if (expected != Token.OPERAND)
					throw new ParserException(scanner.currentToken.iSourceLineNr
							, "Unexpected operand \'" + scanner.currentToken.tokenStr + "\' in expression"
							, scanner.sourceFileName);
							
				tempRes01 = this.evaluateOperand(scanner.currentToken);
				outputStack.push(tempRes01);
				expected = Token.OPERATOR;
				break;
			case Token.OPERATOR:
				if (expected == Token.OPERAND && scanner.currentToken.tokenStr.equals("-")) // If we are looking for an operand then this is u-
				{
					temp = scanner.currentToken;
					temp.tokenStr = "u-";
					sToken = new StackToken(temp);
				}
				else if (expected != Token.OPERATOR) // Error Unexpected operator
					throw new ParserException(scanner.currentToken.iSourceLineNr
							, "Unexpected operator \'" + scanner.currentToken.tokenStr + "\' in expression"
							, scanner.sourceFileName);
				
				sToken = new StackToken(scanner.currentToken);
				if (operatorStack.isEmpty())
				{
					operatorStack.push(sToken);
				}
				else
				{
					if (sToken.iPrecedence > operatorStack.peek().iStackPrecedence) // If precedence is greater push onto stack
					{
						operatorStack.push(sToken);
					}
					else // Otherwise loop until it is not the case
					{
						while (!operatorStack.isEmpty() && sToken.iPrecedence <= (popped = operatorStack.pop()).iStackPrecedence)
						{
							if (popped.iOperandCnt == 1) // Unary operator is a unique case
							{
								tempRes01 = outputStack.pop(); // get a single operand
								tempRes02 = Utility.evaluateUnaryOperator(this, tempRes01, popped.token);
								outputStack.push(tempRes02); // Push result back onto stack
							}
							else
							{
								tempRes02 = outputStack.pop(); // Get two operands for our operator
								tempRes01 = outputStack.pop();
								
								tempRes03 = Utility.evaluateBinaryOperator(this, tempRes01, tempRes02, popped.token);
								
								outputStack.push(tempRes03); // Push result back onto stack
							}
						}
						operatorStack.push(sToken); // Then push the new operator on
					}
				}
				expected = Token.OPERAND;
				break;
			case Token.SEPARATOR:
				switch (scanner.currentToken.tokenStr)
				{
				case ")":
					bFound = false;
					while (!operatorStack.isEmpty())
					{
						popped = operatorStack.pop();
						if (popped.token.tokenStr.equals("(")) // If we found the lparn break
						{
							bFound = true;
							break; 
						}

						if (popped.iOperandCnt == 1) // Unary operator is a unique case
						{
							tempRes01 = outputStack.pop(); // get a single operand
							tempRes02 = Utility.evaluateUnaryOperator(this, tempRes01, popped.token);
							outputStack.push(tempRes02); // Push result back onto stack
						}
						else
						{
							tempRes02 = outputStack.pop(); // Get two operands for our operator
							tempRes01 = outputStack.pop();
							
							tempRes03 = Utility.evaluateBinaryOperator(this, tempRes01, tempRes02, popped.token);
							
							outputStack.push(tempRes03); // Push result back onto stack
						}
					} // if bFound OR if we found a function call TODO
					break;
				case "(":
					sToken = new StackToken(scanner.currentToken);
					operatorStack.push(sToken);
					break;
				default:
					throw new ParserException(scanner.currentToken.iSourceLineNr
							, "Unexpected separator \'" + scanner.currentToken.tokenStr + "\' in expression"
							, scanner.sourceFileName);
				}
				break;
			default:
				break;
			}
			scanner.getNext();
		}
		while (!operatorStack.isEmpty())
		{
			popped = operatorStack.pop();
			if (popped.token.tokenStr.equals("(")) // If we found the lparn break
			{
				throw new ParserException(scanner.currentToken.iSourceLineNr
						, "Unexpected separator \'" + popped.token.tokenStr + "\' in expression"
						, scanner.sourceFileName);
			}

			if (popped.iOperandCnt == 1) // Unary operator is a unique case
			{
				tempRes01 = outputStack.pop(); // get a single operand
				tempRes02 = Utility.evaluateUnaryOperator(this, tempRes01, popped.token);
				outputStack.push(tempRes02); // Push result back onto stack
			}
			else
			{
				tempRes02 = outputStack.pop(); // Get two operands for our operator
				tempRes01 = outputStack.pop();
				
				tempRes03 = Utility.evaluateBinaryOperator(this, tempRes01, tempRes02, popped.token);
				
				outputStack.push(tempRes03); // Push result back onto stack
			}
		}
		res = outputStack.pop();
		if (!outputStack.isEmpty())
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Expression parse missmatch"
					, scanner.sourceFileName);
		}
		if (!scanner.currentToken.tokenStr.equals(expectedTerminator))
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Expected \'" + expectedTerminator + "\' at end of expression"
					, scanner.sourceFileName);
		}
		return res;
	}

	private ResultValue evaluateOperand(Token operandToken) throws ParserException {
		ResultValue res = null;
		
		if (operandToken.primClassif != Token.OPERAND)
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Token \'" + operandToken.tokenStr + "\' is not an operand"
					, scanner.sourceFileName);
		}
		switch (operandToken.subClassif)
		{
		case Token.IDENTIFIER:
			res = this.storageManager.getVariableValue(operandToken.tokenStr);
			if (res == null)
			{
				throw new ParserException(scanner.currentToken.iSourceLineNr
						, "Unknown identifier \'" + operandToken.tokenStr + "\' found in expression"
						, scanner.sourceFileName);
			}
			break;
		case Token.INTEGER:
			res = Utility.parseInt(this, operandToken);
			break;
		case Token.FLOAT:
			res = Utility.parseFloat(this, operandToken);
			break;
		case Token.BOOLEAN:
			res = Utility.parseBool(this, operandToken);
			break;
		case Token.STRING:
			res = Utility.parseString(this, operandToken);
			break;
		case Token.DATE:
			res = Utility.parseDate(this, operandToken);
			break;
		case Token.VOID:
			res = null;
			break;
		default:
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Unknown identifier type \'" + operandToken.tokenStr + "\' found"
					, scanner.sourceFileName);
		}
		return res;
	}
	
	
}
