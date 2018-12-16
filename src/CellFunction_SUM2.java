public class CellFunction_SUM2 extends CellFunction {
    private Object arg1;
    private Object arg2;
    
    public CellFunction_SUM2(String content) {
		super(content);
	}
    
    public Number getValue() {
    	Number result;
    	if(arg1 instanceof Integer && arg2 instanceof Integer)
    		result = ((Number) arg1).intValue() + ((Number) arg2).intValue();
    	else
    		result = ((Number) arg1).doubleValue() + ((Number) arg2).doubleValue();
    	return result;
    }
}
