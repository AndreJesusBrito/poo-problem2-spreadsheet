public class CellSumBinary implements ICellContent {
    private ICellContent arg1;
    private ICellContent arg2;
    private boolean isDouble;

    public CellSumBinary(ICellContent arg1, ICellContent arg2) {
        setArg1(arg1);
        setArg2(arg2);
        setDouble();
    }

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
    public Number getValue() {
    double value = ((Number) arg1.getValue()).doubleValue() + ((Number) arg2.getValue()).doubleValue();
        if(isDouble)
            return value;
        else
            return (int) value;
    }

    @Override
    public String getContent() {
        String result = "=SUM ";
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
