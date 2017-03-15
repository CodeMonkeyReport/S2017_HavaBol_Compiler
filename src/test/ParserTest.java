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
			System.out.println(e.toString());
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
			System.out.println(e.toString());
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canAssignAcrossVariables()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 5;\n"
						 + "Int j = i;";
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
			
			testScanner.getNext();
			
			// Should now have i declared, now declare and initilize j
			parser.declareVarStmt();
			testScanner.getNext();
			parser.assignmentStmt();
			
			assertEquals("5", storageManager.getVariableValue("j").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canPerformArithmeticOnVariables()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 5;\n"
				         + "Int j = i;\n"
				         + "Int k = i * j;";
		
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
			
			testScanner.getNext();
			
			// Should now have i declared, now declare and initilize j
			parser.declareVarStmt();
			testScanner.getNext();
			parser.assignmentStmt();
			
			testScanner.getNext();
			
			// Should now have j declared, now declare and initilize k
			parser.declareVarStmt();
			testScanner.getNext();
			parser.assignmentStmt();
			
			assertEquals("25", storageManager.getVariableValue("k").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canTruncateIntegerValuesFromInteger()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 5;\n"
						 + "Int j = i / 2;";
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
			
			testScanner.getNext();
			
			// Should now have i declared, now declare and initilize j
			parser.declareVarStmt();
			testScanner.getNext();
			parser.assignmentStmt();
			
			assertEquals("2", storageManager.getVariableValue("j").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canTruncateIntegerValuesFromFloat()
	{
		// Set up the inital 'file' to be read
		String testInput = "Float f = 5.0;\n"
						 + "Int j = f / 2;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			testScanner.getNext(); // Read a single token
			
			// current token is now on Float, declare the variable and assign to it
			parser.declareVarStmt();
			testScanner.getNext();
			parser.assignmentStmt();
			
			testScanner.getNext();
			
			// Should now have f declared, now declare and initilize j
			parser.declareVarStmt();
			testScanner.getNext();
			parser.assignmentStmt();
			
			assertEquals("2", storageManager.getVariableValue("j").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canCalculateFloatValues()
	{
		// Set up the inital 'file' to be read
		String testInput = "Float f = 5.0;\n"
						 + "Float j = f / 2;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			testScanner.getNext(); // Read a single token
			
			// current token is now on Float, declare the variable and assign to it
			parser.declareVarStmt();
			testScanner.getNext();
			parser.assignmentStmt();
			
			testScanner.getNext();
			
			// Should now have f declared, now declare and initilize j
			parser.declareVarStmt();
			testScanner.getNext();
			parser.assignmentStmt();
			
			assertEquals("2.5", storageManager.getVariableValue("j").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canAddStringValuesToInts()
	{
		// Set up the inital 'file' to be read
		String testInput = "String s = \"5\";\n"
						 + "Int i = 2 + s;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			testScanner.getNext(); // Read a single token
			
			// current token is now on String, declare the variable and assign to it
			parser.declareVarStmt();
			testScanner.getNext();
			parser.assignmentStmt();
			
			testScanner.getNext();
			
			// Should now have s declared, now declare and initilize i
			parser.declareVarStmt();
			testScanner.getNext();
			parser.assignmentStmt();
			
			assertEquals("7", storageManager.getVariableValue("i").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canAddStringValuesToFloats()
	{
		// Set up the inital 'file' to be read
		String testInput = "String s = \"5.0\";\n"
						 + "Float f = 2.0 + s;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			testScanner.getNext(); // Read a single token
			
			// current token is now on String, declare the variable and assign to it
			parser.declareVarStmt();
			testScanner.getNext();
			parser.assignmentStmt();
			
			testScanner.getNext();
			
			// Should now have s declared, now declare and initilize i
			parser.declareVarStmt();
			testScanner.getNext();
			parser.assignmentStmt();
			
			assertEquals("7.0", storageManager.getVariableValue("f").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canAddAcrossStringIntAndFloatTypes()
	{
		// Set up the inital 'file' to be read
		String testInput = "String s = \"5.0\";\n"
						 + "Float f = 2.0 + s;\n"
						 + "Int i = f + s;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			testScanner.getNext(); // Read a single token
			
			// current token is now on String, declare the variable and assign to it
			parser.declareVarStmt();
			testScanner.getNext();
			parser.assignmentStmt();
			
			testScanner.getNext();
			
			// Should now have s declared, now declare and initilize f
			parser.declareVarStmt();
			testScanner.getNext();
			parser.assignmentStmt();
			
			testScanner.getNext();
			
			// Should now have f declared, now declare and initilize i
			parser.declareVarStmt();
			testScanner.getNext();
			parser.assignmentStmt();
			
			assertEquals("12", storageManager.getVariableValue("i").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canDoSimpleComparisons()
	{
		// Set up the inital 'file' to be read
		String testInput = "String s = \"5.0\";\n"
						 + "Float f = 5.0;\n"
						 + "Bool b = f == s;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			testScanner.getNext(); // Read a single token
			
			// current token is now on String, declare the variable and assign to it
			parser.declareVarStmt();
			testScanner.getNext();
			parser.assignmentStmt();
			
			testScanner.getNext();
			
			// Should now have s declared, now declare and initilize f
			parser.declareVarStmt();
			testScanner.getNext();
			parser.assignmentStmt();
			
			testScanner.getNext();
			
			// Should now have f declared, now declare and initilize b
			parser.declareVarStmt();
			testScanner.getNext();
			parser.assignmentStmt();
			
			assertEquals("T", storageManager.getVariableValue("b").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			assertTrue("Unable to read input stream", false);
		}
	}
}
