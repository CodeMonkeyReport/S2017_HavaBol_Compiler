package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.*;
import havaBol.*;

public class ParserTest {

	@Test
	public void canDeclareIntValues()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			testScanner.getNext(); // Read a single token
			
			// current token is now on Int, declare the variable
			parser.declareVarStmt();
			
			assertNotNull(storageManager.getVariableValue("i"));
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canInitilizeValues()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 5;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			testScanner.getNext(); // Read a single token
			
			// current token is now on Int, declare the variable and assign to it
			parser.declareVarStmt();
			testScanner.getNext();
			parser.assignmentStmt();
			
			assertEquals(storageManager.getVariableValue("i").internalValue, "5");
			//***
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canReadSimpleExpressions()
	{
		// Set up the inital 'file' to be read
		String testInput = "5;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			testScanner.getNext(); // Read a single token
			
			// current token is now on 5, read the expression
			ResultValue res = parser.expression();
			
			assertEquals("5", res.internalValue);
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canReadAdditionExpressions()
	{
		// Set up the inital 'file' to be read
		String testInput = "5 + 3;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			testScanner.getNext(); // Read a single token
			
			// current token is now on 5, read the expression
			ResultValue res = parser.expression();
			
			assertEquals("8", res.internalValue);
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	
	@Test
	public void canReadMultiplicationExpressions()
	{
		// Set up the inital 'file' to be read
		String testInput = "5 * 3;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			testScanner.getNext(); // Read a single token
			
			// current token is now on 5, read the expression
			ResultValue res = parser.expression();
			
			assertEquals("15", res.internalValue);
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canReadSubtractionExpressions()
	{
		// Set up the inital 'file' to be read
		String testInput = "5 - 3;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			testScanner.getNext(); // Read a single token
			
			// current token is now on 5, read the expression
			ResultValue res = parser.expression();
			
			assertEquals("2", res.internalValue);
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canReadDivisionExpressions()
	{
		// Set up the inital 'file' to be read
		String testInput = "10 / 2;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			testScanner.getNext(); // Read a single token
			
			// current token is now on 10, read the expression
			ResultValue res = parser.expression();
			
			assertEquals("5", res.internalValue);
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canReadParenthesizedExpressions()
	{
		// Set up the inital 'file' to be read
		String testInput = "10 * (5 + 2);";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			testScanner.getNext(); // Read a single token
			
			// current token is now on 10, read the expression
			ResultValue res = parser.expression();
			
			assertEquals("70", res.internalValue);
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canReadUnaryMinusExpressions()
	{
		// Set up the inital 'file' to be read
		String testInput = "-6;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			testScanner.getNext(); // Read a single token
			
			// current token is now on 10, read the expression
			ResultValue res = parser.expression();
			
			assertEquals("-6", res.internalValue);
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canReadComplexExpressions()
	{
		// Set up the inital 'file' to be read
		String testInput = "10 * (5 + 2) - 15 + 4 * 6;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			testScanner.getNext(); // Read a single token
			
			// current token is now on 10, read the expression
			ResultValue res = parser.expression();
			
			assertEquals("79", res.internalValue);
			
			//***
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue("Unable to read input stream", false);
		}
	}
}
