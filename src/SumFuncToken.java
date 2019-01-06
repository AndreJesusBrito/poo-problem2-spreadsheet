
import java.util.ArrayList;
import java.util.List;

public class SumFuncToken extends FunctionToken {
    private static List<IToken> UNI_ARG_LEXICAL = new ArrayList<IToken>();
    private static List<IToken> BIN_ARG_LEXICAL = new ArrayList<IToken>();
    static {
        UNI_ARG_LEXICAL.add(new RowPointerToken());
        UNI_ARG_LEXICAL.add(new ColumnPointerToken());

        BIN_ARG_LEXICAL.add(new PointerToken());
        BIN_ARG_LEXICAL.add(new NumberToken());
    }

    @Override
    public boolean supportsUnary() {
        return true;
    }
    @Override
    public boolean supportsBinary() {
        return true;
    }

    @Override
    public List<IToken> getUnaryArgContext() {
        return UNI_ARG_LEXICAL;
    }

    @Override
    public List<IToken> getBinaryArg1Context() {
        return BIN_ARG_LEXICAL;
    }

    @Override
    public List<IToken> getBinaryArg2Context() {
        return BIN_ARG_LEXICAL;
    }
    @Override
    public ICellContent createUnaryFunc(ICellContent arg) throws CellFunctionInvalidArgException {
        return new CellSumUnary(arg);
    }
    @Override
    public ICellContent createBinaryFunc(ICellContent arg1, ICellContent arg2) throws CellFunctionInvalidArgException {
        return new CellSumBinary(arg1, arg2);
    }
    @Override
    protected String getFuncName() {
        return "SUM";
    }
}
