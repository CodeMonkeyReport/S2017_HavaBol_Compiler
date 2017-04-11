package havaBol;

import java.util.ArrayList;
import java.util.Stack;

public class Parser {

	Scanner scanner;
	StorageManager storageManager;
	
	public static boolean bShowToken = false;
	boolean bShowExpr = false;
	public boolean bShowAssign = false;

	public Parser(Scanner scanner, StorageManager storageManager)
	{
		this.scanner = scanner;
		this.storageManager = storageManager;
	}

	/**
	 * 	This method executes a list of statements until an expected terminating string
	 * <p>
	 * This method is called as the main program loop with the parameters true and "" indicating EOF
	 * <p>
	 * 
	 * @param bExecuting - Indicates if the method should execute the list of statements
	 *
	 * @throws Exception
	 */
	public ResultValue statements(boolean bExecuting) throws ParserException
	{
		ResultValue res = null;
		// Get next token
		while (! scanner.getNext().isEmpty() ) // While we have not reached the EOF
		{
			Token temp = scanner.currentToken;
			switch (temp.primClassif)
			{
			case Token.CONTROL:
				switch (temp.subClassif)
				{
				case Token.DECLARE:
					declareStmt(bExecuting);
					if (scanner.nextToken.primClassif == Token.OPERATOR)
						assignmentStmt(bExecuting);
//					if (! scanner.currentToken.tokenStr.equals(";")) // Expect to see a ';' after assign/declare
//						throw new ParserException(scanner.currentToken.iSourceLineNr
//								, "Expected \';\' after declaration statement"
//								, scanner.sourceFileName);
					break;
				case Token.FLOW:
					if (temp.tokenStr.equals("if"))
					{
						res = ifStmt(bExecuting);
						if (res.terminatingStr.equals("else"))
						{
							if (res.getInternalValue().equals("T")) // If we executed the if part
								elseStmt(false);	// Don't execute else
							else
								elseStmt(bExecuting); // Or do
						}
						else if (! res.terminatingStr.equals("endif"))
						{
							throw new ParserException(scanner.currentToken.iSourceLineNr
									, "Expected \'endif\' after \'if\' statement"
									, scanner.sourceFileName);
						}
					}
					if (temp.tokenStr.equals("while"))
					{
						res = whileStmt(bExecuting);
					}
					if (temp.tokenStr.equals("for"))
						res = forStmt(bExecuting);
					break;
				case Token.END:
					if (temp.tokenStr.equals("else"))
					{
						res = new ResultValue(Type.BOOL); // Build a result object and return it
						if (bExecuting)
							res.internalValue = "T";
						else
							res.internalValue = "F";
						res.terminatingStr = "else";
						scanner.getNext(); // move past the else statement
						return res;
					}
					if (! scanner.nextToken.tokenStr.equals(";"))
					{
						throw new ParserException(scanner.currentToken.iSourceLineNr
								, "Missing simicolon after: \'" + temp.tokenStr + "\'"
								, scanner.sourceFileName);
					}
					else if (temp.tokenStr.equals("endif"))
					{						
						res = new ResultValue(Type.BOOL); // Build a result object and return it
						if (bExecuting)
							res.internalValue = "T";
						else
							res.internalValue = "F";
						res.terminatingStr = "endif";
						return res;
					}
					else if (temp.tokenStr.equals("endwhile"))
					{
						res = new ResultValue(Type.BOOL); // Build a result object and return it
						if (bExecuting)
							res.internalValue = "T";
						else
							res.internalValue = "F";
						res.terminatingStr = "endwhile";
						return res;
					}
                    else if (temp.tokenStr.equals("endfor"))
                    {
                        res = new ResultValue(Type.BOOL); // Build a result object and return it
                        if (bExecuting)
                            res.internalValue = "T";
                        else
                            res.internalValue = "F";
                        res.terminatingStr = "endfor";
                        return res;
                    }
					break;
				case Token.DEBUG:
					// Handle debug statements here
					res = debugStmt(bExecuting);
					break;
				default:
					break;
				}
				break;
			case Token.OPERAND:
				if (temp.subClassif != Token.IDENTIFIER)
					throw new ParserException(scanner.currentToken.iSourceLineNr
							, "Unexpected litteral value \'" + temp.tokenStr + "\'"
							, scanner.sourceFileName);
				assignmentStmt(bExecuting);
				break;
			case Token.FUNCTION:
				functionStmt(bExecuting);
				// Function call
				break;
			case Token.DEFINE:
				 // TODO maybe some error checking needed?
				defineStmt(bExecuting);
				break;
			case Token.SEPARATOR:
				 // TODO maybe some error checking needed?
				break;
			default:
				throw new ParserException(scanner.currentToken.iSourceLineNr
						, "Unknown token found: \'" + temp.tokenStr + "\'"
						, scanner.sourceFileName);
			}
		}
		return null;
	}

	/**
	 * Handle debug statements
	 * <p>
	 * for the example input: debug Expr on;
	 * On entering this method we expect to be on 'debug'
	 * On exiting this method we expect to be on ';'
	 * The result of this example should be to set the local value this.bShowExpr to true
	 * <p>
	 * 
	 * @param bExecuting
	 * @return
	 */
	private ResultValue debugStmt(boolean bExecuting) throws ParserException 
	{
		scanner.getNext();
		
		switch (scanner.currentToken.tokenStr)
		{
			case "Expr":
				this.bShowExpr = true;
				break;
			case "Assign":
				this.bShowAssign = true;
				break;
			case "Token" :
				this.bShowToken = true; // Not implemented? TODO
				break;
			default:
				throw new ParserException(scanner.currentToken.iSourceLineNr
						, "Non Implemented Debug type: \'" + scanner.currentToken.tokenStr + "\'"
						, "scanner.sourceFileName");
		}
		
		scanner.getNext();
			
		return null;
	}


	/**
	 * Not yet implemented
	 * @param bExecuting
	 * @return
	 * @throws ParserException
	 */
	private ResultValue functionStmt(boolean bExecuting) throws ParserException
	{
		ResultValue res = null;
		Token functionToken;
		
		functionToken = scanner.currentToken;
		
		if (functionToken.subClassif == Token.BUILTIN)
		{
			switch (functionToken.tokenStr)
			{
			case "print":
				functionPrint(bExecuting);
				res = new ResultValue(Type.STRING);
				res.internalValue = "";
				break;
			case "LENGTH":
				res = functionLength(bExecuting);
				break;
			case "SPACES":
				res = functionSpaces(bExecuting);
				break;
			case "ELEM":
				res = functionElem(bExecuting);
				break;
			case "MAXLENGTH":
				res = functionMaxLength(bExecuting);
			case "MAXELEM":
				res = functionMaxElem(bExecuting);
				break;
			default:
				throw new ParserException(scanner.currentToken.iSourceLineNr
						, "Unknown builtin function: \'" + functionToken.tokenStr + "\'"
						, scanner.sourceFileName);	
			}
		}
		else
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Builtin functions not yet implemented, can not execute function \'" + functionToken.tokenStr + "\'"
					, scanner.sourceFileName);
		}		
		return res;
	}
	
	/**
	 * Handles the MAXELEM builtin function
	 * <p>
	 * On entering the method the currentToken should be on 'MAXELEM'
	 * On leaving the method the currentToken should be on ';'
	 * <p>
	 * 
	 * @param bExecuting
	 * @return
	 * @throws ParserException
	 */
	private ResultValue functionMaxElem(boolean bExecuting) throws ParserException
	{
		ResultValue res = new ResultValue(Type.INT);
		
		if(bExecuting == false)
		{
			scanner.skipTo(";");
		}
		
		scanner.getNext();
		if (! scanner.currentToken.tokenStr.equals("(")) // Missing lparen
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Error missing \'(\' after LENGTH function"
					, scanner.sourceFileName);
		}
		scanner.getNext();
		
		ResultList arg = (ResultList)expression(")");
		
		if(arg.structure != Type.ARRAY)
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Arguement to ElEM Not An Array"
					, scanner.sourceFileName);
		}
		
		res.internalValue = Integer.toString(arg.iMaxSize);
		
		return res;
	}
	
	/**
	 * Handles the ELEM builtin function
	 * <p>
	 * On entering the method the currentToken should be on 'Elem'
	 * On leaving the method the currentToken should be on ';'
	 * <p>
	 * 
	 * @param bExecuting
	 * @return
	 * @throws ParserException
	 */
	private ResultValue functionElem(boolean bExecuting) throws ParserException
	{
		ResultValue res = new ResultValue(Type.INT);
		
		if(bExecuting == false)
		{
			scanner.skipTo(";");
		}
		
		scanner.getNext();
		if (! scanner.currentToken.tokenStr.equals("(")) // Missing lparen
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Error missing \'(\' after LENGTH function"
					, scanner.sourceFileName);
		}
		scanner.getNext();
		
		ResultList arg = (ResultList)expression(")");
		
		if(arg.structure != Type.ARRAY)
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Arguement to ElEM Not An Array"
					, scanner.sourceFileName);
		}
		
		res.internalValue = Integer.toString(arg.iCurrentSize);
		
		return res;
	}

	public ResultValue defineStmt(boolean bExecuting) throws ParserException
	{
		Token statementToken;
		if (bExecuting == false)
		{
			scanner.skipTo(":");
			return null;
		}
		statementToken = scanner.currentToken;
		switch (statementToken.tokenStr)
		{
		case "deftuple": // defining a tuple
			//defineTuple()
			break;
		}
		
		
		return null; // TODO avoid stupid IDE errors
	}
	
	/**
	 * Handles the string length builtin function
	 * <p>
	 * On entering the method the currentToken should be on 'LENGTH'
	 * On leaving the method the currentToken should be on ';'
	 * <p>
	 * 
	 * @param bExecuting
	 * @return
	 * @throws ParserException
	 */
	private ResultValue functionLength(boolean bExecuting) throws ParserException 
	{
		ResultValue res = null;
		
		if (bExecuting == false)
		{
			scanner.skipTo(";");
			return null;
		}
		
		scanner.getNext();
		if (! scanner.currentToken.tokenStr.equals("(")) // Missing lparen
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Error missing \'(\' after LENGTH function"
					, scanner.sourceFileName);
		}
		scanner.getNext();
		
		ResultValue stringParameter = expression(")");
		
		res = new ResultValue(Type.INT);
		res.internalValue = Integer.toString(stringParameter.internalValue.length());
		return res;
	}
	
	
	/*
	 * Handles the string length builtin function
	 * <p>
	 * on entering the method the currentToken should be on 'SPACES'
	 * on leaving the method the current token should be on ';'
	 * <p>
	 *
	 * @param bExecuting
	 * @return Boolean:
	 * 			T - string is empty or only contains spaces
	 * 			F - string is non-empty
	 * @throws ParserException
	 */
	private ResultValue functionSpaces(boolean bExecuting) throws ParserException
	{
		ResultValue arg;
		ResultValue res = new ResultValue(Type.BOOL);
		res.internalValue = "T";
		
		
		if(bExecuting == false)
		{
			scanner.skipTo(";");
		}
		
		scanner.getNext();
		
		if(!scanner.currentToken.tokenStr.equals("("))
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Expected \'(\' after SPACES"
					, scanner.sourceFileName);
		
		scanner.getNext();
		
		arg = expression(")");
		
		
		//set res to False if string isn't empty
		if(!arg.internalValue.isEmpty()){
			Character c;
			for(int i=0; i<arg.internalValue.length(); i++)
			{
				c = arg.internalValue.charAt(i);
				if(!c.equals(' '))
				{
					res.internalValue = "F";
				}
			}
		}
		
		return res;
	}
	
	/*
	 * Handles the string maxLength builtin function
	 * <p>
	 * on entering the method the currentToken should be on 'SPACES'
	 * on leaving the method the current token should be on ';'
	 * <p>
	 *
	 * @param bExecuting
	 * @return INT - The declared length of the string
	 * @throws ParserException
	 */
	private ResultValue functionMaxLength(boolean bExecuting) throws ParserException
	{
		ResultValue arg;
		ResultValue res = new ResultValue(Type.INT);
		
		
		if(bExecuting == false)
		{
			scanner.skipTo(";");
		}
		
		scanner.getNext();
		
		if(!scanner.currentToken.tokenStr.equals("("))
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Expected \'(\' after SPACES"
					, scanner.sourceFileName);
		
		scanner.getNext();
		
		arg = expression(")");
		
		if(arg.type != Type.STRING)
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Arguement not of type String"
					, scanner.sourceFileName);
		}
		
		
		scanner.getNext();
		if(!scanner.currentToken.tokenStr.equals(";"))
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Expected \')\' after SPACES argument"
					, scanner.sourceFileName);
		
		//current token should now be ;
		scanner.getNext();
		
		res.internalValue = Integer.toString(arg.internalValue.length());
		
		return res;
	}

	/**
	 * Handles the print builtin function
	 * <p>
	 * On entering the method the currentToken should be on 'print'
	 * On exiting the method the currentToken should be on ';'
	 * <p>
	 * The output of this function will include the printing of a newline
	 * @param bExecuting
	 * @throws ParserException
	 */
	private void functionPrint(boolean bExecuting) throws ParserException 
	{
		
		if(bExecuting == false)
		{
			scanner.skipTo(";");
			return;
		}
		
		scanner.getNext(); // skip over the 'print'
		
		if(!scanner.currentToken.tokenStr.equals("("))
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Expected \'(\' after PRINT"
					, scanner.sourceFileName);
		
		scanner.getNext(); // Move past the '('
		
		ArrayList<ResultValue> printResults;
		printResults = argList(")");
		
		for (int i = 0; i < printResults.size(); i++)
		{
			System.out.print(printResults.get(i).getInternalValue());
		}
		System.out.println();
		
		scanner.getNext(); // Move past the ')'
		
		if(!scanner.currentToken.tokenStr.equals(";"))
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Expected \';\' after \'print\'"
					, scanner.sourceFileName);
		
		return;
	}


	/**
	 * Handles execution of the else branch of an if-than-else statement
	 * <p>
	 * On entering this method the current token should be on 'else'
	 * On exiting this method the current token should be on 'endif'
	 * <p>
	 * @param bExecuting
	 * @return
	 * @throws ParserException
	 */
	public ResultValue elseStmt(boolean bExecuting) throws ParserException
	{
		ResultValue res = new ResultValue(Type.BOOL);
		res.terminatingStr = "endif";
		
		if (bExecuting == false) // If we are not executing then we don't care
		{
			scanner.skipTo(":");
			res = statements(false);

			return res;
		}

		res = statements(bExecuting);
		
		if (scanner.currentToken.tokenStr.equals("endif") == false)
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Expected \'endif\' after if statement"
					, scanner.sourceFileName);

		return res;
	}
	
	/**
	 * Not yet implemented
	 * @param bExecuting
	 * @return
	 */
    public ResultValue forStmt(boolean bExecuting) throws ParserException
    {
        ResultValue res = null,controlVal,limitVal,delimVal,incrVal;
        Token forToken, intToken;
        int max, incr;
      
        if (bExecuting == false)
        {
            scanner.skipTo(":");
            res = statements(false);
            return res;
        }
        scanner.getNext();

        // Adding support for declare statement in for statements
        if (scanner.currentToken.subClassif == Token.DECLARE)
        {
        	declareStmt(true);
        }
        
        /*
            First, check what the next keyword is after starting/control value is. Since control value can only by one
            token, check the next token's string;
         */
        switch (scanner.nextToken.tokenStr) {
            /*
                Counting for loop,
                First initialize control value,
                then evaluate the limit value(can be an expression),
                then evaluate the increment value(can be expression),
                finally use a for loop to iterate the correct number of times and process statements within the loop as
                we go.
             */
            case "=":

                controlVal = evaluateOperand(scanner.currentToken); //Initialize the control value
            	if (controlVal == null) // Value has not been declared so now we need to declare it
            	{
            		intToken = new Token();
            		intToken.tokenStr = Type.INT;
            		controlVal = declareScalar(intToken, scanner.currentToken); // Implicitly declare the currentToken as an int
                    STIdentifier newIdentifier = new STIdentifier(scanner.currentToken.tokenStr
                                , Type.INT
                                , Type.PRIMITIVE
                                , Type.VALUE
                                , Type.LOCAL);

                    scanner.symbolTable.putSymbol(scanner.currentToken.tokenStr, newIdentifier); // add symbol to table
            		
            		storageManager.putVariableValue(scanner.currentToken.tokenStr, controlVal);
            	}
           
                //Since for loop assignment does not use semicolon, a slightly modified version must be implemented here
                scanner.getNext(); //skip '='
                scanner.getNext();
                Utility.assign(this, controlVal, expression("to")); //Set control value, accounting for expressions

                //Evaluate and set limit value
                scanner.getNext();
                limitVal = expression("by");

                if (limitVal.terminatingStr.equals("by"))
                {
                	//Evaluate and set increment value
                	scanner.getNext();
                	incrVal = expression(":");
                }
                else
                {
                	// Set default incrValue to 1
                	incrVal = new ResultValue(Type.INT);
                	incrVal.internalValue = "1";
                }

                //Class voted changing incr or limit will NOT affect # of iterations, so set them before evaluating statements
                //within the for loop.
                forToken = scanner.currentToken;    //set loopback
                max = Integer.parseInt(limitVal.getInternalValue());
                incr = Integer.parseInt(incrVal.getInternalValue());

                for (int i = Integer.parseInt(controlVal.getInternalValue()); i < max; i += incr) {
                    scanner.jumpToPosition(forToken.iSourceLineNr, forToken.iColPos);
                    controlVal.internalValue = String.valueOf(i);   //Control value is updated on every iteration
                    res = statements(true);             //Evaluate statements in for loop
                }
                break;
            /*
                Iterating through strings using a delimiter for loop, similar to counting for loop:
                First initialize control value,
                then evaluate the string to iterate through(can be an expression),
                then evaluate the delimiter value(can be expression),
                finally use a for loop.
             */
            case "from":
                /*
                    As per specifications, the control value is implicitly declared if it has not been declared previously.
                    However declareScalar only takes ';' as terminating character so this is a slight modification of it.
                    First check if the control value exists, if it doesn't, create and put in storageManager; in either
                    case, initialize it.
                 */
                if (scanner.symbolTable.getSymbol(scanner.currentToken.tokenStr) == null) {
                    STIdentifier newIdentifier = new STIdentifier(scanner.currentToken.tokenStr
                            , Type.STRING
                            , Type.PRIMITIVE
                            , Type.VALUE
                            , Type.LOCAL);

                    scanner.symbolTable.putSymbol(scanner.currentToken.tokenStr, newIdentifier); // add symbol to table

                    controlVal = new ResultValue(Type.STRING);
                    storageManager.putVariableValue(scanner.currentToken.tokenStr, controlVal);
                }
                controlVal = evaluateOperand(scanner.currentToken);

                //Evaluate and set iterable string
                scanner.getNext();  //skip 'to'
                scanner.getNext();
                limitVal = expression("by");

                //Evaluate and set delimiter string
                scanner.getNext();
                delimVal = expression(":");

                //Since class voted changing delimiter or string does NOT impact # of iterations, first split the string,
                //then iterate over each piece
                forToken = scanner.currentToken;
                String[] tokens = limitVal.getInternalValue().split(delimVal.getInternalValue());
                for (String token : tokens) {
                    scanner.jumpToPosition(forToken.iSourceLineNr, forToken.iColPos);
                    controlVal.internalValue = token;
                    res = statements(true);
                }
                break;
            /*
                Iterate over string or elements in array for loops. Again, similar to the other loops except, we need to
                check if the thing we're iterating over is an array or string to set the control value and for loop correctly.
             */
            case "in":
                Token control = scanner.currentToken;   //Hold the control value token so it can be set later

                //Evaluate and set the thing we're iterating over
                scanner.getNext();//skip 'in'
                scanner.getNext();
                limitVal = expression(":");
                forToken = scanner.currentToken;

                //Now set the control value, since we know the limitVal's type. If control value doesn't exist, create it; otherwise initialize control value
                if (scanner.symbolTable.getSymbol(control.tokenStr) == null) {
                    STIdentifier newIdentifier = new STIdentifier(control.tokenStr
                            , limitVal.type
                            , Type.PRIMITIVE
                            , Type.VALUE
                            , Type.LOCAL);

                    scanner.symbolTable.putSymbol(control.tokenStr, newIdentifier); // add symbol to table

                    controlVal = new ResultValue(limitVal.type);
                    storageManager.putVariableValue(control.tokenStr, controlVal);
                }
                controlVal = evaluateOperand(control);
                //Check if the thing we're iterating over is an array first
                if (limitVal instanceof ResultList) {
                    //As per the specs, changing # of array elements does not affect # of iterations, so set it before looping
                    max = ((ResultList) limitVal).iMaxSize;
                    for (int i = 0; i < max; i++) 
                    {
                        scanner.jumpToPosition(forToken.iSourceLineNr, forToken.iColPos);
                        /*
                            As a personal choice, I decided to skip over holes in the array. This can be changed so it creates an error
                            in the future...
                         */
                        if (((ResultList) limitVal).get(this, i) != null) {
                            Utility.assign(this, controlVal, ((ResultList) limitVal).get(this, i));
                            res = statements(true);
                        } else res = statements(false);
                    }
                }
                //Class voted changing the string does not affect # of iterations, so set it before looping
                else if(limitVal.type.equals(Type.STRING)) {
                    max = limitVal.getInternalValue().length();
                    for (int i = 0; i < max; i++) {
                        scanner.jumpToPosition(forToken.iSourceLineNr, forToken.iColPos);
                        controlVal.internalValue = String.valueOf(limitVal.getInternalValue().charAt(i));
                        res = statements(true);
                    }
                }
                else throw new ParserException(scanner.currentToken.iSourceLineNr,
                            "Unsupported object, cannot iterate over object \'" + scanner.currentToken.tokenStr + "\'",
                            scanner.sourceFileName);
                break;
            //Since the control value cannot be an expression itself, throw an error if any unknown tokens appear within the for loop expression.
            default:
                throw new ParserException(scanner.currentToken.iSourceLineNr,
                        "Expected \'in\', \'to\', or \'=\' after starting variable, found \'" + scanner.nextToken.tokenStr + "\'",
                        scanner.sourceFileName);
        }



        return res;
    }



    /**
	 * handles the execution of while statements
	 * <p>
	 * On entering the method the currentToken should be on 'while'
	 * On exiting the method the currentToken should be on 'endwhile'
	 * <p>
	 * 
	 * @param bExecuting
	 * @return
	 * @throws ParserException
	 */
	public ResultValue whileStmt(boolean bExecuting) throws ParserException
	{
		ResultValue res;
		Token whileToken = scanner.currentToken;
		
		if (bExecuting == false)
		{
			scanner.skipTo(":");
			res = statements(false);
			return res;
		}
		
		scanner.getNext();
		res = expression(":");
		
		while (res.getInternalValue().equals("T"))
		{
			statements(true);
			scanner.jumpToPosition(whileToken.iSourceLineNr, whileToken.iColPos);
			scanner.getNext();
			res = expression(":");
		}
		res = statements(false);
		
		if (! scanner.currentToken.tokenStr.equals("endwhile"))
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Expected \'endwhile\' after \'while\' statement"
					, scanner.sourceFileName);
		}
		return res;
	}
	
	/**
	 * Handles execution of If-Then statements
	 * <p>
	 * On entering the method the currentToken should be on 'if'
	 * On exiting the method the currenToken should be on ':'
	 * <p>
	 * @param bExecuting
	 * @return - The 
	 * @throws ParserException
	 */
	private ResultValue ifStmt(boolean bExecuting) throws ParserException
	{
		ResultValue res = new ResultValue(Type.BOOL);
		
		if (bExecuting == false) // If we are not executing then we don't care
		{
			scanner.skipTo(":");
			res = statements(false);
			return res;
		}
		
		scanner.getNext();
		res = expression(":");
		
		if (res.getInternalValue().equals("T"))
			res = statements(true);
		else 
			res = statements(false);
		
		return res;
	}

	/**
	 * This code handles evaluation of comma separated expressions
	 * <p>
	 * On entering the method the currentToken should be on the first term in the expression
	 * On exiting the method the currentToken should be on the expected terminator
	 * <p>
	 * @param expectedTerminator - The token we are expecting to see at the end of the argument list
	 * @return
	 * @throws ParserException 
	 */
	public ArrayList<ResultValue> argList(String expectedTerminator) throws ParserException
	{
		ArrayList<ResultValue> resultArray = new ArrayList<ResultValue>();
		
		resultArray.add(expression(expectedTerminator));
		while (!scanner.currentToken.tokenStr.equals(expectedTerminator) && !scanner.getNext().equals("") )
		{
			resultArray.add(expression(expectedTerminator));
		}
		return resultArray;
	}
	
	/**
	 * Function for handling declarations of variables.
	 * <p>
	 * Example: Int i;
	 * 	Assume currentToken is on "Int" when entering this method.
	 * 	This method should create a new entry into the symbol table and assign it the appropriate information.
	 * 
	 * <p>
	 * On exiting the method the current token should be on ';' OR '=' if an initialization statement follows
	 * @throws ParserException 
	 * 
	 */
	public void declareStmt(boolean bExecuting) throws ParserException 
	{
		STIdentifier variableIdentifier;
		ResultValue variableValue = null;
		Token variableToken;
		Token typeToken;
		if (bExecuting == false)
		{
			scanner.skipTo(";");
			return;
		}
		
		typeToken = scanner.currentToken;
		variableToken = scanner.nextToken;
		
		variableIdentifier = declareIdentifier(bExecuting);
		
		scanner.symbolTable.putSymbol(variableToken.tokenStr, variableIdentifier);
		
		if (variableIdentifier.structureType == Type.ARRAY)
			variableValue = declareArray(typeToken, variableToken);
		else if (variableIdentifier.structureType == Type.PRIMITIVE)
			variableValue = declareScalar(typeToken, variableToken);
		else if (variableIdentifier.structureType == Type.TUPLE)
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Tuple types not yet implemented"
					, scanner.sourceFileName);
			
		// TODO This line will need modification based on by reference parameter passing later
		storageManager.putVariableValue(variableToken.tokenStr, variableValue); // Insert the new empty variable into the storage table.
	}

	/**
	 * Handles the creation of the relevant STIdentifier when building a variable from a declaration.
	 * <p>
	 * @param bExecuting
	 * @return
	 * @throws ParserException
	 */
	public STIdentifier declareIdentifier(boolean bExecuting) throws ParserException
	{
		Token typeToken;
		Token variableToken;
		STIdentifier newIdentifier;
		if (bExecuting == false)
		{
			scanner.skipTo(";");
			return null;
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
		typeToken = scanner.currentToken;
		scanner.getNext(); // Move past the type declaration, should now be on variable
		variableToken = scanner.currentToken;
		
		if (scanner.nextToken.tokenStr.equals("["))
		{
			// Array stuff
			newIdentifier = new STIdentifier(variableToken.tokenStr
					, typeToken.tokenStr
					, Type.ARRAY
					, Type.VALUE
					, Type.LOCAL);
		}
		else
		{
			newIdentifier = new STIdentifier(variableToken.tokenStr
					, typeToken.tokenStr
					, Type.PRIMITIVE
					, Type.VALUE
					, Type.LOCAL);
			
		}
		
		return newIdentifier;
	}
	
	/**
	 * Handles the declaration of scalar variables
	 * <p>
	 * On entering the method the current token should be on either a '=' or a ';'
	 * On exiting the method the currentToken should be on ';'
	 * <p>
	 * 
	 * @param typeToken 
	 * @param variableToken
	 * @return
	 * @throws ParserException 
	 */
	private ResultValue declareScalar(Token typeToken, Token variableToken) throws ParserException 
	{
		ResultValue result = new ResultValue(typeToken.tokenStr);
		
		return result;
	}

	/**
	 * Handles the declaration of arrays
	 * <p>
	 * On entering the method the currentToken should be on the variable we want to declare
	 * On exiting the method the currentToken should be on ';' OR an assignment operator
	 * <p>
	 * @param typeToken 
	 * @param variableToken 
	 * @return
	 * @throws ParserException 
	 */
	private ResultList declareArray(Token typeToken, Token variableToken) throws ParserException
	{
		ResultList arrayValue;
		ArrayList<ResultValue> initArgs;
		scanner.getNext(); // Move past the identifier name
		
		if (scanner.nextToken.tokenStr.equals("unbounded")) // CASE 1 Unbounded arrays may be initialized or maybe not
		{
			arrayValue = new ResultList(typeToken.tokenStr, Type.ARRAY_UNBOUNDED);
			
			// unbounded arrays need to skip the next few tokens and read the intArgs
			if (! scanner.getNext().equals("]"))
				throw new ParserException(scanner.currentToken.iSourceLineNr
						, "Unexpected token in declaration \'" + scanner.nextToken.tokenStr + "\'"
						, scanner.sourceFileName);
			
			scanner.getNext();
			scanner.getNext();
			
			if (scanner.currentToken.tokenStr.equals("=")) // If the next statement is an assignment
			{
				scanner.getNext();
				initArgs = argList(";");
				if (initArgs.size() > 1) // Insert all the items into the array
				{
					for (int i = 0; i < initArgs.size(); i++)
					{
						arrayValue.insert(this, i, Utility.CoerceToType(this, typeToken.tokenStr, initArgs.get(i)));
					}
				}
				else // Insert default value of the item
				{
					arrayValue.defaultValue = Utility.CoerceToType(this, typeToken.tokenStr, initArgs.get(0));
					arrayValue.insert(this, 0, arrayValue.defaultValue);
				}
			}
			else if (! scanner.currentToken.tokenStr.equals(";"))
				throw new ParserException(scanner.currentToken.iSourceLineNr
						, "Unexpected token in declaration \'" + scanner.currentToken.tokenStr + "\'"
						, scanner.sourceFileName);
		}
		else if (scanner.nextToken.tokenStr.equals("]")) // CASE 2 Array without subscript requires initial values
		{
			// In this case there needs to be an init here
			scanner.getNext();
			scanner.getNext(); // Move the currentToken onto the beginning of the first expression
			if (scanner.currentToken.tokenStr.equals(";"))
				throw new ParserException(scanner.currentToken.iSourceLineNr
						, "Expected argument list for initilization of " + typeToken.tokenStr + " array \'" + variableToken.tokenStr + "\'"
						, scanner.sourceFileName);
			
			scanner.getNext();
			
			initArgs = argList(";");
			arrayValue = new ResultList(typeToken.tokenStr, initArgs.size());
			
			for (int i = 0; i < initArgs.size(); i++)
			{
				arrayValue.insert(this, i, Utility.CoerceToType(this, typeToken.tokenStr, initArgs.get(i)) );
			}
		}
		else // CASE 3 There is an expression in between brackets
		{
			scanner.getNext();
			ResultValue arraySizeExpressionResult = Utility.coerceToInt(this, expression("]"));
			int arraySize = Integer.parseInt(arraySizeExpressionResult.getInternalValue());
			arrayValue = new ResultList(typeToken.tokenStr, arraySize);
			scanner.getNext();
			if (scanner.currentToken.tokenStr.equals("=")) // If the next statement is an assignment
			{
				scanner.getNext();
				initArgs = argList(";");
				if (initArgs.size() > 1) // Insert all the items into the array
				{
					for (int i = 0; i < initArgs.size(); i++)
					{
						arrayValue.insert(this, i, Utility.CoerceToType(this, typeToken.tokenStr, initArgs.get(i)));
					}
				}
				else // Insert copies of the item until the array is full
				{
					for (int i = 0; i < arraySize; i++)
					{
						arrayValue.insert(this, i, Utility.CoerceToType(this, typeToken.tokenStr, initArgs.get(0)));
					}
				}
			}
			else
			{
				for (int i = 0; i < arraySize; i++)
				{
					arrayValue.insert(this, i, new ResultValue(typeToken.tokenStr));
					arrayValue.iCurrentSize = 0;
				}
			}
		}
		
		
		return arrayValue;
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
		
		ResultValue targetResult = evaluateOperand(targetToken);
		scanner.getNext();
		
		if (scanner.currentToken.primClassif != Token.OPERATOR) // Next token should be an operator.
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Expected assignment operator, found: " + scanner.currentToken.tokenStr
					, scanner.sourceFileName);
		}
		Token operatorToken = scanner.currentToken; // Store the operation we are performing
		
		
		if (targetResult == null)
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Undeclared identifier: \'" + targetToken.tokenStr + "\'"
					, scanner.sourceFileName);
		}
		
		ResultValue subResult1;
		ResultValue subResult2;
		
		scanner.getNext();
		String expectedTerminator = ";";
		subResult1 = expression(expectedTerminator);
		
		if (! subResult1.terminatingStr.equals(";"))
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Missing \';\' after assignment statement"
					, scanner.sourceFileName);
		}
		
		if (operatorToken.tokenStr.equals("="))
		{													// Assignment is the simple case
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
		else if (!Utility.isNumeric(this, targetResult)) // If the target is not numeric we can't do any of the following assignments
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
	 * Evaluate an expression and return the result.
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
		boolean bOperatorFound = false;
		
		while(!scanner.currentToken.tokenStr.equals(expectedTerminator) 
				&& !scanner.currentToken.tokenStr.equals(",")
				&& !scanner.currentToken.tokenStr.equals(":")
				&& !scanner.currentToken.tokenStr.equals(";"))  // Until we reach a semicolon, colon, comma or terminator
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
			case Token.FUNCTION:
				tempRes01 = functionStmt(true);
				outputStack.push(tempRes01);
				expected = Token.OPERATOR;
				break;
			case Token.OPERATOR:
				bOperatorFound = true;
				if (expected == Token.OPERAND && scanner.currentToken.tokenStr.equals("-")) // If we are looking for an operand then this is u-
				{
					temp = scanner.currentToken;
					temp.tokenStr = "u-";
					sToken = new StackToken(temp);
				}
				else if (expected == Token.OPERAND && scanner.currentToken.tokenStr.equals("not"))
				{
					// Handle 'not' unary operator
					temp = scanner.currentToken;
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
						while (!operatorStack.isEmpty() && sToken.iPrecedence <= operatorStack.peek().iStackPrecedence)
						{
							popped = operatorStack.pop();
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
				case "(":
					
					if (expected != Token.OPERAND)
						throw new ParserException(scanner.currentToken.iSourceLineNr
								, "Unexpected operand \'" + scanner.currentToken.tokenStr + "\' in expression"
								, scanner.sourceFileName);
					
					scanner.getNext();
					tempRes01 = expression(")"); // evaluate the expression up to the rparen
					outputStack.push(tempRes01);
					expected = Token.OPERATOR;
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
		
		if (! outputStack.isEmpty())
		{
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Expression parse missmatch"
					, scanner.sourceFileName);
		}
		res.terminatingStr = scanner.currentToken.tokenStr;
		
		if (bShowExpr && bOperatorFound)
			Utility.printResult(this, res);
		
		return res;
	}

	private ResultValue evaluateOperand(Token operandToken) throws ParserException
	{
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
				return null;
			}
			if (res instanceof ResultList) // If the element is an array
			{
				if (scanner.nextToken.tokenStr.equals("[")) // Check if we need to grab an index
				{
					scanner.getNext();
					scanner.getNext(); // Move past the [ and onto the expression
					int index = Integer.parseInt(Utility.coerceToInt(this, expression("]")).getInternalValue());
					res = ((ResultList) res).get(this, index);
				}
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
			res = null; // Case for all tuple types
			break;
		default:
			throw new ParserException(scanner.currentToken.iSourceLineNr
					, "Unknown identifier type \'" + operandToken.tokenStr + "\' found"
					, scanner.sourceFileName);
		}
		return res;
	}
}
