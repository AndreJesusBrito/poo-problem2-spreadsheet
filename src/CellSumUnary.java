

public class CellSumUnary extends CellUnaryFunction {
    public CellSumUnary(Spreadsheet spreadsheet, ICellContent line) throws CellFunctionInvalidArgException {
        setArg1(line);
    }

    @Override
    public void setArg1(ICellContent content) throws CellFunctionInvalidArgException {
    	if(content instanceof CellCollection)
    		arg1 = content;
    	else
    		throw new CellFunctionInvalidArgException();
    }

    @Override
    public Object getValue() {
	    Double result = 0.0;
	
	    boolean isDouble = false;
	    for(ICellContent cellContent : (CellCollection) arg1) {
	    	Number num = (Number) cellContent.getValue();
	    	if(num instanceof Integer) {
	    		result += num.intValue();
	    	} else {
	    		result += num.doubleValue();
	    		isDouble = true;
	    	}
	    }
	    return isDouble ? result : result.intValue();
	}
    
	@Override
	public String getFuncName() {
		return "SUM";
	}
}
