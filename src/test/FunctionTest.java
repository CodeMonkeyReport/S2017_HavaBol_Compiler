package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import havaBol.Parser;
import havaBol.ParserException;
import havaBol.STEntry;
import havaBol.STFunction;
import havaBol.Scanner;
import havaBol.StorageManager;
import havaBol.SymbolTable;

public class FunctionTest {

	@Rule
	public ExpectedException  expectedEx = ExpectedException.none();
	
	@Test
	public void simpleFunctionCallInExpression()
	{
		// Set up the inital 'file' to be read
		String testInput = "String empty;\n"
						 + "empty = \"  \";\n"
						 + "Bool true = SPACES(empty);\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("T", storageManager.getVariableValue("true").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void simpleUserDefinedFunctionCanBeDefinedTest()
	{
		// Set up the inital 'file' to be read
		String testInput = "func Int add(Int a, Int b):\n"
						 + "	return a + b;\n"
						 + "endfunc;\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			STEntry functionEntry = st.getSymbol("add");
			
			assertNotNull(functionEntry);
			assertTrue(functionEntry instanceof STFunction);
			
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void simpleUserDefinedFunctionCanBeExecuted()
	{
		// Set up the inital 'file' to be read
		String testInput = "func Int add(Int a, Int b):\n"
						 + "	return a + b;\n"
						 + "endfunc;\n"
						 + "Int i;\n"
						 + "i = add(1, 2);\n";
		
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
	public void simpleRecursiveUserDefinedFunctionCanBeExecuted()
	{
		// Set up the inital 'file' to be read
		String testInput = "func Int recursiveMultiply(Int a, Int b):\n"
						 + "	if b == 1:\n"
						 + "		return a;\n"
						 + "	endif;\n"
						 + "	return a + recursiveMultiply(a, b-1);\n"
						 + "endfunc;\n"
						 + "Int i;\n"
						 + "i = recursiveMultiply(2, 2);\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);

			assertEquals("4", storageManager.getVariableValue("i").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void functionCanAccessGlobalScopeVaraibles()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int c = 2;\n"
						 + "func Int globalMultiply(Int a, Int b):\n"
						 + "	return a * b * c;\n"
						 + "endfunc;\n"
						 + "Int i;\n"
						 + "i = globalMultiply(2, 2);\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);

			assertEquals("8", storageManager.getVariableValue("i").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	
	@Test
	public void functionCanNotAccessVaraiblesOutsideScope() throws Exception
	{
		// Set up the inital 'file' to be read
		String testInput = "Int c = 2;\n"
						 + "func Int multiply(Int a, Int b):\n"
						 + "	Int x = 0;"
						 + "	func Int recursiveMultiply(Int a, Int b):\n"
						 + "		if b == 1:\n"
						 + "			return a;\n"
						 + "		endif;\n"
						 + "		return a + recursiveMultiply(a, b-1);\n"
						 + "	endfunc;\n"
						 + "	x = recursiveMultiply(a, b);\n"
						 + "	return x;\n"
						 + "endfunc;\n"
						 + "func Int globalMultiply(Int a, Int b):\n"
						 + "	return a * b * x;\n"
						 + "endfunc;\n"
						 + "Int i;\n"
						 + "i = globalMultiply(2, 2);\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		
	    expectedEx.expect(ParserException.class); // Here we need to 'expect' to throw an error
	    expectedEx.expectMessage("Unknown operand \'x\' in expression");
	    
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);

			assertEquals("8", storageManager.getVariableValue("i").internalValue);
			
			//***
	}
	
	@Test
	public void functionCanChangeGlobalScopeVariables()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int c = 2;\n"
						 + "func Int globalSet(Int a, Int b):\n"
						 + "	c = a + b;\n"
						 + "endfunc;\n"
						 + "globalSet(2, 2);\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);

			assertEquals("4", storageManager.getVariableValue("c").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void functionCanBeDefinedInsideOfFunction()
	{
		// Set up the inital 'file' to be read
		String testInput = "func Int multiply(Int a, Int b):\n"
						 + ""
						 + "	func Int recursiveMultiply(Int a, Int b):\n"
						 + "		if b == 1:\n"
						 + "			return a;\n"
						 + "		endif;\n"
						 + "		return a + recursiveMultiply(a, b-1);\n"
						 + "	endfunc;\n"
						 + "	return recursiveMultiply(a, b);"
						 + "endfunc;"
						 + ""
						 + "\n"
						 + "Int i;\n"
						 + "i = multiply(2, 3);\n";
		
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
	public void functionOutsideScopeCanNotBeAccessed() throws Exception
	{
		// Set up the inital 'file' to be read
		String testInput = "func Int multiply(Int a, Int b):\n"
						 + ""
						 + "	func Int recursiveMultiply(Int a, Int b):\n"
						 + "		if b == 1:\n"
						 + "			return a;\n"
						 + "		endif;\n"
						 + "		return a + recursiveMultiply(a, b-1);\n"
						 + "	endfunc;\n"
						 + "	return recursiveMultiply(a, b);"
						 + "endfunc;"
						 + ""
						 + "\n"
						 + "Int i;\n"
						 + "i = recursiveMultiply(2, 3);\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		
		
	    expectedEx.expect(ParserException.class); // Here we need to 'expect' to throw an error
	    expectedEx.expectMessage("Unknown operand \'recursiveMultiply\' in expression");
		
		StorageManager storageManager = new StorageManager();
		
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			assertTrue(false);
	}
	
	
	@Test
	public void largeScaleFunctionTestOne()
	{
		FileReader testReader;
		try {
			// Set up the inital 'file' to be read
			testReader = new FileReader("p4Func.txt");
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
