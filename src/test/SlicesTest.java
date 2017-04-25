package test;

import static org.junit.Assert.*;
import havaBol.Parser;
import havaBol.Scanner;
import havaBol.StorageManager;
import havaBol.SymbolTable;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.Test;

public class SlicesTest {

	@Test
	public void stringSliceAssignment()
	{
		// Set up the inital 'file' to be read
		String testInput = "String str = 'goodbye';\n"
						 + "Int ze = 0;\n"
						 + "Int th = 3;\n"
						 + "String good = str[ze~4];\n"
						 + "String goo = str[~th];\n"
						 + "String bye = str[4~];\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);

			assertEquals("good", storageManager.getVariableValue("good").internalValue);
			assertEquals("goo", storageManager.getVariableValue("goo").internalValue);
			assertEquals("bye", storageManager.getVariableValue("bye").internalValue);
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			System.out.println("good = " + storageManager.getVariableValue("good").internalValue);
			System.out.println("goo = " + storageManager.getVariableValue("goo").internalValue);
			System.out.println("bye = " + storageManager.getVariableValue("bye").internalValue);
			assertTrue(false);
		}
	}
	
	/*@Test
	public void arraySliceAssignment()
	{
		// Set up the inital 'file' to be read
		String testInput = "String str[5] = 'one', 'two', 'three', 'four', 'five';\n"
						 + "Int th = 3;\n"
						 + "String stg[5];\n"
						 + "stg = str[th~];\n"
						 + "String s = stg[0]";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			parser.statements(true);

			assertEquals("s", storageManager.getVariableValue("four").internalValue);
			
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue(false);
		}
	}*/

}
