package test;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import havaBol.Parser;
import havaBol.ParserException;
import havaBol.ResultValue;
import havaBol.Scanner;
import havaBol.StorageManager;
import havaBol.SymbolTable;

public class ExceptionTest
{

	@Rule
	public ExpectedException  expectedEx = ExpectedException.none();
	
	@Test
	public void returnStmtOutsideFunctionThrowsError() throws Exception
	{
		FileReader testReader;
			// Set up the inital 'file' to be read
			testReader = new FileReader("p5ReturnError.txt");
			BufferedReader br = new BufferedReader(testReader);
			SymbolTable st = new SymbolTable();
			StorageManager storageManager = new StorageManager();
			
		    expectedEx.expect(ParserException.class); // Here we need to 'expect' to throw an error
		    expectedEx.expectMessage("return statment outside of function definition");
			
				Scanner testScanner = new Scanner("p5FunctionFour.txt", br, st);
				Parser parser = new Parser(testScanner, storageManager);
				
				// Use this area to run tests
				ResultValue res = parser.statements(true);

				if (res.terminatingStr.equals("return"))
					throw new ParserException(testScanner.currentToken.iSourceLineNr,
							"return statment outside of function definition", testScanner.sourceFileName);
				//***
	}
	
	@Test
	public void stringToIntErrorTest() throws Exception
	{
		FileReader testReader;
			// Set up the inital 'file' to be read
			testReader = new FileReader("p5StringToIntCoersionError.txt");
			BufferedReader br = new BufferedReader(testReader);
			SymbolTable st = new SymbolTable();
			StorageManager storageManager = new StorageManager();
			
		    expectedEx.expect(ParserException.class); // Here we need to 'expect' to throw an error
		    expectedEx.expectMessage("Can not parse \'hello\' as Int");
			
				Scanner testScanner = new Scanner("p5StringToIntCoersionError.txt", br, st);
				Parser parser = new Parser(testScanner, storageManager);
				
				// Use this area to run tests
				ResultValue res = parser.statements(true);
					
				if (res.terminatingStr.equals("return"))
					throw new ParserException(testScanner.currentToken.iSourceLineNr,
							"return statment outside of function definition", testScanner.sourceFileName);
				//***
	}
	
	@Test
	public void stringToIntMissingSimicolon() throws Exception
	{
		FileReader testReader;
			// Set up the inital 'file' to be read
			testReader = new FileReader("p5StringToIntMissingSimicolon.txt");
			BufferedReader br = new BufferedReader(testReader);
			SymbolTable st = new SymbolTable();
			StorageManager storageManager = new StorageManager();
			
		    expectedEx.expect(ParserException.class); // Here we need to 'expect' to throw an error
		    expectedEx.expectMessage("Missing \';\' after expression");
			
				Scanner testScanner = new Scanner("p5StringToIntCoersionError.txt", br, st);
				Parser parser = new Parser(testScanner, storageManager);
				
				// Use this area to run tests
				ResultValue res = parser.statements(true);
					
				if (res.terminatingStr.equals("return"))
					throw new ParserException(testScanner.currentToken.iSourceLineNr,
							"return statment outside of function definition", testScanner.sourceFileName);
				//***
	}
	
	@Test
	public void ifStmtMissingColon() throws Exception
	{
		FileReader testReader;
			// Set up the inital 'file' to be read
			testReader = new FileReader("p5IfStmtWithSimicolon.txt");
			BufferedReader br = new BufferedReader(testReader);
			SymbolTable st = new SymbolTable();
			StorageManager storageManager = new StorageManager();
			
		    expectedEx.expect(ParserException.class); // Here we need to 'expect' to throw an error
		    expectedEx.expectMessage("Missing \':\' after if");
			
				Scanner testScanner = new Scanner("p5IfStmtWithSimicolon.txt", br, st);
				Parser parser = new Parser(testScanner, storageManager);
				
				// Use this area to run tests
				ResultValue res = parser.statements(true);
					
				if (res.terminatingStr.equals("return"))
					throw new ParserException(testScanner.currentToken.iSourceLineNr,
							"return statment outside of function definition", testScanner.sourceFileName);
				//***
	}
	
	@Test
	public void ifStmtWithNonBoolTest() throws Exception
	{
		FileReader testReader;
			// Set up the inital 'file' to be read
			testReader = new FileReader("p5IfStmtWithNonBoolResult.txt");
			BufferedReader br = new BufferedReader(testReader);
			SymbolTable st = new SymbolTable();
			StorageManager storageManager = new StorageManager();
			
		    expectedEx.expect(ParserException.class); // Here we need to 'expect' to throw an error
		    expectedEx.expectMessage("Expression result \'Thats not a bool\' can not be parsed as Bool");
			
				Scanner testScanner = new Scanner("p5IfStmtWithNonBoolResult.txt", br, st);
				Parser parser = new Parser(testScanner, storageManager);
				
				// Use this area to run tests
				ResultValue res = parser.statements(true);
					
				if (res.terminatingStr.equals("return"))
					throw new ParserException(testScanner.currentToken.iSourceLineNr,
							"return statment outside of function definition", testScanner.sourceFileName);
				//***
	}
	
	@Test
	public void whileStmtWithNonBoolTest() throws Exception
	{
		FileReader testReader;
			// Set up the inital 'file' to be read
			testReader = new FileReader("p5WhileStmtWithNonBoolResult.txt");
			BufferedReader br = new BufferedReader(testReader);
			SymbolTable st = new SymbolTable();
			StorageManager storageManager = new StorageManager();
			
		    expectedEx.expect(ParserException.class); // Here we need to 'expect' to throw an error
		    expectedEx.expectMessage("Expression result \'0\' can not be parsed as Bool");
			
				Scanner testScanner = new Scanner("p5WhileStmtWithNonBooResult.txt", br, st);
				Parser parser = new Parser(testScanner, storageManager);
				
				// Use this area to run tests
				ResultValue res = parser.statements(true);
					
				if (res.terminatingStr.equals("return"))
					throw new ParserException(testScanner.currentToken.iSourceLineNr,
							"return statment outside of function definition", testScanner.sourceFileName);
				//***
	}
	
	@Test
	public void expressionOverflowOntoNextLine() throws Exception
	{
		FileReader testReader;
			// Set up the inital 'file' to be read
			testReader = new FileReader("p5ExpressionOverflowOntoNextLine.txt");
			BufferedReader br = new BufferedReader(testReader);
			SymbolTable st = new SymbolTable();
			StorageManager storageManager = new StorageManager();
			
		    expectedEx.expect(ParserException.class); // Here we need to 'expect' to throw an error
		    expectedEx.expectMessage("Unexpected operand \'i\' in expression, possible missing \';\' on line 8");
			
				Scanner testScanner = new Scanner("p5ExpressionOverflowOntoNextLine.txt", br, st);
				Parser parser = new Parser(testScanner, storageManager);
				
				// Use this area to run tests
				ResultValue res = parser.statements(true);
					
				if (res.terminatingStr.equals("return"))
					throw new ParserException(testScanner.currentToken.iSourceLineNr,
							"return statment outside of function definition", testScanner.sourceFileName);
				//***
	}
	
	@Test
	public void  uninitilizedVariableTest() throws Exception
	{
		FileReader testReader;
			// Set up the inital 'file' to be read
			testReader = new FileReader("p5UninitilizedVariable.txt");
			BufferedReader br = new BufferedReader(testReader);
			SymbolTable st = new SymbolTable();
			StorageManager storageManager = new StorageManager();
			
		    expectedEx.expect(ParserException.class); // Here we need to 'expect' to throw an error
		    expectedEx.expectMessage("Can not parse \'\' as Int");
			
				Scanner testScanner = new Scanner("p5UninitilizedVariable.txt", br, st);
				Parser parser = new Parser(testScanner, storageManager);
				
				// Use this area to run tests
				ResultValue res = parser.statements(true);
					
				if (res.terminatingStr.equals("return"))
					throw new ParserException(testScanner.currentToken.iSourceLineNr,
							"return statment outside of function definition", testScanner.sourceFileName);
				//***
	}

	@Test
	public void  divideByZeroTest() throws Exception
	{
		FileReader testReader;
			// Set up the inital 'file' to be read
			testReader = new FileReader("p5DivideByZero.txt");
			BufferedReader br = new BufferedReader(testReader);
			SymbolTable st = new SymbolTable();
			StorageManager storageManager = new StorageManager();
			
		    expectedEx.expect(ParserException.class); // Here we need to 'expect' to throw an error
		    expectedEx.expectMessage("Dividing by zero detected");
			
				Scanner testScanner = new Scanner("p5DivideByZero.txt", br, st);
				Parser parser = new Parser(testScanner, storageManager);
				
				// Use this area to run tests
				ResultValue res = parser.statements(true);
					
				if (res.terminatingStr.equals("return"))
					throw new ParserException(testScanner.currentToken.iSourceLineNr,
							"return statment outside of function definition", testScanner.sourceFileName);
				//***
	}
}
