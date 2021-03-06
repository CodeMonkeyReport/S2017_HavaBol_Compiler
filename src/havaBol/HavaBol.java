/*
  This is a simple driver for the first programming assignment.
  Command Arguments:
      java HavaBol arg1
             arg1 is the havabol source file name.
  Output:
      Prints each token in a table.
  Notes:
      1. This creates a SymbolTable object which doesn't do anything
         for this first programming assignment.
      2. This uses the student's Scanner class to get each token from
         the input file.  It uses the getNext method until it returns
         an empty string.
      3. If the Scanner raises an exception, this driver prints 
         information about the exception and terminates.
      4. The token is printed using the Token::printToken() method.
 */
package havaBol;

import java.io.BufferedReader;
import java.io.FileReader;

public class HavaBol {
	public static void main(String[] args) {
		// Create the SymbolTable
		SymbolTable symbolTable = new SymbolTable();
		try {
			ResultValue res;
			// Used to abstract out the file reader
			FileReader fr = new FileReader(args[0]);
			BufferedReader br = new BufferedReader(fr);
			Scanner scan = new Scanner(args[0], br, symbolTable);
			StorageManager sm = new StorageManager();
			Parser parser = new Parser(scan, sm);
			res = parser.statements(true);
			
			if(res != null)
			{
				if (res.terminatingStr.equals("return"))
				throw new ParserException(scan.currentToken.iSourceLineNr,
						"return statment outside of function definition", scan.sourceFileName);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}