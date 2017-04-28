package test;

import static org.junit.Assert.*;
import havaBol.Parser;
import havaBol.Scanner;
import havaBol.StorageManager;
import havaBol.SymbolTable;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.Test;

public class DataTypeTest {

	@Test
	public void declareIntTestPass()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int x = 10;\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("10", storageManager.getVariableValue("x").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void declareIntTestFail()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int x = 'hello';\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			fail();
			
			//***
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void declareFloatTestPass()
	{
		// Set up the inital 'file' to be read
		String testInput = "Float x = 10.505;\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("10.505", storageManager.getVariableValue("x").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void declareFloatTestFail()
	{
		// Set up the inital 'file' to be read
		String testInput = "Float x = 'fail';\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			fail();
			
			//***
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void declareBoolTestPass()
	{
		// Set up the inital 'file' to be read
		String testInput = "Bool x = T;\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("T", storageManager.getVariableValue("x").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void declareBoolTestFail()
	{
		// Set up the inital 'file' to be read
		String testInput = "Bool x = 10;\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			fail();
			
			//***
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void declareStringTestPass()
	{
		// Set up the inital 'file' to be read
		String testInput = "String x = 'hello';\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("hello", storageManager.getVariableValue("x").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	
	@Test
	public void declareDateTestPass()
	{
		// Set up the inital 'file' to be read
		String testInput = "Date x = '2017-04-27';\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("2017-04-27", storageManager.getVariableValue("x").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void declareDateTestFail()
	{
		// Set up the inital 'file' to be read
		String testInput = "Date x = '07-08-1197';\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			fail();
			
			//***
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	




}
