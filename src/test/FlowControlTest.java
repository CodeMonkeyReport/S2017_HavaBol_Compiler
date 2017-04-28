package test;

import havaBol.Parser;
import havaBol.Scanner;
import havaBol.StorageManager;
import havaBol.SymbolTable;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.assertTrue;

public class FlowControlTest {

	
	@Test
	public void largeScaleSelectTest()
	{
		FileReader testReader;
		try {
			// Set up the inital 'file' to be read
			testReader = new FileReader("p5Select.txt");
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

	@Test
	public void largeScaleBreakContinueTest()
	{
		FileReader testReader;
		try {
			// Set up the inital 'file' to be read
			testReader = new FileReader("p5Break.txt");
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
