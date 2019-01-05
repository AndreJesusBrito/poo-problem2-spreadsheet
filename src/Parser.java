
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

<<<<<<< HEAD
    private boolean isUnaryWithOneSum(int pos) {
        try {
            tokens.get(pos+2);
            return false;
        } catch(IndexOutOfBoundsException e) {
            return true;
        }
    }
    
    private boolean isUnary(int pos) {
        try {
            if(tokens.get(pos-1).getType().equals("SUM")) {
                try {
                    if(tokens.get(pos+2).getType().equals("SUM")) {
                        return true;
                    }
                    tokens.get(pos+3);
                    return false;
                } catch(IndexOutOfBoundsException e1) {
                    return true;
                }
            }
            else {
                return isUnaryWithOneSum(pos);
            }
        } catch(IndexOutOfBoundsException e2) {
            return isUnaryWithOneSum(pos);
        }
    }
    
    private int increasePos(ICellContent arg, int pos) {
        if(arg instanceof CellSumBinary)
            return pos + 2;
        else if(arg instanceof CellSumUnary)
=======
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
>>>>>>> alt
            return pos + 1;
        else
            return pos;
    }
<<<<<<< HEAD
    
	public Object expr(int pos) {
	    Object result = null;
	    if(tokens.get(pos).getType().equals("CELL_REFERENCE")) {
	        result = new CellPointer(spreadsheet.get(tokens.get(pos).getValue()));
	    }
	    else if(tokens.get(pos).getType().equals("STRING")) {
	        result = new String(tokens.get(pos).getValue());
	    }
	    else if(tokens.get(pos).getType().equals("INTEGER")) {
	        result = new CellNumber(Integer.parseInt(tokens.get(pos).getValue()), false);
	    }
	    else if(tokens.get(pos).getType().equals("DOUBLE")) {
	        result = new CellNumber(Double.parseDouble(tokens.get(pos).getValue()), true);
	    }
	    else if(tokens.get(pos).getType().equals("SUM")) {
	        if(tokens.get(pos+1).getType().equals("CELL_REFERENCE") || 
	           tokens.get(pos+1).getType().equals("DOUBLE") ||
	           tokens.get(pos+1).getType().equals("SUM")) {
	            ICellContent arg1 = (ICellContent) expr(pos+1);
	            pos = increasePos(arg1, pos);
	            ICellContent arg2 = (ICellContent) expr(pos+2);
	            pos = increasePos(arg2, pos);
                result = new CellSumBinary(arg1, arg2);
	        }
	        else if(tokens.get(pos+1).getType().equals("STRING")) {
	            String line = (String) expr(pos+1);
	            pos++;
	            result = new CellSumUnary(spreadsheet, line);
	        }
	        else if(tokens.get(pos+1).getType().equals("INTEGER")) {
	            if(!isUnary(pos)) {
	                ICellContent arg1 = (ICellContent) expr(pos+1);
	                pos = increasePos(arg1, pos);
	                ICellContent arg2 = (ICellContent) expr(pos+2);
	                pos = increasePos(arg2, pos);
	                result = new CellSumBinary(arg1, arg2);
	            }
	            else {
	                String line = ((ICellContent) expr(pos+1)).getValue().toString();
	                pos++;
	                result = new CellSumUnary(spreadsheet, line);
	            }
	        }
	    }
	    return result;
	}
=======

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
>>>>>>> alt
}
