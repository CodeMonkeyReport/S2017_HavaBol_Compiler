package havaBol;

import java.util.HashMap;
import java.util.ArrayList;



public class SymbolTable {
	
	static final int VAR_ARGS = -1;
	
	//global symbol table
	HashMap <String, STEntry> globalST = new HashMap<String, STEntry>();
	
	//symbol tables for functions
	ArrayList <HashMap <String, STEntry> > funcST = new ArrayList< HashMap <String, STEntry> >();
	
	public SymbolTable()
	{
		initGlobal();
	}
	
	
	//Takes a string and returns the symbol's entry information
	public STEntry getSymbol(String symbol)
	{
		return globalST.get(symbol);
	}
	
	//stores the symbol and its entry in the symbol table
	public void putSymbol(String symbol, STEntry entry)
	{
		globalST.put(symbol, entry);
	}
	
	//stores all reserved symbols into the global symbol table
	private void initGlobal()
	{
		globalST.put("def", new STControl("def", Token.FLOW));
		globalST.put("if", new STControl("if",Token.FLOW));
	    globalST.put("for", new STControl("for",Token.FLOW));
	    globalST.put("while", new STControl("while",Token.FLOW));
	    
	    globalST.put("endif", new STControl("endif",Token.END));
	    globalST.put("enddef", new STControl("enddef",Token.END));
	    globalST.put("else", new STControl("else",Token.END));
	    globalST.put("endfor", new STControl("endfor",Token.END));
	    globalST.put("endwhile", new STControl("endwhile",Token.END));
	    globalST.put("endtuple", new STControl("endwhile",Token.END));
	    
	    globalST.put("Int", new STControl("Int",Token.DECLARE));
	    globalST.put("Float", new STControl("Float",Token.DECLARE));
	    globalST.put("String", new STControl("String",Token.DECLARE));
	    globalST.put("Bool", new STControl("Bool",Token.DECLARE));
	    globalST.put("Date", new STControl("Date",Token.DECLARE));
	    
	    globalST.put("tuple", new STControl("tuple", Token.DEFINE));
	    
	    
	    globalST.put("print", new STFunction("print",Token.VOID,Token.BUILTIN, VAR_ARGS));
	    
	    globalST.put("LENGTH", new STFunction("LENGTH", Token.INTEGER, Token.BUILTIN, 1));
	    globalST.put("MAXLENGTH", new STFunction("MAXLENGTH", Token.INTEGER, Token.BUILTIN, 1));
	    globalST.put("SPACES", new STFunction("SPACES", Token.INTEGER, Token.BUILTIN, 1));
	    globalST.put("ELEM", new STFunction("ELEM", Token.INTEGER, Token.BUILTIN, 1));
	    globalST.put("MAXELEM", new STFunction("MAXELEM", Token.INTEGER, Token.BUILTIN, 1));
	    
	    globalST.put("and", new STEntry("and", Token.OPERATOR, 0));
	    globalST.put("or", new STEntry("or", Token.OPERATOR, 0));
	    globalST.put("not", new STEntry("not", Token.OPERATOR, 0));
	    globalST.put("in", new STEntry("in", Token.OPERATOR, 0));
	    globalST.put("notin", new STEntry("notin", Token.OPERATOR, 0));
        globalST.put("by", new STEntry("by", Token.OPERATOR, 0));
        globalST.put("in", new STEntry("for", Token.OPERATOR, 0));
        globalST.put("to", new STEntry("to", Token.OPERATOR, 0));
        globalST.put("from", new STEntry("from", Token.OPERATOR, 0));


		globalST.put("T", new STEntry("T", Token.OPERAND, Token.BOOLEAN));
	    globalST.put("F", new STEntry("F", Token.OPERAND, Token.BOOLEAN));



	}

}
