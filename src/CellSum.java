
public class CellSum implements ICellContent {
	private ICellContent[] args;
	private boolean isDouble;
	
	public CellSum(ICellContent[] args) {
		setArgs(args);
		boolean isDouble = true;
		for(ICellContent arg : args) {
			isDouble = isDouble || arg.isDouble();
		}
		setDouble(isDouble);
	}
	
	@Override
	public Number getValue() {
		double value = 0.0;
		for(ICellContent arg : args) {
			value += (Double) arg.getValue();
		}
    	if(isDouble)
    		return value;
    	else
    		return (int) value;
	}

	@Override
	public String getContent() {
		String result = "";
		for(ICellContent arg : args)
			result += arg.getContent() + " ";
		result = result.trim();
		return result;
	}

	@Override
	public void onDelete() {
		for(ICellContent arg : args) {
			arg.onDelete();
		}
	}
	
	public void setArgs(ICellContent[] args) {
		this.args = args;
	}

	public ICellContent[] getArgs() {
		return args;
	}
	
	@Override
	public boolean isDouble() {
		return isDouble;
	}
	
	public void setDouble(boolean isDouble) {
		this.isDouble = isDouble;
	}
}
