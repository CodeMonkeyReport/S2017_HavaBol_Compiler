package havaBol;

import java.util.HashMap;
import java.util.ArrayList;

public class SymbolTable implements Cloneable {

	static final int VAR_ARGS = -1;

	// global symbol table
	HashMap<String, STEntry> globalST = new HashMap<String, STEntry>();

	// symbol tables for functions
	ArrayList<HashMap<String, STEntry>> funcST = new ArrayList<HashMap<String, STEntry>>();

	public SymbolTable() {
		initGlobal();
	}

	// Takes a string and returns the symbol's entry information
	public STEntry getSymbol(String symbol) {
		return globalST.get(symbol);
	}

	// stores the symbol and its entry in the symbol table
	public void putSymbol(String symbol, STEntry entry) {
		globalST.put(symbol, entry);
	}

	// stores all reserved symbols into the global symbol table
	private void initGlobal() {
		globalST.put("if", new STControl("if", Token.FLOW, 0));
		globalST.put("for", new STControl("for", Token.FLOW, 0));
		globalST.put("while", new STControl("while", Token.FLOW, 0));
		globalST.put("return", new STControl("return", Token.FLOW, 0));
		globalST.put("Ref", new STControl("Ref", Token.FLOW, 0));

		globalST.put("endif", new STControl("endif", Token.END, 0));
		globalST.put("endfunc", new STControl("endfunc", Token.END, 0));
		globalST.put("else", new STControl("else", Token.END, 0));
		globalST.put("endfor", new STControl("endfor", Token.END, 0));
		globalST.put("endwhile", new STControl("endwhile", Token.END, 0));
		globalST.put("endtuple", new STControl("endtuple", Token.END, 0));
		globalST.put("return", new STControl("return", Token.END, 0));

		globalST.put("Int", new STControl("Int", Token.DECLARE, 0));
		globalST.put("Float", new STControl("Float", Token.DECLARE, 0));
		globalST.put("String", new STControl("String", Token.DECLARE, 0));
		globalST.put("Bool", new STControl("Bool", Token.DECLARE, 0));
		globalST.put("Date", new STControl("Date", Token.DECLARE, 0));
		globalST.put("Void", new STControl("Void", Token.DECLARE, 0));

		globalST.put("tuple", new STControl("tuple", Token.DEFINE, 0));
		globalST.put("func", new STControl("func", Token.DEFINE, 0));

		globalST.put("print", new STFunction("print", Type.VOID, Token.BUILTIN, VAR_ARGS, 0));

		globalST.put("LENGTH", new STFunction("LENGTH", Type.INT, Token.BUILTIN, 1, 0));
		globalST.put("MAXLENGTH", new STFunction("MAXLENGTH", Type.INT, Token.BUILTIN, 1, 0));
		globalST.put("SPACES", new STFunction("SPACES", Type.INT, Token.BUILTIN, 1, 0));
		globalST.put("ELEM", new STFunction("ELEM", Type.INT, Token.BUILTIN, 1, 0));
		globalST.put("MAXELEM", new STFunction("MAXELEM", Type.INT, Token.BUILTIN, 1, 0));
		globalST.put("dateDiff", new STFunction("dateDiff", Type.INT, Token.BUILTIN, 1, 0));
		globalST.put("dateAdj", new STFunction("dateAdj", Type.INT, Token.BUILTIN, 1, 0));
		globalST.put("dateAge", new STFunction("dateAge", Type.INT, Token.BUILTIN, 1, 0));

		globalST.put("and", new STEntry("and", Token.OPERATOR, 0, 0));
		globalST.put("or", new STEntry("or", Token.OPERATOR, 0, 0));
		globalST.put("not", new STEntry("not", Token.OPERATOR, 0, 0));
		globalST.put("in", new STEntry("in", Token.OPERATOR, 0, 0));
		globalST.put("notin", new STEntry("notin", Token.OPERATOR, 0, 0));
		globalST.put("by", new STEntry("by", Token.OPERATOR, 0, 0));
		globalST.put("in", new STEntry("for", Token.OPERATOR, 0, 0));
		globalST.put("to", new STEntry("to", Token.OPERATOR, 0, 0));
		globalST.put("from", new STEntry("from", Token.OPERATOR, 0, 0));

		globalST.put("T", new STEntry("T", Token.OPERAND, Token.BOOLEAN, 0));
		globalST.put("F", new STEntry("F", Token.OPERAND, Token.BOOLEAN, 0));

	}

	public SymbolTable Clone() {
		SymbolTable newTable = new SymbolTable();
		newTable.globalST = (HashMap<String, STEntry>) this.globalST.clone();
		return newTable;
	}

}
