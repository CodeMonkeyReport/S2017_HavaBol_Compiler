package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import havaBol.Parser;
import havaBol.Scanner;
import havaBol.StorageManager;
import havaBol.SymbolTable;

public class ReferenceTest 
{
	
	@Rule
	public ExpectedException  expectedEx = ExpectedException.none();
	
	@Test
	public void largeScaleReferenceTestOne()
	{
		FileReader testReader;
		try {
			// Set up the inital 'file' to be read
			testReader = new FileReader("p5ReferenceOne.txt");
			BufferedReader br = new BufferedReader(testReader);
			SymbolTable st = new SymbolTable();
			StorageManager storageManager = new StorageManager();
			try {
				Scanner testScanner = new Scanner("p5ReferenceOne.txt", br, st);
				Parser parser = new Parser(testScanner, storageManager);
				
				// Use this area to run tests	
				parser.statements(true);
								
				assertEquals("15", storageManager.getVariableValue("a").internalValue);
				
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
	public void largeScaleReferenceTestTwo()
	{
		FileReader testReader;
		try {
			// Set up the inital 'file' to be read
			testReader = new FileReader("p5ReferenceTwo.txt");
			BufferedReader br = new BufferedReader(testReader);
			SymbolTable st = new SymbolTable();
			StorageManager storageManager = new StorageManager();
			try {
				Scanner testScanner = new Scanner("p5ReferenceTwo.txt", br, st);
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
	public void largeScaleReferenceTestThree()
	{
		FileReader testReader;
		try {
			// Set up the inital 'file' to be read
			testReader = new FileReader("p5ReferenceThree.txt");
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
