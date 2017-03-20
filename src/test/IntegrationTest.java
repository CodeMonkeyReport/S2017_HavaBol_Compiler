package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.*;
import havaBol.*;


public class IntegrationTest {

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
	public void simpleIfStatementsTestOne()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 3;\n"
						 + "if F:"
						 + "  i = 12;\n"
						 + "endif\n"
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
						 + "endif\n"
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
	public void simpleIfElseStatementsTestOne()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 1;\n"
						 + "if T:"
						 + "  i = 2;\n"
						 + "else:\n"
						 + "  i = 3;\n"
						 + "endif\n";
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
	public void simpleWhileStatementTestOne()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 0;\n"
				         + "while i < 5:\n"
				         + "  i += 1;\n;"
				         + "endwhile";
		
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
	public void simpleWhileStatementTestTwo()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 0;\n"
				         + "while i < 5:\n"
				         + "  i += 2;\n;"
				         + "endwhile";
		
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
}
