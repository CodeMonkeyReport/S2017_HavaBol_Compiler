package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.*;
import havaBol.*;

public class ParserTest {

	@Test
	public void canDeclareIntValues()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			testScanner.getNext(); // Read a single token
			
			// current token is now on Int, declare the variable
			parser.declareVarStmt();
			
			assertNotNull(storageManager.getVariableValue("i"));
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canInitilizeIntValues()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 5;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		SymbolTable st = new SymbolTable();
		StorageManager storageManager = new StorageManager();
		try {
			Scanner testScanner = new Scanner("TEST", br, st);
			Parser parser = new Parser(testScanner, storageManager);
			
			// Use this area to run tests	
			testScanner.getNext(); // Read a single token
			
			// current token is now on Int, declare the variable
			parser.declareVarStmt();
			
			assertNotNull(storageManager.getVariableValue("i"));
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
}
