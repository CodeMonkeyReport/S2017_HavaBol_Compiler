package test;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Test;

import havaBol.Parser;
import havaBol.Scanner;
import havaBol.StorageManager;
import havaBol.SymbolTable;

public class StringTest {

	
	@Test
	public void largeScaleStringTestOne()
	{
		FileReader testReader;
		try {
			// Set up the inital 'file' to be read
			testReader = new FileReader("p4String.txt");
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
