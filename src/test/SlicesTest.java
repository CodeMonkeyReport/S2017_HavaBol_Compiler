package test;

import static org.junit.Assert.*;
import havaBol.Parser;
import havaBol.ResultList;
import havaBol.Scanner;
import havaBol.StorageManager;
import havaBol.SymbolTable;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.Test;

public class SlicesTest {

	
	@Test
	public void arraySlice1()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int test[5] = 1, 2, 3, 4, 5;\n"
						 + "Int th = 3;\n"
						 + "Int splice[2];\n"
						 + "splice = test[~th];"
						 + "Int a = splice[0];\n"
						 + "Int b = splice[1];\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);

			assertEquals("1", storageManager.getVariableValue("a").internalValue);
			assertEquals("2", storageManager.getVariableValue("b").internalValue);
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void arraySlice2()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int test[5] = 1, 2, 3, 4, 5;\n"
						 + "Int begin = 2;\n"
						 + "Int end = 4;\n"
						 + "Int splice[2];\n"
						 + "splice = test[begin~end];\n"
						 + "Int a = splice[0];\n"
						 + "Int b = splice[1];\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);

			assertEquals("3", storageManager.getVariableValue("a").internalValue);
			assertEquals("4", storageManager.getVariableValue("b").internalValue);
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void arraySlice3()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int test[5] = 1, 2, 3, 4, 5;\n"
						 + "Int end = 3;\n"
						 + "Int splice[2];\n"
						 + "splice = test[end~];"
						 + "Int a = splice[0];\n"
						 + "Int b = splice[1];\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);

			assertEquals("4", storageManager.getVariableValue("a").internalValue);
			assertEquals("5", storageManager.getVariableValue("b").internalValue);
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void sliceString1()
	{
		// Set up the inital 'file' to be read
		String testInput = "String test='goodbye cruel world!';\n"
						 + "Int index = 7;\n"
						 + "String splice = test[~7];\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);

			assertEquals("goodbye", storageManager.getVariableValue("splice").internalValue);
			
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void sliceString2()
	{
		// Set up the inital 'file' to be read
		String testInput = "String test='goodbye cruel world!';\n"
						 + "Int begin = 8;\n"
						 + "Int end = 13;\n"
						 + "String splice = test[begin~end];\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);

			assertEquals("cruel", storageManager.getVariableValue("splice").internalValue);
			
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void sliceString3()
	{
		// Set up the inital 'file' to be read
		String testInput = "String test='goodbye cruel world!';\n"
						 + "Int index = 14;\n"
						 + "String splice = test[index~];\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);

			assertEquals("world!", storageManager.getVariableValue("splice").internalValue);
			
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
