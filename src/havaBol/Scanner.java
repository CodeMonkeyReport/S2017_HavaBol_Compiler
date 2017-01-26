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
	
	private StringBuilder tokenBuilder;

	/**
	 * Constructor for the havaBol.Scanner object for use in reading HavaBol source code.
	 * Local line number information will be initilized to 1
	 * 
	 * @param path - Path to the HavaBol source code we are scanning in
	 * @param symbolTable - SymbolTable object used to store symbols and keywords
	 * @throws IOException - If the file is not found or is not in a readable format throw an IOException
	 */
	public Scanner(String path, SymbolTable symbolTable) throws IOException {
		FileReader reader = new FileReader(path);
		file = new BufferedReader(reader);
		tokenBuilder = new StringBuilder();
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
	 * 
	 * @return a String representation of the token
	 * @throws Exception 
	 */
	public String getNext() throws Exception {
		
		int tokenStart;
		int tokenEnd;
		int type;
		boolean isDelimiter;
		//this.currentToken = this.nextToken;
		
		// Skip white space
		while (currentLine.length == this.linePosition || currentLine[linePosition] == '\t' || currentLine[linePosition] == ' ')
		{
			if (currentLine.length == this.linePosition)
			{
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
		
		while (!delimiters.contains( Character.toString(currentLine[linePosition]) )) {
			linePosition++;
		}
		tokenEnd = linePosition;
		
		classify(tokenStart, tokenEnd);
		
		return this.currentToken.tokenStr;
	}

	/***
	 * Sets the primClassif and subClassf features of the currentToken depending
	 * on the string found within tokenStart and tokenEnd on the currentLine
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
	 * 
	 * @param tokenStart - Beginning index of the token
	 * @param tokenEnd - Ending index of the token
	 * @throws NumberFormatException
	 */
	private void classifyNumericConstant(int tokenStart, int tokenEnd) throws NumberFormatException {
		String token = new String(currentLine, tokenStart, tokenEnd - tokenStart);
		
		if (token.contains("."))
		{
			Double.parseDouble(token);
			this.currentToken.primClassif = Token.OPERAND;
			this.currentToken.subClassif = Token.FLOAT;
			this.currentToken.tokenStr = token;
		}
		else
		{
			Integer.parseInt(token);
			this.currentToken.primClassif = Token.OPERAND;
			this.currentToken.subClassif = Token.INTEGER;
			this.currentToken.tokenStr = token;
		}
	}

	/**
	 * Helper method to the classify method used to set values for a numeric constant
	 * 
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
			this.currentToken.primClassif = Token.SEPARATOR;
			this.currentToken.subClassif = 0;
			this.currentToken.tokenStr = token;
			break;
		case '\'':
		case '\"':
			readStringConstant(tokenStart);
			break;
		default:
				throw new IOException();
		}
	}

	/**
	 * Helper method used to complete the reading of a string constant. 
	 * 
	 * @param tokenStart - Beginning of the string
	 * @throws StringFormatException - Custom exception in case of a format error
	 */
	private void readStringConstant(int tokenStart) throws StringFormatException {
		
		int tokenEnd = tokenStart+1;
		
		while (currentLine[tokenStart] != currentLine[tokenEnd] || currentLine[tokenEnd-1] == '\\')
		{
			tokenEnd++;
			if (tokenEnd >= currentLine.length)
				throw new StringFormatException("Invalid string format found");
			
		}
		
		String token = new String(currentLine, tokenStart+1, tokenEnd - tokenStart - 1);
		this.linePosition = tokenEnd+1;
		this.currentToken.primClassif = Token.OPERAND;
		this.currentToken.subClassif = Token.STRING;
		this.currentToken.tokenStr = token;
	}

}



