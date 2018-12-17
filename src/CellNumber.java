public class CellNumber implements ICellContent {
    private double value;
    private boolean isDouble;

    public CellNumber(double value, boolean isDouble) {
        setValue(value);
        setDouble(isDouble);
    }

    @Override
    public Number getValue() {
    	if(isDouble)
    		return value;
    	else
    		return (int) value;
    }

    private void setValue(double value) {
        this.value = value;
    }

	public boolean isDouble() {
		return isDouble;
	}

	public void setDouble(boolean isDouble) {
		this.isDouble = isDouble;
	}

	@Override
    public String getContent() {
    	return getValue().toString();
    }

    @Override
    public void onDelete() {}
}
