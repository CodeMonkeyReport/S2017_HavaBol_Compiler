package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.Test;

import havaBol.Parser;
import havaBol.Scanner;
import havaBol.StorageManager;
import havaBol.SymbolTable;

public class TupleTest {

	
	@Test
	public void simpleOneTupleTest()
	{
		// Set up the inital 'file' to be read
		String testInput = "tuple MyTuple:\n"
						 + "    Int number;\n"
						 + "endtuple;\n"
						 + "\n"
						 + "MyTuple example;\n"
						 + "example.number = 5;\n"
						 + "Int test = example.number;\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("5", storageManager.getVariableValue("test").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void simpleMultiTupleTest()
	{
		// Set up the inital 'file' to be read
		String testInput = "tuple MyTuple:\n"
						 + "    Int number;\n"
						 + "	Float floatNumber;\n"
						 + "endtuple;\n"
						 + "\n"
						 + "MyTuple example;\n"
						 + "example.number = 5;\n"
						 + "example.floatNumber = 6.0;\n"
						 + "Float test = example.floatNumber;\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("6.0", storageManager.getVariableValue("test").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void tupleWithArrayTest()
	{
		// Set up the inital 'file' to be read
		String testInput = "tuple MyTuple:\n"
						 + "    Int numbers[2];\n"
						 + "	Float floatNumber;\n"
						 + "endtuple;\n"
						 + "\n"
						 + "MyTuple example;\n"
						 + "example.numbers[0] = 5;\n"
						 + "example.numbers[1] = 3;\n"
						 + "example.floatNumber = 6.0;\n"
						 + "Float test = example.numbers[1];\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("3.0", storageManager.getVariableValue("test").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void arrayOfTuplesTest()
	{
		// Set up the inital 'file' to be read
		String testInput = "tuple MyTuple:\n"
						 + "    Int number;\n"
						 + "endtuple;\n"
						 + "\n"
						 + "MyTuple exampleArray[2];\n"
						 + "\n"
						 + "MyTuple exampleOne;\n"
						 + "exampleOne.number = 1;\n"
						 + "\n"
						 + "MyTuple exampleTwo;\n"
						 + "exampleTwo.number = 2;\n"
						 + "exampleArray[0] = exampleOne;\n"
						 + "exampleArray[1] = exampleTwo;\n"
						 + "Int test = exampleArray[0].number;\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("1", storageManager.getVariableValue("test").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void arrayOfTuplesWithArrayTest()
	{
		// Set up the inital 'file' to be read
		String testInput = "tuple MyTuple:\n"
						 + "    Int numbers[10];\n"
						 + "endtuple;\n"
						 + "\n"
						 + "MyTuple exampleArray[2];\n"
						 + "\n"
						 + "MyTuple exampleOne;\n"
						 + "exampleOne.numbers = 1;\n"
						 + "\n"
						 + "MyTuple exampleTwo;\n"
						 + "exampleTwo.numbers = 2;\n"
						 + "exampleArray[0] = exampleOne;\n"
						 + "exampleArray[1] = exampleTwo;\n"
						 + "Int test = exampleArray[0].numbers[5];\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("1", storageManager.getVariableValue("test").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void nestedTuplesTest()
	{
		// Set up the inital 'file' to be read
		String testInput = "tuple Inner:\n"
						 + "    Int numbers[10];\n"
						 + "endtuple;\n"
						 + "tuple Outer:\n"
						 + "	Inner inner;\n"
						 + "endtuple;\n"
						 + "\n"
						 + "Outer outer;\n"
						 + "\n"
						 + "Inner inner;\n"
						 + "inner.numbers = 1;\n"
						 + "\n"
						 + "outer.inner = inner;\n"
						 + "Int test = outer.inner.numbers[0];\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("1", storageManager.getVariableValue("test").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void trippleNestedArrayOfTuplesTest()
	{
		// Set up the inital 'file' to be read
		String testInput = "tuple Inner:\n"
						 + "    Int numbers[10];\n"
						 + "endtuple;\n"
						 + "tuple Outer:\n"
						 + "	Inner inner[1];\n"
						 + "endtuple;\n"
						 + "\n"
						 + "Outer outerArray[1];\n"
						 + "Outer outerOne;\n"
						 + "\n"
						 + "Inner innerOne;\n"
						 + "innerOne.numbers = 1;\n"
						 + "\n"
						 + "outerOne.inner[0] = innerOne;\n"
						 + "outerArray[0] = outerOne;\n"
						 + "Int test = outerArray[0].inner[0].numbers[0];\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("1", storageManager.getVariableValue("test").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
}

