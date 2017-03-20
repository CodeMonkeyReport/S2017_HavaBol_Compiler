package test;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.*;
import havaBol.*;
/**
 * Tests related to the havaBol.Scanner
 * 
 * @author ckw273
 *
 */
public class ScannerTest {

	@Test
	public void canReadFirstToken() 
	{
		// Set up the inital 'file' to be read
		String testInput = "Int i = 5;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests
			
			testScanner.getNext(); // Read a single token
			Token testToken = testScanner.currentToken; // get the token
			
			assertEquals("Int", testToken.tokenStr); // check the token
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canMatchLineNumbers() 
	{
		// Set up the inital 'file' to be read
		String testInput = "x\n"
		         		 + "y\n"
		         		 + "Z Int i\n"
		         		 + "Hello\n"
		         		 + "Int\n";
		
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests
			
			testScanner.getNext(); // Read a single token
			Token testToken = testScanner.currentToken; // get the token
			assertEquals(1, testToken.iSourceLineNr); // check the token
			
			testScanner.getNext(); // Read a single token
			testToken = testScanner.currentToken; // get the token
			assertEquals(2, testToken.iSourceLineNr); // check the token
			
			testScanner.getNext(); // Read a single token
			testToken = testScanner.currentToken; // get the token
			assertEquals(3, testToken.iSourceLineNr); // check the token
			
			testScanner.getNext(); // Read a single token
			testToken = testScanner.currentToken; // get the token
			assertEquals(3, testToken.iSourceLineNr); // check the token
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canReadNextToken()
	{
		// Set up the inital 'file' to be read
		String testInput = "a b";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests 
			testScanner.getNext();
			
			testToken = testScanner.currentToken;
			
			assertEquals("a", testToken.tokenStr); // check the token

			
			testToken = testScanner.nextToken;
			
			assertEquals("b", testToken.tokenStr); // check the token
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canJumpToPosition()
	{
		// Set up the inital 'file' to be read
		String testInput = "while T:\n"
						 + "  i++;\n"
						 + "endwhile";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests 
			
			testScanner.getNext();
			testToken = testScanner.currentToken;
			assertEquals("while", testToken.tokenStr); // check the token
			
			testScanner.getNext();
			testToken = testScanner.currentToken;
			assertEquals("T", testToken.tokenStr); // check the token
			
			testScanner.getNext();
			testToken = testScanner.currentToken;
			assertEquals(":", testToken.tokenStr); // check the token
			
			testScanner.getNext();
			testToken = testScanner.currentToken;
			assertEquals("i", testToken.tokenStr); // check the token
			
			testScanner.jumpToPosition(1, 0);
			
			testToken = testScanner.currentToken;
			assertEquals("while", testToken.tokenStr); // check the token
			testToken = testScanner.nextToken;	
			assertEquals("T", testToken.tokenStr); // check the token
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canReadAcrossNewLines() 
	{
		// Set up the inital 'file' to be read
		String testInput = "x \n y";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests
			
			testScanner.getNext(); // Read a single token should be x
			testToken = testScanner.currentToken; // get the token
			assertEquals("x", testToken.tokenStr); // check the token
			
			
			
			testScanner.getNext(); // Read a single token should be y
			testToken = testScanner.currentToken; // get the token
			assertEquals("y", testToken.tokenStr); // check the token
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canReadAcrossManyNewLines() 
	{
		// Set up the inital 'file' to be read
		String testInput = "x\n"
				         + "y\n"
				         + "Z Int i\n"
				         + "Hello\n"
				         + "Int\n";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests
			
			testScanner.getNext(); // Read a single token should be x
			testToken = testScanner.currentToken; // get the token
			assertEquals("x", testToken.tokenStr); // check the token
			
			
			
			testScanner.getNext(); // Read a single token should be y
			testToken = testScanner.currentToken; // get the token
			assertEquals("y", testToken.tokenStr); // check the token
			
			testScanner.getNext(); // Read a single token should be y
			testToken = testScanner.currentToken; // get the token
			assertEquals("Z", testToken.tokenStr); // check the token

			testScanner.getNext(); // Read a single token should be y
			testToken = testScanner.currentToken; // get the token
			assertEquals("Int", testToken.tokenStr); // check the token
			
			testScanner.getNext(); // Read a single token should be y
			testToken = testScanner.currentToken; // get the token
			assertEquals("i", testToken.tokenStr); // check the token
			
			testScanner.getNext(); // Read a single token should be y
			testToken = testScanner.currentToken; // get the token
			assertEquals("Hello", testToken.tokenStr); // check the token
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canClassifyIntType() 
	{
		// Set up the inital 'file' to be read
		String testInput = "Int x";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests
			
			testScanner.getNext(); // Read a single token should be Int
			testToken = testScanner.currentToken; // get the token
			
			assertEquals("Int", testToken.tokenStr); // check the token
			assertEquals(Token.CONTROL, testToken.primClassif); // check primClassf
			assertEquals(Token.DECLARE, testToken.subClassif); // check subClassf
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canCombineTwoCharacterOperators()
	{
		// Set up the inital 'file' to be read
		String testInput = "<= >= +=";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests
			testScanner.getNext();
			testToken = testScanner.currentToken;
			
			assertEquals("<=", testToken.tokenStr); // check the token
			assertEquals(Token.OPERATOR, testToken.primClassif); // check primClassf
			
			testScanner.getNext();
			testToken = testScanner.currentToken; 
			
			assertEquals(">=", testToken.tokenStr); // check the token
			assertEquals(Token.OPERATOR, testToken.primClassif); // check primClassf
			
			testScanner.getNext();
			testToken = testScanner.currentToken;
			
			assertEquals("+=", testToken.tokenStr); // check the token
			assertEquals(Token.OPERATOR, testToken.primClassif); // check primClassf
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canSkipComments()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int x; // This is a test for skipping comments \n Int y;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests
			for (int i = 0; i < 4; i++) // read 4 tokens
				testScanner.getNext();
			
			testToken = testScanner.currentToken; // get the token, should skipp comments and read Int
			
			assertEquals("Int", testToken.tokenStr); // check the token
			assertEquals(Token.CONTROL, testToken.primClassif); // check primClassf
			assertEquals(Token.DECLARE, testToken.subClassif); // check subClassf
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canClassifyAndOperator()
	{
		// Set up the inital 'file' to be read
		String testInput = "and;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests
			testScanner.getNext();
			
			testToken = testScanner.currentToken;
			
			assertEquals("and", testToken.tokenStr); // check the token
			assertEquals(Token.OPERATOR, testToken.primClassif); // check primClassf
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canClassifyNotinOperator()
	{
		// Set up the inital 'file' to be read
		String testInput = "notin;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests
			testScanner.getNext();
			
			testToken = testScanner.currentToken;
			
			assertEquals("notin", testToken.tokenStr); // check the token
			assertEquals(Token.OPERATOR, testToken.primClassif); // check primClassf
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canClassifyOrOperator()
	{
		// Set up the inital 'file' to be read
		String testInput = "or;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests
			testScanner.getNext();
			
			testToken = testScanner.currentToken;
			
			assertEquals("or", testToken.tokenStr); // check the token
			assertEquals(Token.OPERATOR, testToken.primClassif); // check primClassf
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canClassifyNotOperator()
	{
		// Set up the inital 'file' to be read
		String testInput = "not;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests
			testScanner.getNext();
			
			testToken = testScanner.currentToken;
			
			assertEquals("not", testToken.tokenStr); // check the token
			assertEquals(Token.OPERATOR, testToken.primClassif); // check primClassf
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canClassifyEndif()
	{
		// Set up the inital 'file' to be read
		String testInput = "endif;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests
			testScanner.getNext();
			
			testToken = testScanner.currentToken;
			
			assertEquals("endif", testToken.tokenStr); // check the token
			assertEquals(Token.CONTROL, testToken.primClassif); // check primClassif
			assertEquals(Token.END, testToken.subClassif); // check subClassif
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canClassifyIf()
	{
		// Set up the inital 'file' to be read
		String testInput = "if;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests
			testScanner.getNext();
			
			testToken = testScanner.currentToken;
			
			assertEquals("if", testToken.tokenStr); // check the token
			assertEquals(Token.CONTROL, testToken.primClassif); // check primClassif
			assertEquals(Token.FLOW, testToken.subClassif); // check subClassif
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canClassifyElse()
	{
		// Set up the inital 'file' to be read
		String testInput = "else;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests
			testScanner.getNext();
			
			testToken = testScanner.currentToken;
			
			assertEquals("else", testToken.tokenStr); // check the token
			assertEquals(Token.CONTROL, testToken.primClassif); // check primClassif
			assertEquals(Token.END, testToken.subClassif); // check subClassif
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canClassifyWhile()
	{
		// Set up the inital 'file' to be read
		String testInput = "while;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests
			testScanner.getNext();
			
			testToken = testScanner.currentToken;
			
			assertEquals("while", testToken.tokenStr); // check the token
			assertEquals(Token.CONTROL, testToken.primClassif); // check primClassif
			assertEquals(Token.FLOW, testToken.subClassif); // check subClassif
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canClassifyEndWhile()
	{
		// Set up the inital 'file' to be read
		String testInput = "endwhile;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests
			testScanner.getNext();
			
			testToken = testScanner.currentToken;
			
			assertEquals("endwhile", testToken.tokenStr); // check the token
			assertEquals(Token.CONTROL, testToken.primClassif); // check primClassif
			assertEquals(Token.END, testToken.subClassif); // check subClassif
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canClassifyFor()
	{
		// Set up the inital 'file' to be read
		String testInput = "for;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests
			testScanner.getNext();
			
			testToken = testScanner.currentToken;
			
			assertEquals("for", testToken.tokenStr); // check the token
			assertEquals(Token.CONTROL, testToken.primClassif); // check primClassif
			assertEquals(Token.FLOW, testToken.subClassif); // check subClassif
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canClassifyEndFor()
	{
		// Set up the inital 'file' to be read
		String testInput = "endfor;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests
			testScanner.getNext();
			
			testToken = testScanner.currentToken;
			
			assertEquals("endfor", testToken.tokenStr); // check the token
			assertEquals(Token.CONTROL, testToken.primClassif); // check primClassif
			assertEquals(Token.END, testToken.subClassif); // check subClassif
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canClassifyString()
	{
		// Set up the inital 'file' to be read
		String testInput = "String;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests
			testScanner.getNext();
			
			testToken = testScanner.currentToken;
			
			assertEquals("String", testToken.tokenStr); // check the token
			assertEquals(Token.CONTROL, testToken.primClassif); // check primClassif
			assertEquals(Token.DECLARE, testToken.subClassif); // check subClassif
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canClassifyInt()
	{
		// Set up the inital 'file' to be read
		String testInput = "Int;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests
			testScanner.getNext();
			
			testToken = testScanner.currentToken;
			
			assertEquals("Int", testToken.tokenStr); // check the token
			assertEquals(Token.CONTROL, testToken.primClassif); // check primClassif
			assertEquals(Token.DECLARE, testToken.subClassif); // check subClassif
			
			//***
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canClassifyFloat()
	{
		// Set up the inital 'file' to be read
		String testInput = "Float;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests
			testScanner.getNext();
			
			testToken = testScanner.currentToken;
			
			assertEquals("Float", testToken.tokenStr); // check the token
			assertEquals(Token.CONTROL, testToken.primClassif); // check primClassif
			assertEquals(Token.DECLARE, testToken.subClassif); // check subClassif
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canClassifyBool()
	{
		// Set up the inital 'file' to be read
		String testInput = "Bool;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests 
			testScanner.getNext();
			
			testToken = testScanner.currentToken;
			
			assertEquals("Bool", testToken.tokenStr); // check the token
			assertEquals(Token.CONTROL, testToken.primClassif); // check primClassif
			assertEquals(Token.DECLARE, testToken.subClassif); // check subClassif
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canClassifyBoolConstantT()
	{
		// Set up the inital 'file' to be read
		String testInput = "T;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests 
			testScanner.getNext();
			
			testToken = testScanner.currentToken;
			
			assertEquals("T", testToken.tokenStr); // check the token
			assertEquals(Token.OPERAND, testToken.primClassif); // check primClassif
			assertEquals(Token.BOOLEAN, testToken.subClassif); // check subClassif
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}
	
	@Test
	public void canClassifyBoolConstantF()
	{
		// Set up the inital 'file' to be read
		String testInput = "F;";
		StringReader testReader = new StringReader(testInput);
		BufferedReader br = new BufferedReader(testReader);
		Token testToken;
		
		SymbolTable st = new SymbolTable();
		try {
			
			Scanner testScanner = new Scanner("TEST", br, st);
			// Use this area to run tests 
			testScanner.getNext();
			
			testToken = testScanner.currentToken;
			
			assertEquals("F", testToken.tokenStr); // check the token
			assertEquals(Token.OPERAND, testToken.primClassif); // check primClassif
			assertEquals(Token.BOOLEAN, testToken.subClassif); // check subClassif
			
			//***
		} catch (Exception e) {
			assertTrue("Unable to read input stream", false);
		}
	}

}

