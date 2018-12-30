
public final class CellSumBinary extends CellBinaryFunction {
    public CellSumBinary(ICellContent arg1, ICellContent arg2) {
        this.arg1 = arg1;
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
}
