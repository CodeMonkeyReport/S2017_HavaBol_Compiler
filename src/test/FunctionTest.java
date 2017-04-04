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

public class FunctionTest {

	
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
}
