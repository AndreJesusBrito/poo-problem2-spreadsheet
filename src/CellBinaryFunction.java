
public abstract class CellBinaryFunction extends CellFunction {
	protected ICellContent arg1;
    protected ICellContent arg2;
    
    public ICellContent getArg1() {
        return arg1;
    }

    public void setArg1(ICellContent arg1) {
        this.arg1 = arg1;
    }

    public ICellContent getArg2() {
        return arg2;
    }

    public void setArg2(ICellContent arg2) {
        this.arg2 = arg2;
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
}
