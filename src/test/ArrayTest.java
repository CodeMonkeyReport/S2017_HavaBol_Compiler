package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import havaBol.Parser;
import havaBol.ParserException;
import havaBol.Scanner;
import havaBol.StorageManager;
import havaBol.SymbolTable;

public class ArrayTest {
	
	@Rule
	public ExpectedException  expectedEx = ExpectedException.none();
	
	@Test
	public void simpleArrayDeclarationWithoutInitThrowsException() throws Exception
	{
		// Set up the initial 'file' to be read
		String testInput = "String empty[]; \n";
		
		
	    expectedEx.expect(ParserException.class); // Here we need to 'expect' to throw an error
	    expectedEx.expectMessage("Expected argument list for initilization of String array \'empty\'");
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
			
		Scanner testScanner = new Scanner("TEST", br, st);
		Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests
			
			parser.statements(true);
			
			assertTrue("Expected index out of bounds error", false);
			
			//***

	}
	
	@Test
	public void arrayAccessOutOfBoundsThrowsException() throws Exception
	{
		// Set up the initial 'file' to be read
		String testInput = "String empty[5]; \n"
						 + "empty[10] = \"Hello World\"; \n";
		
	    expectedEx.expect(ParserException.class); // Here we need to 'expect' to throw an error
	    expectedEx.expectMessage("Array index out of bounds: 10");
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		Scanner testScanner = new Scanner("TEST", br, st);
		Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			
			parser.statements(true);
			
			assertTrue("Expected missing argument list error", false);
			
			//***
	}
	
	@Test
	public void arrayInitInvailidTypesThrowsException() throws Exception
	{
		// Set up the initial 'file' to be read
		String testInput = "Bool bools[5] = T, 3, F; \n";
		
	    expectedEx.expect(ParserException.class); // Here we need to 'expect' to throw an error
	    expectedEx.expectMessage("Can not parse \'3\' as Bool");
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		Scanner testScanner = new Scanner("TEST", br, st);
		Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests
			
			parser.statements(true);
			
			assertTrue("Expected missing argument list error", false);
			
			//***
	}

	@Test
	public void arrayInitilizationOutOfBoundsThrowsException() throws Exception
	{
		// Set up the initial 'file' to be read
		String testInput = "Bool bools[2] = T, F, F, F; \n";
		
	    expectedEx.expect(ParserException.class); // Here we need to 'expect' to throw an error
	    expectedEx.expectMessage("Array index out of bounds: 2");
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		Scanner testScanner = new Scanner("TEST", br, st);
		Parser parser = new Parser(testScanner, storageManager);
		
			// Use this area to run tests
			
			parser.statements(true);
			
			assertTrue("Expected missing argument list error", false);
			
			//***
	}
	
	@Test
	public void arrayInitilizationTypeErrorException() throws Exception
	{
		// Set up the initial 'file' to be read
		String testInput = "Bool bools[F] = T, F, F, F; \n";
		
	    expectedEx.expect(ParserException.class); // Here we need to 'expect' to throw an error
	    expectedEx.expectMessage("Can not parse \'F\' as Int");
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		Scanner testScanner = new Scanner("TEST", br, st);
		Parser parser = new Parser(testScanner, storageManager);
		
			// Use this area to run tests
			
			parser.statements(true);
			
			assertTrue("Expected missing argument list error", false);
			
			//***
	}
	
	@Test
	public void arrayInitilizationTypeErrorWithVarThrowsException() throws Exception
	{
		// Set up the initial 'file' to be read
		String testInput = "Bool false = F;\n"
				         + "Bool bools[false] = T, F, F, F; \n";
		
	    expectedEx.expect(ParserException.class); // Here we need to 'expect' to throw an error
	    expectedEx.expectMessage("Can not parse \'F\' as Int");
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		Scanner testScanner = new Scanner("TEST", br, st);
		Parser parser = new Parser(testScanner, storageManager);
		
			// Use this area to run tests
			
			parser.statements(true);
			
			assertTrue("Expected missing argument list error", false);
			
			//***
	}
	
	@Test
	public void unboundedArraysGrow()
	{
		// Set up the inital 'file' to be read
		String testInput = ""
						 + "Int a[unbounded];\n"
						 + "a[10] = 5;\n"
						 + "Int b = a[10];\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("5", storageManager.getVariableValue("b").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void unboundedArrayDefaultValueIsSet()
	{
		// Set up the inital 'file' to be read
		String testInput = ""
						 + "Int a[unbounded] = 0;\n"
						 + "a[10] = 5;\n"
						 + "Int b = a[0];\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("0", storageManager.getVariableValue("b").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void negativeIndexingSelectsCorrectly()
	{
		// Set up the inital 'file' to be read
		String testInput = ""
						 + "Int a[] = 1, 3, 9, 27;\n"
						 + "Int b = a[-1];\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("27", storageManager.getVariableValue("b").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void negativeIndexingSelectsCorrectlyOnUnboundedArrays()
	{
		// Set up the inital 'file' to be read
		String testInput = ""
						 + "Int a[unbounded] = 0;\n"
						 + "a[10] = 5;\n"
						 + "Int b = a[-1];\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("5", storageManager.getVariableValue("b").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
