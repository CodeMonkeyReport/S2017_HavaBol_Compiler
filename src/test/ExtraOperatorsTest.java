package test;

import static org.junit.Assert.*;
import havaBol.Parser;
import havaBol.Scanner;
import havaBol.StorageManager;
import havaBol.SymbolTable;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.Test;

public class ExtraOperatorsTest {

	@Test
	public void plusEqualsOperator()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int x = 0;\n"
						 + "x+=5;\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("5", storageManager.getVariableValue("x").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void minusEqualsOperator()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int x = 10;\n"
						 + "x-=5;\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);
			
			assertEquals("5", storageManager.getVariableValue("x").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
