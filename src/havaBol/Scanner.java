package havaBol;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Scanner {

	private static final String delimiters = " \t;:()\'\"=!<>+-*/[]#,^\n";

	public Token currentToken;
	public Token nextToken;
	private BufferedReader file;
	private SymbolTable symbolTable;

	public char[] currentLine;
	private int linePosition;
	private int lineNumber;
	private String sourceFileName;


    /**
	 * Constructor for the havaBol.Scanner object for use in reading HavaBol source code.
	 * Local line number information will be initilized to 1
	 * <p>
	 *
	 * @param path - Path to the HavaBol source code we are scanning in
	 * @param symbolTable - SymbolTable object used to store symbols and keywords
	 * @throws IOException - If the file is not found or is not in a readable format throw an IOException
	 */
	public Scanner(String path, SymbolTable symbolTable) throws Exception 
	{
		FileReader reader = new FileReader(path);
		file = new BufferedReader(reader);

		this.sourceFileName = path;
		this.symbolTable = symbolTable;
		this.currentToken = new Token();
		this.nextToken = new Token();

        String line = file.readLine();
		this.currentLine = line.toCharArray();
		this.lineNumber = 1;
		this.linePosition = 0; // reset position
		getNext(); // Read the first token into current
		
		// What is this code?
//        if(line != null)
//            System.out.printf("    %d %s\n", lineNumber+1, line);
//		try {
//			do{
//				getNext();
//			}
//			while(nextToken.tokenStr.isEmpty() && currentLine != null);
//		}catch (Exception exception){
//			exception.printStackTrace();
//		}
	}

	/**
	 * Populates the public currentToken property with the appropriate token information and
	 * returns the Token's tokenString value
	 * <p>
	 *
	 * @return a String representation of the token
	 * @throws Exception
	 */
	public String getNext() throws Exception {

		//System.out.println(currentToken.tokenStr);
		currentToken = nextToken;
		nextToken = new Token();
		int tokenStart;
		int tokenEnd;

		// Skip white space
		while (currentLine.length == this.linePosition || currentLine[linePosition] == '\t' || currentLine[linePosition] == ' ')
		{
			// If we have reached the end of the line
			if (currentLine.length == this.linePosition)
			{
				// Read the next line
				String line = file.readLine();
				if (line == null)
				{
					// EOF
					nextToken.primClassif = Token.EOF;
					nextToken.tokenStr = "";
					return currentToken.tokenStr;
				}
                this.currentLine = line.toCharArray();
				this.lineNumber++;
				this.linePosition = 0;
			}
			else
			{
				this.linePosition++;
			}
		}
		tokenStart = linePosition;
		// Find the next delimiter
		while (!delimiters.contains(Character.toString(currentLine[linePosition]))) {
			linePosition++;
			if(this.linePosition == currentLine.length)
			    break;
		}
		tokenEnd = linePosition;

		// get the classf and subClassf for the token using starting and ending position
		classify(tokenStart, tokenEnd);

		return this.nextToken.tokenStr;
	}

	/***
	 * Sets the primClassif and subClassf features of the currentToken depending
	 * on the string found within tokenStart and tokenEnd on the currentLine
	 * <p>
	 *
	 * @param tokenStart - Beginning index of the token
	 * @param tokenEnd - Ending index of the token
	 * @throws Exception
	 */
	private void classify(int tokenStart, int tokenEnd) throws Exception {

		this.nextToken.iSourceLineNr = this.lineNumber;
		this.nextToken.iColPos = tokenStart;



		if (Character.isAlphabetic(currentLine[tokenStart]))
		{
			// identifier
			classifyIdentifier(tokenStart, tokenEnd);
		}

		if (Character.isDigit(currentLine[tokenStart]))
		{
			// numeric constant
			classifyNumericConstant(tokenStart, tokenEnd);
		}

		if (tokenStart == tokenEnd)
		{
			// operator or separator
			classifySpecialCharacter(tokenStart, tokenEnd);
		}

	}

	/**
	 * Helper method to the classify method used to set values for a numeric constant
	 * <p>
	 *
	 * @param tokenStart - Beginning index of the token
	 * @param tokenEnd - Ending index of the token
	 * @throws NumberFormatException
	 */
	private void classifyNumericConstant(int tokenStart, int tokenEnd) throws Exception {
		String token = new String(currentLine, tokenStart, tokenEnd - tokenStart);

		// For floating point values
		if (token.contains("."))
		{
			try
			{
				Double.parseDouble(token);
				this.nextToken.primClassif = Token.OPERAND;
				this.nextToken.subClassif = Token.FLOAT;
				this.nextToken.tokenStr = token;
			}
			catch (Exception e)
			{
				error("Could not parse floating point token %s at position %d", token, tokenStart);
			}
		}
		// For integer values
		else
		{
			try
			{

				Integer.parseInt(token);
				this.nextToken.primClassif = Token.OPERAND;
				this.nextToken.subClassif = Token.INTEGER;
				this.nextToken.tokenStr = token;
			}
			catch (Exception e)
			{
				error("Could not parse integer token %s at position %d", token, tokenStart);
			}
		}
	}

	/**
	 * Helper method to the classify method used to set values for a numeric constant
	 * <p>
	 * TODO This function will need to be updated to use the SymbolTable for classifications
	 * @param tokenStart - Beginning index of the token
	 * @param tokenEnd - Ending index of the token
	 */
	private void classifyIdentifier(int tokenStart, int tokenEnd) {

		// IF stEntry = st.getSymbol() HAS VALUE
			// set classifications based on resulting values from stEntry
		// ELSE st.putSymbol(TOKEN) add the token as an identifier to ST
		
		String token = new String(currentLine, tokenStart, tokenEnd - tokenStart);
		this.nextToken.primClassif = Token.OPERAND;
		this.nextToken.subClassif = Token.IDENTIFIER;
		this.nextToken.tokenStr = token;
	}

	/**
	 * Helper method to the classify method used to set values for a special characters
	 * <p>
	 *
	 * @param tokenStart - Beginning index of the token
	 * @param tokenEnd - Ending index of the token
	 * @throws Exception
	 */
	private void classifySpecialCharacter(int tokenStart, int tokenEnd) throws Exception {

		String token = new String(currentLine, tokenStart, tokenEnd - tokenStart+1);
		this.linePosition++;
		switch (currentLine[tokenStart])
		{
			case '/':
				if((tokenStart + 1 != currentLine.length) && (currentLine[tokenStart+1] == '/'))
				{
					String line = file.readLine();
					if (line == null)
					{
						// EOF
						nextToken.primClassif = Token.EOF;
						nextToken.tokenStr = "";
						if(tokenStart+2 == currentLine.length)
						{
							this.linePosition++;
							nextToken = currentToken;
							getNext();
						}
						break;
					}
					this.currentLine = line.toCharArray();
					this.lineNumber++;
					this.linePosition = 0;
					nextToken = currentToken;
					getNext();
					break;
				}
			case '+':
            case '-':
			case '<':
			case '>':
			case '!':
			case '=':
			case '*':
			case '#':
                if((tokenStart + 1 != currentLine.length) && (currentLine[tokenStart+1] == '='))
                {
                    nextToken.tokenStr = String.valueOf(currentLine, tokenStart, 2);
                    this.nextToken.primClassif = Token.OPERATOR;
                    this.nextToken.subClassif = 0;
                    linePosition++;
                    break;
                }
			case '^':
				// All above symbols are operators
				this.nextToken.primClassif = Token.OPERATOR;
				this.nextToken.subClassif = 0;
				this.nextToken.tokenStr = token;
				break;
			case '(':
			case ')':
			case ':':
			case ';':
			case '[':
			case ']':
			case ',':
				// All above symbols are separators
				this.nextToken.primClassif = Token.SEPARATOR;
				this.nextToken.subClassif = 0;
				this.nextToken.tokenStr = token;
				break;
			case '\'':
			case '\"':
				// Above symbols are used for string constants
				readStringConstant(tokenStart);
				break;
			default:
				error("Could not read symbol %c at position %d", currentLine[tokenStart], tokenStart);
		}
	}

	/**
	 * Helper method used to complete the reading of a string constant.
	 * <p>
	 *
	 * @param tokenStart - Beginning of the string
	 * @throws Exception - Custom exception in case of a format error
	 */
	private void readStringConstant(int tokenStart) throws Exception {

		int tokenEnd = tokenStart+1;
		// While we are still within a valid string constant
		while (currentLine[tokenStart] != currentLine[tokenEnd] || (currentLine[tokenEnd-1] == '\\' && currentLine[tokenEnd-2] != '\\'))
		{
			tokenEnd++;
			// If we have gone past the end of the line
			if (tokenEnd >= currentLine.length)
				error("Reached end of line while reading string constant at position %d", tokenStart);

		}

		String token = new String(currentLine, tokenStart+1, tokenEnd - tokenStart - 1);
		char[] retCharM = new char[token.length()];
		int charPosition = 0;
        for (int i = 0; i < token.length(); i++) {
            char c = token.charAt(i);
            if(c == '\\'){
                switch (token.charAt(i+1))
                {
                    case '"':
                        retCharM[charPosition] = '"';
                        break;
                    case '\'':
                        retCharM[charPosition] = '\'';
                        break;
                    case '\\':
                        retCharM[charPosition] = '\\';
                        break;
                    case 'n':
                        retCharM[charPosition] = 0x0A;
                        break;
                    case 't':
                        retCharM[charPosition] = 0x09;
                        break;
                    case 'a':
                        retCharM[charPosition] = 0x07;
                        break;
                    default:
                        error("Unknown escape value:\"%c%c\"", c,token.charAt(i+1));
                }
                charPosition++;
                if(i < token.length())
                {
                    i++;
                }
            }
            else
            {
                retCharM[charPosition] = c;
                charPosition++;
            }
        }
        this.linePosition = tokenEnd+1;
		this.nextToken.primClassif = Token.OPERAND;
		this.nextToken.subClassif = Token.STRING;
		this.nextToken.tokenStr = String.valueOf(retCharM,0,charPosition);
	}

	public void error(String fmt, Object... varArgs) throws Exception
	{
		String diagnosticTxt = String.format(fmt, varArgs);
		throw new ParserException(this.lineNumber
				, diagnosticTxt
				, this.sourceFileName);
	}





}


