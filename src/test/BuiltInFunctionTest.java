package test;

import static org.junit.Assert.*;
import havaBol.Parser;
import havaBol.Scanner;
import havaBol.StorageManager;
import havaBol.SymbolTable;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.Test;

public class BuiltInFunctionTest {
	
	@Test
	public void spacesTest()
	{
		// Set up the inital 'file' to be read
		String testInput = "String empty;\n"
						 + "empty = '  ';\n"
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
	public void spacesTest2()
	{
		// Set up the inital 'file' to be read
		String testInput = "String nonempty;\n"
						 + "nonempty = '///n';\n"
						 + "Bool true = SPACES(nonempty);\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("F", storageManager.getVariableValue("true").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	@Test
	public void LengthTest()
	{
		// Set up the inital 'file' to be read
		String testInput = "String test='length';\n"
						 + "Int x = LENGTH(test);\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("6", storageManager.getVariableValue("x").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void elemTest()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int x[10] = 1,2,3,4,5;\n"
						 + "Int y = ELEM(x);\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("5", storageManager.getVariableValue("y").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void maxelemTest()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int x[10] = 1,2,3,4,5;\n"
						 + "Int y = MAXELEM(x);\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("10", storageManager.getVariableValue("y").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void dateDiffTest1()
	{
		// Set up the inital 'file' to be read
		String testInput = "Date d1 = '2017-03-01';\n"
						 + "Date d2 = '2016-03-01';\n"
						 + "Int y = dateDiff(d1,d2);\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("365", storageManager.getVariableValue("y").internalValue);
			
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void dateDiffTest2()
	{
		// Set up the inital 'file' to be read
		String testInput = "Date d1 = '2017-02-01';\n"
						 + "Date d2 = '2016-02-01';\n"
						 + "Int y = dateDiff(d1,d2);\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("366", storageManager.getVariableValue("y").internalValue);
			
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void dateAdjTest1()
	{
		// Set up the inital 'file' to be read
		String testInput = "Date d1 = '2017-03-01';\n"
						 + "Int adj = -365;\n"
						 + "Date y = dateAdj(d1,adj);\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("2016-03-01", storageManager.getVariableValue("y").internalValue);
			
		} catch (Exception e) {
			System.out.println(storageManager.getVariableValue("y").internalValue);
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void dateAdjTest2()
	{
		// Set up the inital 'file' to be read
		String testInput = "Date d1 = '2017-03-01';\n"
						 + "Int adj = 365;\n"
						 + "Date y = dateAdj(d1,adj);\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("2018-03-01", storageManager.getVariableValue("y").internalValue);
			
		} catch (Exception e) {
			System.out.println(storageManager.getVariableValue("y").internalValue);
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void dateAgeTest1()
	{
		// Set up the inital 'file' to be read
		String testInput = "Date d1 = '2017-03-01';\n"
						 + "Date d2 = '2016-03-01';\n"
						 + "Int y = dateAge(d1,d2);\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("1", storageManager.getVariableValue("y").internalValue);
			
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void dateAgeTest2()
	{
		// Set up the inital 'file' to be read
		String testInput = "Date d1 = '2017-02-01';\n"
						 + "Date d2 = '2016-02-01';\n"
						 + "Int y = dateAge(d1,d2);\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("1", storageManager.getVariableValue("y").internalValue);
			
		} catch (Exception e) {
			System.out.println(storageManager.getVariableValue("y").internalValue);
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void dateAgeTest3()
	{
		// Set up the inital 'file' to be read
		String testInput = "Date d1 = '2017-02-01';\n"
						 + "Date d2 = '1957-12-04';\n"
						 + "Int y = dateAge(d1,d2);\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("59", storageManager.getVariableValue("y").internalValue);
			
		} catch (Exception e) {
			System.out.println(storageManager.getVariableValue("y").internalValue);
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	
	
}
