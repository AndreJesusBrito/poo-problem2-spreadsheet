
public abstract class CellBinaryFunction extends CellFunction {
	protected ICellContent arg1;
    protected ICellContent arg2;
    protected boolean isDouble;
    
    public ICellContent getArg1() {
        return arg1;
    }

    public void setArg1(ICellContent arg1) {
        this.arg1 = arg1;
        setDouble();
    }

    public ICellContent getArg2() {
        return arg2;
    }

    public void setArg2(ICellContent arg2) {
        this.arg2 = arg2;
        setDouble();
    }

    @Override
    public String getContent() {
        String result = super.getContent();
        result += arg1.getContent() + " ";
        result += arg2.getContent();
        return result;
    }

    @Override
    public void onDelete() {
        arg1.onDelete();
        arg2.onDelete();
    }

    @Override
    public boolean isDouble() {
        return isDouble;
    }

    public void setDouble() {
        this.isDouble = arg1.isDouble() || arg2.isDouble();
    }
}
