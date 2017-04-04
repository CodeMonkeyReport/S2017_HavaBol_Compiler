package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;

import org.junit.*;
import org.junit.rules.ExpectedException;

import havaBol.*;


public class IntegrationTest {
	
	@Rule
	public ExpectedException  expectedEx = ExpectedException.none();

	@Test
	public void simpleStatementsTest()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i;\n"
						 + "i = 12;\n"
						 + "Int j = 3 + i;";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("15", storageManager.getVariableValue("j").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			assertTrue(false);
		}
	}
	
	@Test
	public void errorUndeclaredIdentifier() throws Exception
	{
		// Set up the inital 'file' to be read
		String testInput = "s = \"5.0\";\n"
						 + "Float f = 5.0;\n"
						 + "Bool b = f == s;";
		
	    expectedEx.expect(ParserException.class); // Here we need to 'expect' to throw an error
	    expectedEx.expectMessage("Undeclared identifier: \'s\'");
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		Scanner testScanner = new Scanner("TEST", br, st);
		Parser parser = new Parser(testScanner, storageManager);
		
		// Use this area to run tests	
		parser.statements(true);
		
		//***
		assertTrue("Expected Undeclared identifier error", false);
	}



	@Test
	public void simpleIfStatementsTestOne()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 3;\n"
						 + "if F:"
						 + "  i = 12;\n"
						 + "endif;\n"
						 + "Int j = 3 + i;";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("6", storageManager.getVariableValue("j").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void simpleIfStatementsTestTwo()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 3;\n"
						 + "if T:"
						 + "  i = 12;\n"
						 + "endif;\n"
						 + "Int j = 3 + i;";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("15", storageManager.getVariableValue("j").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void nestedIfStatementsTestOne()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 3;\n"
						 + "if T:"
						 + "  if i == 3:\n"
						 + "     i = 12;\n"
						 + "  endif;\n"
						 + "endif;\n"
						 + "Int j = 3 + i;\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("15", storageManager.getVariableValue("j").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void nestedIfStatementsTestTwo()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 3;\n"
						 + "if T:"
						 + "  if F:\n"
						 + "     i = 12;\n"
						 + "  endif;\n"
						 + "endif;\n"
						 + "Int j = 3 + i;\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("6", storageManager.getVariableValue("j").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void simpleIfElseStatementsTestOne()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 1;\n"
						 + "if T:"
						 + "  i = 2;\n"
						 + "else:\n"
						 + "  i = 3;\n"
						 + "endif;\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("2", storageManager.getVariableValue("i").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void nestedIfElseStatementsTestOne()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 1;\n"
						 + "if F:"
						 + "  i = 2;\n"
						 + "else:\n"
						 + "  if T:"
						 + "    i = 3;\n"
						 + "  endif;\n"
						 + "endif;\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("3", storageManager.getVariableValue("i").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void nestedIfElseStatementsTestTwo()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 1;\n"
						 + "if F:"
						 + "  i = 2;\n"
						 + "else:\n"
						 + "  if F:"
						 + "    i = 3;\n"
						 + "  endif;\n"
						 + "endif;\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("1", storageManager.getVariableValue("i").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void simpleArraysTestOne()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 0;\n"
						 + "Int a[5];\n"
				         + "while i < 5:\n"
				         + "  a[i] = i*2;\n"
				         + "  i += 1;\n;"
				         + "endwhile;\n"
				         + "Int x = a[4];\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("8", storageManager.getVariableValue("x").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void simpleWhileStatementTestOne()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 0;\n"
				         + "while i < 5:\n"
				         + "  i += 1;\n;"
				         + "endwhile;";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("5", storageManager.getVariableValue("i").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void simpleFunctionCallInExpression()
	{
		// Set up the inital 'file' to be read
		String testInput = "String s = \"Hello World\";\n"
						 + "Int i = 2 * LENGTH(s);\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("22", storageManager.getVariableValue("i").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void simpleWhileStatementTestTwo()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 0;\n"
				         + "while i < 5:\n"
				         + "  i += 2;\n;"
				         + "endwhile;";

		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("6", storageManager.getVariableValue("i").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void nestedWhileStatementTest()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 0;\n"
						 + "Int j;\n"
						 + "Int k = 0;\n"
				         + "while i < 5:\n"
				         + "  j = 0;\n"
				         + "  while j < 5:\n"
				         + "    j += 1;\n"
				         + "    k += 2;\n;"
				         + "  endwhile;\n"
				         + "  i += 1;\n"
				         + "endwhile;\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("50", storageManager.getVariableValue("k").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void nestedIfWhileStatementTest()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 0;\n"
						 + "Int j;\n"
						 + "Int k = 0;\n"
				         + "while i < 5:\n"
				         + "  j = 0;\n"
				         + "  while j < 5:\n"
				         + "    j += 1;\n"
				         + "    if k < 25:\n"
				         + "      k += 2;\n;"
				         + "    endif;"
				         + "  endwhile;\n"
				         + "  i += 1;\n"
				         + "endwhile;\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			
			parser.statements(true);
			
			assertEquals("26", storageManager.getVariableValue("k").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void simpleForLoopStatment()
	{
		// Set up the inital 'file' to be read
		String testInput = ""
						 + "Int j;\n"
						 + "Int i;\n"
						 + "Int k = 0;\n"
				         + "for i = 0 to 5 by 1:\n"
				         + "  j = 0;\n"
				         + "  while j < 5:\n"
				         + "    j += 1;\n"
				         + "    if k < 25:\n"
				         + "      k += 2;\n;"
				         + "    endif;"
				         + "  endwhile;\n"
				         + "  i += 1;\n"
				         + "endfor;\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("26", storageManager.getVariableValue("k").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void simpleForLoopStatmentWithDeclare()
	{
		// Set up the inital 'file' to be read
		String testInput = ""
						 + "Int j;\n"
						 + "\n"
						 + "Int k = 0;\n"
				         + "for Int i = 0 to 5 by 1:\n"
				         + "  j = 0;\n"
				         + "  while j < 5:\n"
				         + "    j += 1;\n"
				         + "    if k < 25:\n"
				         + "      k += 2;\n;"
				         + "    endif;"
				         + "  endwhile;\n"
				         + "  i += 1;\n"
				         + "endfor;\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("26", storageManager.getVariableValue("k").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void largeScaleTestOne()
	{
		FileReader testReader;
		try {
			// Set up the inital 'file' to be read
			testReader = new FileReader("p3Input.txt");
			BufferedReader br = new BufferedReader(testReader);
			SymbolTable st = new SymbolTable();
			StorageManager storageManager = new StorageManager();
			try {
				Scanner testScanner = new Scanner("TEST", br, st);
				Parser parser = new Parser(testScanner, storageManager);
				
				// Use this area to run tests	
				parser.statements(true);
								
				//***
			} catch (Exception e) {
				System.out.println(e.toString());
				e.printStackTrace();
				assertTrue(false);
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void largeScaleTestTwo()
	{
		FileReader testReader;
		try {
			// Set up the inital 'file' to be read
			testReader = new FileReader("p3SimpleExpr.txt");
			BufferedReader br = new BufferedReader(testReader);
			SymbolTable st = new SymbolTable();
			StorageManager storageManager = new StorageManager();
			try {
				Scanner testScanner = new Scanner("TEST", br, st);
				Parser parser = new Parser(testScanner, storageManager);
				
				// Use this area to run tests	
				parser.statements(true);
								
				//***
			} catch (Exception e) {
				System.out.println(e.toString());
				e.printStackTrace();
				assertTrue(false);
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			assertTrue(false);
		}
	}
}
