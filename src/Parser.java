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
        int pos = 0;
        tokens = new ArrayList<Token>();
        while(pos < strParts.length) {
    	    if(strParts[pos].equals("=SUM")) {
    	        tokens.add(new Token("SUM", strParts[pos]));
    		}
    		else if(strParts[pos].matches("([A-Z]+)(\\d+)")) {
    		    tokens.add(new Token("CELL_REFERENCE", strParts[pos]));
    		}
    		else if(strParts[pos].matches("[A-Z]+")) {
    		    tokens.add(new Token("STRING", strParts[pos]));
    		}
    		else if(strParts[pos].matches("\\d+")) {
    		    tokens.add(new Token("INTEGER", strParts[pos]));
    		}
    		else if(strParts[pos].matches("\\d+[.]\\d+")) {
    		    tokens.add(new Token("DOUBLE", strParts[pos]));
    		}
    		else
    		    throw new UnsupportedTokenTypeException();
    	    pos++;
        }
    }
	
	public Object expr(int pos) {
	    Object result = null;
	    if(tokens.get(pos).getType().equals("CELL_REFERENCE")) {
	        result = new CellPointer(spreadsheet.get(tokens.get(pos).getValue()));
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
	            ICellContent arg2 = (ICellContent) expr(pos+2);
                result = new CellSumBinary(arg1, arg2);
                tokens.remove(pos+1); tokens.remove(pos+1);
	        }
	        else if(tokens.get(pos+1).getType().equals("STRING")) {
	            result = tokens.get(pos+1).getValue();
	        }
	    }
	    return result;
	}
}
