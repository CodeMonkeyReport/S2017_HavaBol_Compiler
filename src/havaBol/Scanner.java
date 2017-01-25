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
	
	private StringBuilder tokenBuilder = new StringBuilder();

	public Scanner(String path, SymbolTable symbolTable) throws IOException {
		FileReader reader = new FileReader(path);
		file = new BufferedReader(reader);
		this.symbolTable = symbolTable;
		this.currentToken = new Token();
		this.nextToken = new Token();
		
		this.currentLine = file.readLine().toCharArray();
		this.lineNumber = 1;
		this.linePosition = 0;
	}

	public String getNext() throws IOException {
		
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
		//tokenBuilder.append(Character.toString(currentLine[linePosition]));
		
		while (!delimiters.contains( Character.toString(currentLine[linePosition]) )) {
			linePosition++;
			//tokenBuilder.append(Character.toString(currentLine[linePosition]));
		}
		tokenEnd = linePosition;
		
		classify(tokenStart, tokenEnd);
		
		return this.currentToken.tokenStr;
	}

	private void classify(int tokenStart, int tokenEnd) throws IOException {
		
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

	private void classifyNumericConstant(int tokenStart, int tokenEnd) throws NumberFormatException {
		// TODO Auto-generated method stub
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

	private void classifyIdentifier(int tokenStart, int tokenEnd) {
		
		String token = new String(currentLine, tokenStart, tokenEnd - tokenStart);
		this.currentToken.primClassif = Token.OPERAND;
		this.currentToken.subClassif = Token.IDENTIFIER;
		this.currentToken.tokenStr = token;
	}

	private void classifySpecialCharacter(int tokenStart, int tokenEnd) throws IOException {
		
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

	private void readStringConstant(int tokenStart) {
		
		int tokenEnd = tokenStart+1;
		
		while (currentLine[tokenStart] != currentLine[tokenEnd] || currentLine[tokenEnd-1] == '\\')
		{
			tokenEnd++;
		}
		
		String token = new String(currentLine, tokenStart+1, tokenEnd - tokenStart - 1);
		this.linePosition = tokenEnd+1;
		this.currentToken.primClassif = Token.OPERAND;
		this.currentToken.subClassif = Token.STRING;
		this.currentToken.tokenStr = token;
	}

}



