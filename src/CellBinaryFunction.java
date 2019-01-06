
public abstract class CellBinaryFunction extends CellFunction {
    protected ICellContent arg1;
    protected ICellContent arg2;

    public ICellContent getArg1() {
        return arg1;
    }

    public void setArg1(ICellContent arg1) throws CellFunctionInvalidArgException {
        this.arg1 = arg1;
    }

    public ICellContent getArg2() {
        return arg2;
    }

    public void setArg2(ICellContent arg2) throws CellFunctionInvalidArgException {
        this.arg2 = arg2;
    }

    @Override
    public String getContentString() {
        String result = super.getContentString();
        result += arg1.getContentString() + " ";
        result += arg2.getContentString();
        return result;
    }

    @Override
    public void onDelete() {
        arg1.onDelete();
        arg2.onDelete();
    }
}
