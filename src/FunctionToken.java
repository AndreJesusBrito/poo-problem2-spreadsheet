
import java.util.List;

public abstract class FunctionToken implements IToken {
    /** logic used to select the right according to the input
     *  @param pos index on the tokens arrayList in the parser, the index of the token to be evaluated
     *  @param parser The instance of the parser that called the FunctionToken
     *  @return the function as an ICellContent if possible, otherwise throws an UnsupportedTokenTypeException
     */
    @Override
    public ICellContent createCellContent(int pos, Parser parser) throws UnsupportedTokenTypeException {
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
                pos = increasePos(arg1, pos);
                ICellContent arg2 = parser.expr(pos+2, getBinaryArg2Context());
                pos = increasePos(arg2, pos);
                return createBinaryFunc(arg1, arg2);
            }
        } catch(CellFunctionInvalidArgException e) {
            System.err.println("Invalid argument for function");
            e.getStackTrace();
        }
        throw new UnsupportedTokenTypeException();
    }

    /** Increases the position in the String List tokens.
     * Checks if the arg parameter is a CellBinaryFunction or CellUnaryFunction,
     * and increases the position accordingly.
     * @param arg ICellContent arg to be tested
     * @param pos position to be increased
     * @return increased position int
     */
    public int increasePos(ICellContent arg, int pos) {
        if(arg instanceof CellBinaryFunction)
            return pos + 2;
        else if(arg instanceof CellUnaryFunction)
            return pos + 1;
        else
            return pos;
    }

    @Override
    public String getParsePattern() {
        return "^=" + getFuncName() + "$";
    }

    /** Must be implemented to be used to match when parsing
     * @return the function name
     */
    protected abstract String getFuncName();


    /** @return must return if the function supports a Unary form */
    public abstract boolean supportsUnary();

    /** @return must return if the function supports a Binary form */
    public abstract boolean supportsBinary();


    /**
     * @param arg argument to pass to the function
     * @return must return the instance of the unary function matched
     */
    public abstract ICellContent createUnaryFunc(ICellContent arg) throws CellFunctionInvalidArgException;

    /**
     *  @param arg1  first argument to pass to the function
     *  @param arg2  secound argument to pass to the function
     *  @return must return the instance of the binary function matched
     */
    public abstract ICellContent createBinaryFunc(ICellContent arg1, ICellContent arg2) throws CellFunctionInvalidArgException;



    /**
     *  @return must return a list with all compatible argument type ITokens
     */
    public abstract List<IToken> getUnaryArgContext();


    /**
     *  @return must return a list with all ITokens for all compatible types for the first argument
     */
    public abstract List<IToken> getBinaryArg1Context();

    /**
     *  @return must return a list with all ITokens for all compatible types for the secound argument
     */
    public abstract List<IToken> getBinaryArg2Context();


    /** Checks if a function that is no argument of a function and has no function arguments can be a unary function
     *  This method supports the canBeUnary method
     *  @param pos current position in the String List tokens
     *  @param tokens the String List to cycle through
     *  @return <code>true</code> if the function being tested is unary, <code>false</code> otherwise
     */
    protected boolean isUnaryWithOneFunc(int pos, List<String> tokens) {
        if(pos+2 >= tokens.size()) return true;
        return false;
    }

    /** Checks if a function can be unary
     *  @param pos current position in the String List tokens
     *  @param tokens the String List to cycle through
     *  @return <code>true</code> if the function being tested is unary, <code>false</code> otherwise
     */
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
