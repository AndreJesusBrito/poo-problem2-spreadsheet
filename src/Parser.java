
import java.util.ArrayList;
import java.util.List;

public class Parser {

	private final static List<IToken> GENERAL_CONTEXT_LEXICAL = new ArrayList<IToken>();
	private final static List<IToken> GENERAL_CONTEXT_FUNC_LEXICAL = new ArrayList<IToken>();
	    
    static {
    	GENERAL_CONTEXT_LEXICAL.add(new NumberToken());
    	GENERAL_CONTEXT_LEXICAL.add(new PointerToken());
    	
    	GENERAL_CONTEXT_FUNC_LEXICAL.add(new SumFuncToken());
    }

    

    private Spreadsheet spreadsheet;
    private List<String> tokens;
       
    public Parser(Spreadsheet spreadsheet, String[] strParts) throws UnsupportedTokenTypeException {
    	tokens = new ArrayList<String>();
    	for(String token : strParts) {
    		tokens.add(token);
    	}
        this.spreadsheet = spreadsheet;
    }

    public Parser(Spreadsheet spreadsheet, String strLine) throws UnsupportedTokenTypeException {
        this(spreadsheet, strLine.split("\\s+"));
    }
    
    public Spreadsheet getSpreadsheet() {
		return spreadsheet;
    }
    public String getTokenAtPos(int pos) {
    	return tokens.get(pos);
    }
    public List<String> getTokens() {
    	return tokens;
    }
    
    public int increasePos(ICellContent arg, int pos) {
        if(arg instanceof CellBinaryFunction)
            return pos + 2;
        else if(arg instanceof CellUnaryFunction)
            return pos + 1;
        else
            return pos;
    }

    private boolean isFunc(String pattern) {
    	return pattern.charAt(0) == '=';
    }
    
    public ICellContent startParsing() {
    	try {
			return (ICellContent) expr(0, GENERAL_CONTEXT_LEXICAL);
		} catch (UnsupportedTokenTypeException e) {
			System.err.println("Error in parsing!");
			e.printStackTrace();
			System.exit(1);
			return null;
		}
    }
    public ICellContent expr(int pos, List<IToken> contextLexical) throws UnsupportedTokenTypeException {
    	String input = tokens.get(pos);
    	List<IToken> lexical = isFunc(input) ? GENERAL_CONTEXT_FUNC_LEXICAL : contextLexical;
		for(IToken token : lexical) {
    		if(input.matches(token.getParsePattern())) {
    			return token.createToken(pos, this);
            }
    	}
    	throw new UnsupportedTokenTypeException();
    }
}
