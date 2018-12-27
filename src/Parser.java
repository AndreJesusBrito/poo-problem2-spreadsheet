import java.util.ArrayList;
import java.util.List;

public class Parser {
    Spreadsheet spreadsheet;
	List<Token> tokens;
	
	public Parser(Spreadsheet spreadsheet, String[] strParts) throws UnsupportedTokenTypeException {
		parseTokens(strParts);
		this.spreadsheet = spreadsheet;
	}
	
	public Parser(Spreadsheet spreadsheet, String strLine) throws UnsupportedTokenTypeException {
		this(spreadsheet, strLine.split("\\s+"));
	}

    public void parseTokens(String[] strParts) throws UnsupportedTokenTypeException {
        tokens = new ArrayList<Token>();
        for(int pos = 0; pos < strParts.length; pos++) {
    	    tokens.add(getMatchedToken(strParts[pos]));
        }
    }

    private Token getMatchedToken(String str) throws UnsupportedTokenTypeException {
        if(str.equals("=SUM")) {
           return new Token("SUM", str);
        }
        else if(str.matches("([A-Z]+)(\\d+)")) {
           return new Token("CELL_REFERENCE", str);
        }
        else if(str.matches("[A-Z]+")) {
           return new Token("STRING", str);
        }
        else if(str.matches("\\d+")) {
           return new Token("INTEGER", str);
        }
        else if(str.matches("\\d+[.]\\d+")) {
           return new Token("DOUBLE", str);
        }
        else
           throw new UnsupportedTokenTypeException();
    }
    
    private boolean isUnaryWithOneSum(int pos) {
        if(pos+2 >= tokens.size()) return true;
        tokens.get(pos+2);
        return false;
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
            return pos + 1;
        else
            return pos;
    }
    
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
	            if(isUnary(pos)) {
	            	String line = ((ICellContent) expr(pos+1)).getValue().toString();
	                pos++;
	                result = new CellSumUnary(spreadsheet, line);
	            }
	            else {
	            	ICellContent arg1 = (ICellContent) expr(pos+1);
	                pos = increasePos(arg1, pos);
	                ICellContent arg2 = (ICellContent) expr(pos+2);
	                pos = increasePos(arg2, pos);
	                result = new CellSumBinary(arg1, arg2);
	            }
	        }
	    }
	    return result;
	}
}
