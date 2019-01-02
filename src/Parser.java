
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

	// static methods and properties ------------------
	private final static String PACKAGE_PATH = "UnitTest_Dep.";
	
    private final static List<String> TOKENS = new ArrayList<String>();
    private final static Map<String, Class<?>> UNI_FUNC = new HashMap<String, Class<?>>();
    private final static Map<String, Class<?>> BIN_FUNC = new HashMap<String, Class<?>>();
    
    private final static void registerFunc(Map<String, Class<?>> register ,String funcName, String funcClass) {
    	try {
			register.put(funcName, Class.forName(PACKAGE_PATH + funcClass));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
    }
    
    static {
    	TOKENS.add("ool");
    	
		registerFunc(UNI_FUNC, "SUM", "CellSumUnary");
		
		registerFunc(BIN_FUNC, "SUM", "CellSumBinary");
    }
    
    private static final Object newUniFunc(String funcName, ICellContent arg1, Spreadsheet ss) {
    	Class<?>[] type = { Spreadsheet.class, ICellContent.class };
        Object[] obj = { ss, arg1 };
		try {
			return Parser.UNI_FUNC.get(funcName).getConstructor(type).newInstance(obj);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
    }    
    
    private static final Object newBinFunc(String funcName, ICellContent arg1, ICellContent arg2) {
    	Class<?>[] type = { ICellContent.class, ICellContent.class };
        Object[] obj = {arg1, arg2};
		try {
			return Parser.BIN_FUNC.get(funcName).getConstructor(type).newInstance(obj);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
    }    
    
    // -------------------------------------------
    
    
        
    Spreadsheet spreadsheet;
    List<Token> tokens;
    
    public Parser(Spreadsheet spreadsheet, String[] strParts) throws UnsupportedTokenTypeException {
    	tokens = new ArrayList<Token>();
    	parseTokens(strParts);
        this.spreadsheet = spreadsheet;
    }

    public Parser(Spreadsheet spreadsheet, String strLine) throws UnsupportedTokenTypeException {
        this(spreadsheet, strLine.split("\\s+"));
    }

    public void parseTokens(String[] strParts) throws UnsupportedTokenTypeException {
        for(int pos = 0; pos < strParts.length; pos++) {
            tokens.add(getMatchedToken(strParts[pos]));
        }
    }

    private Token getMatchedToken(String str) throws UnsupportedTokenTypeException {
        if(str.charAt(0) == '=') {
           return new Token(str.substring(1), str);
        }
        else {
        	if(str.matches("([A-Z]+)(\\d+)")) {
	           return new Token("CELL_REFERENCE", str);
	        }
	        else if(str.matches("[A-Z]+")) {
	           return new Token("STRING", str);
	        }
	        else if(str.matches("\\d+")) {
	           return new Token("INTEGER", str);
	        }
	        else if(str.matches("\\d+[.]\\d*")) {
	           return new Token("DOUBLE", str);
	        }
	        else
	           throw new UnsupportedTokenTypeException();
    	}
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
        if(arg instanceof CellBinaryFunction)
            return pos + 2;
        else if(arg instanceof CellUnaryFunction)
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
            result = new CellNumber<Integer>(Integer.parseInt(tokens.get(pos).getValue()));
        }
        else if(tokens.get(pos).getType().equals("DOUBLE")) {
            result = new CellNumber<Double>(Double.parseDouble(tokens.get(pos).getValue()));
        }
        else if(tokens.get(pos).getType().equals("SUM")) {
            if(tokens.get(pos+1).getType().equals("CELL_REFERENCE")
            || tokens.get(pos+1).getType().equals("DOUBLE")
            || tokens.get(pos+1).getType().equals("SUM")) {
                ICellContent arg1 = (ICellContent) expr(pos+1);
                pos = increasePos(arg1, pos);
                ICellContent arg2 = (ICellContent) expr(pos+2);
                pos = increasePos(arg2, pos);
//                result = new CellSumBinary(arg1, arg2);
                result = Parser.newBinFunc("SUM", arg1, arg2);
            }
            else if(tokens.get(pos+1).getType().equals("STRING")) {
                String line = (String) expr(pos+1);
                ICellContent arg1 = new CellColumnPointer(spreadsheet, line);
                pos++;
//                result = new CellSumUnary(spreadsheet, new CellRow(spreadsheet, line));
                result = Parser.newUniFunc("SUM", arg1, spreadsheet);
            }
            else if(tokens.get(pos+1).getType().equals("INTEGER")) {
                if(isUnary(pos)) {
                    String line = ((ICellContent) expr(pos+1)).getValue().toString();
                    ICellContent arg1 = new CellRowPointer(spreadsheet, line);
                    pos++;
//                    result = new CellSumUnary(spreadsheet, new CellString(line));
                    result = Parser.newUniFunc("SUM", arg1, spreadsheet);
                }
                else {
                    ICellContent arg1 = (ICellContent) expr(pos+1);
                    pos = increasePos(arg1, pos);
                    ICellContent arg2 = (ICellContent) expr(pos+2);
                    pos = increasePos(arg2, pos);
//                    result = new CellSumBinary(arg1, arg2);
                    result = Parser.newBinFunc("SUM", arg1, arg2);
                }
            }
        }
        return result;
    }
}
