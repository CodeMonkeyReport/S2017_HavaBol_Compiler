package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

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
			parser.declareStmt(true);
			
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
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
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
			String terminatingString = ";";
			ResultValue res = parser.expression(terminatingString);
			
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
			String terminatingString = ";";
			ResultValue res = parser.expression(terminatingString);
			
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
			String terminatingString = ";";
			ResultValue res = parser.expression(terminatingString);
			
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
			String terminatingString = ";";
			ResultValue res = parser.expression(terminatingString);
			
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
			String terminatingString = ";";
			ResultValue res = parser.expression(terminatingString);
			
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
			String terminatingString = ";";
			ResultValue res = parser.expression(terminatingString);
			
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
			String terminatingString = ";";
			ResultValue res = parser.expression(terminatingString);
			
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
			String terminatingString = ";";
			ResultValue res = parser.expression(terminatingString);
			
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
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
			testScanner.getNext();
			
			// Should now have i declared, now declare and initilize j
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
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
				         + "Int k = i * j;\n";
		
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
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
			testScanner.getNext();
			
			// Should now have i declared, now declare and initilize j
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
			testScanner.getNext();
			
			// Should now have j declared, now declare and initilize k
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
			assertEquals("25", storageManager.getVariableValue("k").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue("Unable to read input stream", false);
		}
	}
	
	
	@Test
	public void canPerformArithmeticOnVariablesWithUnaryMinus()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 5;\n"
				         + "Int j = i;\n"
				         + "Int k = i * -j;\n";
		
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
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
			testScanner.getNext();
			
			// Should now have i declared, now declare and initilize j
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
			testScanner.getNext();
			
			// Should now have j declared, now declare and initilize k
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
			assertEquals("-25", storageManager.getVariableValue("k").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
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
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
			testScanner.getNext();
			
			// Should now have i declared, now declare and initilize j
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
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
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
			testScanner.getNext();
			
			// Should now have f declared, now declare and initilize j
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
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
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
			testScanner.getNext();
			
			// Should now have f declared, now declare and initilize j
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
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
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
			testScanner.getNext();
			
			// Should now have s declared, now declare and initilize i
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
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
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
			testScanner.getNext();
			
			// Should now have s declared, now declare and initilize i
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
			assertEquals("7.0", storageManager.getVariableValue("f").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canCoerceToFloat()
	{
		// Set up the inital 'file' to be read
		String testInput = "String s = \"5.0\";\n"
						 + "Float f = s + \"1\";\n";
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
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
			testScanner.getNext();
			
			// Should now have s declared, now declare and initilize i
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
			assertEquals("6.0", storageManager.getVariableValue("f").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue("Unable to read input stream", false);
		}
	}

	@Test
	public void canReadArgumentLists()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 1;\n"
						 + "Float f = 2.0;\n"
						 + "0, i, f, f+i;\n";
		
		ArrayList<ResultValue> list;
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			testScanner.getNext(); // Read a single token
			
			parser.declareStmt(true);
			parser.assignmentStmt(true);
			testScanner.getNext();
			
			parser.declareStmt(true);
			parser.assignmentStmt(true);
			testScanner.getNext();
			
			list = parser.argList(";");
			
			assertEquals("0", list.get(0).internalValue);
			assertEquals("1", list.get(1).internalValue);
			assertEquals("2.0", list.get(2).internalValue);
			assertEquals("3.0", list.get(3).internalValue);
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canDeclareIntArrayWithConstant()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int a[5];";
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
			parser.declareStmt(true);
			
			assertNotNull(storageManager.getVariableValue("a"));
			
			//***
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canDeclareIntArrayWithUnbound()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int a[unbounded];";
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
			parser.declareStmt(true);
			
			assertNotNull(storageManager.getVariableValue("a"));
			
			//***
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canDeclareIntArrayWithInitilization()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int a[] = 5, 4, 6;";
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
			parser.declareStmt(true);
			
			assertNotNull(storageManager.getVariableValue("a"));
			
			ResultList resList = (ResultList)storageManager.getVariableValue("a");
			assertEquals("5", resList.get(parser, 0).internalValue);
			assertEquals("4", resList.get(parser, 1).internalValue);
			assertEquals("6", resList.get(parser, 2).internalValue);
			
			
			//***
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue("Unable to read input stream", false);
		}
	}

	@Test
	public void canDeclareIntArrayWithScalarInitilization()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int a[3] = 5;";
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
			parser.declareStmt(true);
			
			assertNotNull(storageManager.getVariableValue("a"));

			ResultList resList = (ResultList)storageManager.getVariableValue("a");
			assertEquals("5", resList.get(parser, 0).internalValue);
			assertEquals("5", resList.get(parser, 1).internalValue);
			assertEquals("5", resList.get(parser, 2).internalValue);

			//***
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canTakeArraySubAsSource()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int a[3] = 5;\n"
						 + "Int x = a[1];\n";
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
			parser.declareStmt(true);
			testScanner.getNext();
			parser.declareStmt(true);
			parser.assignmentStmt(true);
			
			assertNotNull(storageManager.getVariableValue("x"));

			ResultValue res = storageManager.getVariableValue("x");

			assertEquals("5", res.getInternalValue());
			//***
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue("Unable to read input stream", false);
		}
	}

	@Test
	public void canTakeArraySubAsSourceInComplexExpression()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int a[3] = 5;\n"
						 + "Int x = a[1] * 5 - (12 - a[2]);\n";
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
			parser.declareStmt(true);
			testScanner.getNext();
			parser.declareStmt(true);
			parser.assignmentStmt(true);
			
			assertNotNull(storageManager.getVariableValue("x"));

			ResultValue res = storageManager.getVariableValue("x");

			assertEquals("18", res.getInternalValue());
			//***
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canTakeArraySubAsDestinationInAssignment()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int a[3] = 5;\n"
						 + "a[0] = a[1] * 5 - (12 - a[2]);\n"
						 + "Int x = a[0];\n";
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
			parser.declareStmt(true);
			testScanner.getNext();
			parser.assignmentStmt(true);
			testScanner.getNext();
			
			parser.declareStmt(true);
			parser.assignmentStmt(true);
			
			assertNotNull(storageManager.getVariableValue("x"));

			ResultValue res = storageManager.getVariableValue("x");

			assertEquals("18", res.getInternalValue());
			//***
		} catch (Exception e) {
			e.printStackTrace();
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
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
			testScanner.getNext();
			
			// Should now have s declared, now declare and initilize f
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
			testScanner.getNext();
			
			// Should now have f declared, now declare and initilize i
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
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
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
			testScanner.getNext();
			
			// Should now have s declared, now declare and initilize f
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
			testScanner.getNext();
			
			// Should now have f declared, now declare and initilize b
			parser.declareStmt(true);

			parser.assignmentStmt(true);
			
			assertEquals("T", storageManager.getVariableValue("b").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			assertTrue("Unable to read input stream", false);
		}
	}
	
}
