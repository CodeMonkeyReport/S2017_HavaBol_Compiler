package havaBol;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Scanner {
	
	public static final int MAX_TOKEN_SIZE = 999;
	private static final String delimiters = " \t;:()\'\"=!<>+-*/[]#,^\n";

	public Token currentToken;
	public Token nextToken;
	private BufferedReader file;
	private SymbolTable symbolTable;
	
	private char[] currentLine;
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
	public Scanner(String path, SymbolTable symbolTable) throws IOException {
		FileReader reader = new FileReader(path);
		file = new BufferedReader(reader);
		
		this.sourceFileName = path;
		this.symbolTable = symbolTable;
		this.currentToken = new Token();
		this.nextToken = new Token();
		
		this.currentLine = file.readLine().toCharArray();
		this.lineNumber = 1;
		this.linePosition = 0;
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
					currentToken.primClassif = Token.EOF;
					currentToken.tokenStr = "";
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
		while (!delimiters.contains( Character.toString(currentLine[linePosition]) )) {
			linePosition++;
		}
		tokenEnd = linePosition;
		
		// get the classf and subClassf for the token using starting and ending position
		classify(tokenStart, tokenEnd);
		
		return this.currentToken.tokenStr;
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
		
		this.currentToken.iSourceLineNr = this.lineNumber;
		this.currentToken.iColPos = tokenStart;
		
		if (tokenStart == tokenEnd)
		{
			// operator or separator
			classifySpecialCharacter(tokenStart, tokenEnd);
		}
		
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
				this.currentToken.primClassif = Token.OPERAND;
				this.currentToken.subClassif = Token.FLOAT;
				this.currentToken.tokenStr = token;
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
				this.currentToken.primClassif = Token.OPERAND;
				this.currentToken.subClassif = Token.INTEGER;
				this.currentToken.tokenStr = token;
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
		
		String token = new String(currentLine, tokenStart, tokenEnd - tokenStart);
		this.currentToken.primClassif = Token.OPERAND;
		this.currentToken.subClassif = Token.IDENTIFIER;
		this.currentToken.tokenStr = token;
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
		case '+':
		case '-':
		case '*':
		case '/':
		case '<':
		case '>':
		case '!':
		case '=':
		case '#':
		case '^':
			// All above symbols are operators
			this.currentToken.primClassif = Token.OPERATOR;
			this.currentToken.subClassif = 0;
			this.currentToken.tokenStr = token;
			break;
		case '(':
		case ')':
		case ':':
		case ';':
		case '[':
		case ']':
		case ',':
			// All above symbols are separators
			this.currentToken.primClassif = Token.SEPARATOR;
			this.currentToken.subClassif = 0;
			this.currentToken.tokenStr = token;
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
	 * @throws StringFormatException - Custom exception in case of a format error
	 */
	private void readStringConstant(int tokenStart) throws Exception {
		
		int tokenEnd = tokenStart+1;
		// While we are still within a valid string constant
		while (currentLine[tokenStart] != currentLine[tokenEnd] || currentLine[tokenEnd-1] == '\\')
		{
			tokenEnd++;
			// If we have gone past the end of the line
			if (tokenEnd >= currentLine.length)
				error("Reached end of line while reading string constant at position %d", tokenStart);
			
		}
		
		String token = new String(currentLine, tokenStart+1, tokenEnd - tokenStart - 1);
		this.linePosition = tokenEnd+1;
		this.currentToken.primClassif = Token.OPERAND;
		this.currentToken.subClassif = Token.STRING;
		this.currentToken.tokenStr = token;
	}

	public void error(String fmt, Object... varArgs) throws Exception
	  {
	      String diagnosticTxt = String.format(fmt, varArgs);
	      throw new ParserException(this.lineNumber
	            , diagnosticTxt
	            , this.sourceFileName);
	  }

}



