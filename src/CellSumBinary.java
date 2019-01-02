
public final class CellSumBinary extends CellBinaryFunction {
    public CellSumBinary(ICellContent arg1, ICellContent arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }
    
    @Override
    public Number getValue() {
    	Number a = (Number) arg1.getValue() , b = (Number) arg2.getValue();
    	if(a instanceof Double || b instanceof Double) {
    		return a.doubleValue() + b.doubleValue();
    	} else {
    		return a.intValue() + b.intValue();
    	}
    }

	@Override
	public String getFuncName() {
		return "SUM";
	}
}
