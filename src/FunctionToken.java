
import java.util.List;

public abstract class FunctionToken implements IToken {	
	@Override
	public ICellContent createToken(int pos, Parser parser) throws UnsupportedTokenTypeException {
		try {
			if(supportsUnary() && canBeUnary(pos, parser.getTokens())) {
				try {
					ICellContent arg = parser.expr(pos+1, getUnaryArgContext());
		            pos++;
		            return createUnaryFunc(arg);
				} catch(UnsupportedTokenTypeException e) {}
	    	}
			if(supportsBinary()) {
				ICellContent arg1 = parser.expr(pos+1, getBinaryArg1Context());
			    pos = parser.increasePos(arg1, pos);
			    ICellContent arg2 = parser.expr(pos+2, getBinaryArg2Context());
			    pos = parser.increasePos(arg2, pos);
			    return createBinaryFunc(arg1, arg2);
			}
		} catch(CellFunctionInvalidArgException e) {
			System.err.println("Invalid argument for function");
			e.getStackTrace();
		}
		throw new UnsupportedTokenTypeException();
	}

	
	@Override
	public String getParsePattern() {
		return "^=" + getFuncName() + "$";
	}


	protected abstract String getFuncName();

	public abstract boolean supportsUnary();
	public abstract boolean supportsBinary();

	public abstract ICellContent createUnaryFunc(ICellContent arg) throws CellFunctionInvalidArgException;
	public abstract ICellContent createBinaryFunc(ICellContent arg1, ICellContent arg2) throws CellFunctionInvalidArgException;
	
	
	public abstract List<IToken> getUnaryArgContext();
	
	public abstract List<IToken> getBinaryArg1Context();
	public abstract List<IToken> getBinaryArg2Context();
	
	protected boolean isUnaryWithOneFunc(int pos, List<String> tokens) {
        if(pos+2 >= tokens.size()) return true;
        return false;
    }

    protected boolean canBeUnary(int pos, List<String> tokens) {
        try {
            if(tokens.get(pos-1).matches(getParsePattern())) {
                try {
                    if(tokens.get(pos+2).matches(getParsePattern())) {
                        return true;
                    }
                    tokens.get(pos+3);
                    return false;
                } catch(IndexOutOfBoundsException e1) {
                    return true;
                }
            }
            else {
                return isUnaryWithOneFunc(pos, tokens);
            }
        } catch(IndexOutOfBoundsException e2) {
            return isUnaryWithOneFunc(pos, tokens);
        }
    }
}
