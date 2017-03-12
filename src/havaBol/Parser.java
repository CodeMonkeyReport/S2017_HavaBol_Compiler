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
		// TODO Auto-generated method stub
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
	
	public ResultValue assignmentStmt() throws Exception
	{
		ResultValue res = new ResultValue();
		
		if (scanner.currentToken.subClassif != Token.IDENTIFIER)
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Expected variable identifier for target of assignment, found: " + scanner.currentToken.tokenStr
					, scanner.sourceFileName);
		}
		Token lhsToken = scanner.currentToken; // Store the left hand side token
		
		scanner.getNext();
		if (scanner.currentToken.primClassif != Token.OPERATOR)
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Expected assignment operator, found: " + scanner.currentToken.tokenStr
					, scanner.sourceFileName);
		}
		Token operatorToken = scanner.currentToken;
		
		ResultValue subResult1;
		ResultValue subResult2;
		
		subResult1 = expression();
		if (operatorToken.tokenStr.equals("="))
		{
			
		}
		else if (operatorToken.tokenStr.equals("-="))
		{
			
		}
		else if (operatorToken.tokenStr.equals("+="))
		{
			
		}
		else if (operatorToken.tokenStr.equals("*="))
		{
			
		}
		else if (operatorToken.tokenStr.equals("^="))
		{
			
		}
		else if (operatorToken.tokenStr.equals("/="))
		{
			
		}
		else if (operatorToken.tokenStr.equals("#="))
		{
			
		}
		
		
		return res;
	}

	private ResultValue expression() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
