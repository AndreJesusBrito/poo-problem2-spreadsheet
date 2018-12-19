import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Parser {
    Spreadsheet spreadsheet;
	String[] strParts;
	int pos;
	Token currentToken;
	
	public Parser(Spreadsheet spreadsheed, String[] strParts) {
		setStrParts(strParts);
		setPos(0);
		setCurrentToken(null);
	}
	
	public Parser(Spreadsheet spreadsheet, String strLine) {
		this(spreadsheet, strLine.split("\\s+"));
	}
	
	public String[] getStrParts() {
        return strParts;
    }

    public void setStrParts(String[] strParts) {
        this.strParts = strParts;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
    
    public Token getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(Token currentToken) {
        this.currentToken = currentToken;
    }

    public Token getNextToken() throws UnsupportedTokenTypeException {
	    Token currentToken = null;
	    if(pos > strParts.length - 1)
	        currentToken = new Token("EOF", null);
	    else if(strParts[pos].equals("=SUM")) {
			currentToken = new Token("SUM", strParts[pos]);
			pos++;
		}
		else if(strParts[pos].equals("([A-Z]+)(\\d+)")) {
			currentToken = new Token("CELL_REFERENCE", strParts[pos]);
			pos++;
		}
		else if(strParts[pos].equals("(A-Z)+")) {
		    currentToken = new Token("STRING", strParts[pos]);
		    pos++;
		}
		else if(strParts[pos].equals("\\d+")) {
		    currentToken = new Token("INTEGER", strParts[pos]);
		    pos++;
		}
		else if(strParts[pos].equals("\\d+[.]\\d+")) {
		    currentToken = new Token("DOUBLE", strParts[pos]);
		    pos++;
		}
		else
		    throw new UnsupportedTokenTypeException();
		return currentToken;	
    }
    
    public List<ICellContent> findArgs() throws UnsupportedTokenTypeException {
        List<ICellContent> result = new ArrayList<ICellContent>();
        setCurrentToken(getNextToken());
        if(currentToken.getType().equals("CELL_REFERENCE")) {
            result.add(new CellPointer(spreadsheet.get(currentToken.getValue())));
            result.addAll(findArgs());
        }
        else if(currentToken.getType().equals("STRING")) {
            Set<String> keySet = spreadsheet.getCol(currentToken.getType());
            for(String key : keySet) {
//                resultList.add(spreadsheet.get(key));
            }
        }
        else if(currentToken.getType().equals("INTEGER")) {

        }
        else if(currentToken.getType().equals("DOUBLE")) {
            result.add(new CellNumber(Double.parseDouble(currentToken.getValue()), true));
            result.addAll(findArgs());
        }
        else if(currentToken.getType().equals("SUM")) {
            result.add(new CellSum(findArgs().toArray(new ICellContent[0])));
        }
        return result;
    }
	
	public ICellContent expr() throws UnsupportedTokenTypeException {
	    ICellContent result = null;
	    if(currentToken.getType().equals("CELL_REFERENCE")) {
	        result = new CellPointer(spreadsheet.get(currentToken.getValue()));
	    }
	    else if(currentToken.getType().equals("INTEGER")) {
	        result = new CellNumber(Integer.parseInt(currentToken.getValue()), false);
	    }
	    else if(currentToken.getType().equals("DOUBLE")) {
	        result = new CellNumber(Double.parseDouble(currentToken.getValue()), true);
	    }
	    else if(currentToken.getType().equals("SUM")) {
	        result = new CellSum(findArgs().toArray(new ICellContent[0]));
	    }
	    return result;
	}
}
